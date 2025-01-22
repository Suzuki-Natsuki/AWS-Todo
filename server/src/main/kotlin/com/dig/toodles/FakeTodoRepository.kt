package com.dig.toodles

import org.springframework.stereotype.Repository
import java.util.*


// DynamoDBの代わり
@Repository
class FakeTodoRepository: TodoRepository {
    private val todoItems = listOf(
        TodoItem("外郎を食べる", false, UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002634")),
        TodoItem("じゃがりこを買う", false, UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002634"))
    )

    override fun getAllTodoItem(): List<TodoItem> {
        return todoItems
    }

}
