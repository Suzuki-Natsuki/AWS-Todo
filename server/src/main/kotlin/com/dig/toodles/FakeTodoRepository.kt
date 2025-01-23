package com.dig.toodles

import org.springframework.stereotype.Repository
import java.util.*

val defaultFakeTodoItems = listOf(
    TodoItem(UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002634"), "外郎を食べる"),
    TodoItem(UUID.fromString("D96416E3-2ABE-47C2-8406-4EE2938FE8E0"), "じゃがりこを買う"),
    TodoItem(UUID.fromString("2B6A66CB-51FC-4A04-B595-0CE398724B02"), "お買い物する")
)

// DynamoDBの代わり
@Repository
class FakeTodoRepository(
    var todoItems: List<TodoItem> = defaultFakeTodoItems
) : TodoRepository {

    override fun getAllTodoItem(): List<TodoItem> {
        return todoItems
    }

    override fun getTodoItemById(id: String): TodoItem? {
        return todoItems.find{it.id.toString() == id}
    }

    override fun post(todo: TodoItem) {
        delete(todo.id.toString())
        todoItems += listOf(todo)
    }

    override fun delete(id: String) {
        todoItems = todoItems.filter { it.id.toString() != id }
    }
}
