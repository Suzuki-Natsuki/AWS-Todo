package com.dig.toodles

// DynamoDBとFakeTodoRepositoryで統一させることができる
interface TodoRepository {
    fun getAllTodoItem(): List<TodoItem>
    fun getTodoItemById(id: String): TodoItem?
    fun put(todo: TodoItem)
//    fun insertItem(todo: TodoItem)
//    fun update(id: String, todo: TodoItem): TodoItem
//    fun delete(id: String): Boolean
}
