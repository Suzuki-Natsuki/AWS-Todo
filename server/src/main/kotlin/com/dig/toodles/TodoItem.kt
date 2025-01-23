package com.dig.toodles

import java.util.*

data class TodoItem(
    val id: UUID = UUID.randomUUID(),
    var title: String,
    var done: Boolean = false,
)
