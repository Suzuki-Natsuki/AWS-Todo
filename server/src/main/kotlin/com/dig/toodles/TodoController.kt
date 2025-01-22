package com.dig.toodles

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TodoController(val todoRepository: TodoRepository) {
    @GetMapping("/api/todo")
    fun getAllTodoItems(): List<TodoItem> {
        return todoRepository.getAllTodoItem();
    }
}