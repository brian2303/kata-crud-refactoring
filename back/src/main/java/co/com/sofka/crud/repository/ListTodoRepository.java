package co.com.sofka.crud.repository;

import co.com.sofka.crud.entity.ListTodo;
import org.springframework.data.repository.CrudRepository;

public interface ListTodoRepository extends CrudRepository<ListTodo,Long> {
}
