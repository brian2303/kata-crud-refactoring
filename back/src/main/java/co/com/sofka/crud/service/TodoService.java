package co.com.sofka.crud.service;

import co.com.sofka.crud.converter.TodoConverter;
import co.com.sofka.crud.dto.TodoDTO;
import co.com.sofka.crud.entity.ListTodo;
import co.com.sofka.crud.repository.ListTodoRepository;
import co.com.sofka.crud.repository.TodoRepository;
import co.com.sofka.crud.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    ListTodoRepository listTodoRepository;

    @Autowired
    ListTodoService listTodoService;

    public TodoDTO save(TodoDTO todoDTO){
        TodoConverter converter = new TodoConverter();
        Todo todo = converter.fromDTO(todoDTO);

        ListTodo listTodoById = listTodoRepository.findById(todoDTO.getListTodoId())
                .orElseThrow(() -> new RuntimeException("List id not found"));
        listTodoById.getListTodo().add(todo);

        ListTodo newListTodo = listTodoRepository.save(listTodoById);

        Integer lastTodo = (newListTodo.getListTodo().size() - 1);

        Todo newTodo = newListTodo.getListTodo()
                .get(lastTodo);

        TodoDTO toTodoDTO = converter.fromEntity(newTodo);
        toTodoDTO.setListTodoId(newListTodo.getId());

        return toTodoDTO;
    }


    public TodoDTO update(Long id,TodoDTO todoDTO){
        TodoConverter todoConverter = new TodoConverter();
        ListTodo listToDoToUpdate = listTodoService.getListTodoById(id);
        listToDoToUpdate.getListTodo()
                .stream()
                .map(todoToUpdate -> {
                    if(todoToUpdate.getId().equals(todoDTO.getId())) {
                        todoToUpdate.setName(todoDTO.getName());
                        todoToUpdate.setCompleted(todoDTO.getCompleted());
                    }
                    return todoToUpdate;
                }).collect(Collectors.toList());
        ListTodo listTodo = listTodoRepository.save(listToDoToUpdate);

        Todo todoUpdated = findTodoUpdated(listTodo,todoDTO.getId());

        return todoConverter.fromEntity(todoUpdated);
    }

    private Todo findTodoUpdated(ListTodo listTodo,Long idTodo){
        return listTodo.getListTodo()
                .stream()
                .filter(todo -> todo.getId().equals(idTodo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("todo updated not found"));
    }


    public void delete(Long idList,Long idTodo){
        ListTodo listTodo = listTodoService.getListTodoById(idList);
        Todo todo = getTodoById(listTodo,idTodo);
        listTodo.getListTodo().remove(todo);
        listTodoRepository.save(listTodo);
    }

    public TodoDTO getTodo(Long idList, Long idTodo) {
        TodoConverter todoConverter = new TodoConverter();
        ListTodo listTodo = listTodoService.getListTodoById(idList);
        Todo todo = getTodoById(listTodo,idTodo);
        TodoDTO todoDTO = todoConverter.fromEntity(todo);
        todoDTO.setListTodoId(listTodo.getId());
        return todoDTO;
    }

    private Todo getTodoById(ListTodo listTodo,Long idTodo){
        return listTodo.getListTodo()
                .stream()
                .filter(t -> t.getId().equals(idTodo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Todo id not found"));
    }

}
