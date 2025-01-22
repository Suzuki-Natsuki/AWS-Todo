package com.dig.toodles

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

data class TodoItem(
    val title: String,
    val done: Boolean,
    val id: UUID
)

@RestController
class TodoController {
    @GetMapping("/api/todo")
    fun getAllTodoItems(): List<TodoItem> {
        return listOf(
            TodoItem("外郎を食べる", false, UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002634")),
            TodoItem("じゃがりこを買う", false, UUID.fromString("E5789998-7644-49C3-AB58-BCB47D002634"))
        )
    }
}