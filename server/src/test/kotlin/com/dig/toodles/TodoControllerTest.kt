package com.dig.toodles

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoControllerTest {
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController()).build()
    }

    @Test
    fun `Todoリストを全て取得する`() {
        mockMvc.perform(get("/api/todo"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("外郎を食べる"))
            .andExpect(jsonPath("$[0].done").value(false))
            .andExpect(jsonPath("$[0].id").value("e5789998-7644-49c3-ab58-bcb47d002634"))
            .andExpect(jsonPath("$[1].title").value("じゃがりこを買う"))
            .andExpect(jsonPath("$[1].done").value(false))
            .andExpect(jsonPath("$[1].id").value("e5789998-7644-49c3-ab58-bcb47d002634"))
    }
}