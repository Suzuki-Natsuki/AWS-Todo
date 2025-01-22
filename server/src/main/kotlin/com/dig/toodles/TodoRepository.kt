package com.dig.toodles

// DynamoDBにもRepositoryを統一
// ルール（呼び方）を決めておく
interface TodoRepository {
    fun getAllTodoItem(): List<TodoItem>
}