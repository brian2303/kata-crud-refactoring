package co.com.sofka.crud.service;

import co.com.sofka.crud.converter.ListToDoConverter;
import co.com.sofka.crud.converter.TodoConverter;
import co.com.sofka.crud.dto.ListTodoDTO;
import co.com.sofka.crud.dto.TodoDTO;
import co.com.sofka.crud.entity.ListTodo;
import co.com.sofka.crud.entity.Todo;
import co.com.sofka.crud.repository.ListTodoRepository;
import co.com.sofka.crud.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ListTodoService {

    @Autowired
    ListTodoRepository repository;


    public ListTodoDTO save(ListTodoDTO listTodoDTO){
        ListToDoConverter converter = new ListToDoConverter();
        ListTodo listTodo = converter.fromDTO(listTodoDTO);
        return converter.fromEntity(repository.save(listTodo));
    }




    public Boolean delete(Long id) {
        return repository.findById(id)
                .map(listTodo -> {
                    repository.delete(listTodo);
                    return true;
                }).orElse(false);
    }
}
