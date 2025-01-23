package com.dig.toodles

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
class TodoController(val todoRepository: TodoRepository) {
    @GetMapping("/api/todo")
    fun getAllTodoItems(): List<TodoItem> {
        return todoRepository.getAllTodoItem();
    }

    @GetMapping("/api/todo/{id}")
    fun getTodoItemById(@PathVariable id: String): TodoItem {
        return todoRepository.getTodoItemById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
    //    // 1行の書き方（return不要）
    //    @GetMapping("/api/todo/{id}")
    //    fun getTodoItemById(@PathVariable id: String): TodoItem =
    //        todoRepository.getTodoItemById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @PostMapping("/api/todo")
    @ResponseStatus(HttpStatus.CREATED)
    fun newTodoItem(@RequestBody newItem: TodoItem) {
        todoRepository.put(newItem)
    }
}
