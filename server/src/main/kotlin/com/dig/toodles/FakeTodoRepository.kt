package com.dig.toodles

import org.springframework.stereotype.Repository
import java.util.*

val defaultFakeTodoItems = listOf(
    TodoItem(UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002634"), "外郎を食べる"),
    TodoItem(UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002634"), "じゃがりこを買う"),
    TodoItem(UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002635"), "uuid変えてみた")
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

    override fun put(todo: TodoItem) {
        todoItems += listOf(todo)
    }
}
