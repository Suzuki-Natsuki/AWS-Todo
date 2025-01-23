package com.dig.toodles

// DynamoDBとFakeTodoRepositoryで統一させることができる
interface TodoRepository {
    fun getAllTodoItem(): List<TodoItem>
    fun getTodoItemById(id: String): TodoItem?
    fun post(todo: TodoItem)
    fun delete(id: String)

}
