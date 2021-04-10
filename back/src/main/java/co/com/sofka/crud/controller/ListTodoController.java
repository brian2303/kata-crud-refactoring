package co.com.sofka.crud.controller;

import co.com.sofka.crud.dto.ListTodoDTO;
import co.com.sofka.crud.dto.TodoDTO;
import co.com.sofka.crud.service.ListTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("list-todo")
public class ListTodoController {

    @Autowired
    ListTodoService service;

    @PostMapping
    public ListTodoDTO save(@RequestBody ListTodoDTO listTodoDTO){
        return service.save(listTodoDTO);
    }

    @PostMapping(value = "/todo")
    public TodoDTO save(@RequestBody TodoDTO todoDTO){
        return service.save(todoDTO);
    }





}
