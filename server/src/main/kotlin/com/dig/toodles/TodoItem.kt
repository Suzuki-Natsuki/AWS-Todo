package com.dig.toodles

import java.util.*

data class TodoItem(
    var title: String,
    var done: Boolean,
    val id: UUID
)