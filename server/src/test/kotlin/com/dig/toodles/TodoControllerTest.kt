package com.dig.toodles


import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class TodoControllerTest {
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Todoリストを全て取得する`() {
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(FakeTodoRepository())).build()

        mockMvc.perform(get("/api/todo"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].title").value("外郎を食べる"))
            .andExpect(jsonPath("$[0].done").value(false))
            .andExpect(jsonPath("$[0].id").value("e5789998-7644-49c3-ab58-bcb47d002634"))
            .andExpect(jsonPath("$[1].title").value("じゃがりこを買う"))
            .andExpect(jsonPath("$[1].done").value(false))
            .andExpect(jsonPath("$[1].id").value("d96416e3-2abe-47c2-8406-4ee2938fe8e0"))
    }

    @Test
    fun `idを使って特定のTodoリストを取得する`() {
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(FakeTodoRepository())).build()

        mockMvc.perform(get("/api/todo/2b6a66cb-51fc-4a04-b595-0ce398724b02"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("2b6a66cb-51fc-4a04-b595-0ce398724b02"))
            .andExpect(jsonPath("$.title").value("お買い物する"))
            .andExpect(jsonPath("$.done").value(false))
    }

    @Test
    fun `存在しないidでTodoリストを取得した時に、404ステータスを返す`() {
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(FakeTodoRepository())).build()

        mockMvc.perform(get("/api/todo/helloworld"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Todoリストに初めての項目を追加する`() {
        val fakeRepo = FakeTodoRepository(emptyList())
        val newItem = TodoItem(title="卓球大会を行う")

        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(fakeRepo)).build()
        mockMvc.perform(
            post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(newItem))
        )
            .andExpect(status().isCreated)

        assertEquals(fakeRepo.getAllTodoItem().size, 1)
        assertEquals(fakeRepo.getAllTodoItem()[0].title, "卓球大会を行う")
        assertEquals(fakeRepo.getAllTodoItem()[0].done, false)
    }

    @Test
    fun `Todoリストに複数の項目を追加する`() {
        val fakeRepo = FakeTodoRepository(listOf(TodoItem(title="音楽を聴く")))
        val newItem = TodoItem(title="卓球大会を行う")

        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(fakeRepo)).build()
        mockMvc.perform(
            post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(newItem))
        )
            .andExpect(status().isCreated)

        assertEquals(fakeRepo.getAllTodoItem().size, 2)
        assertEquals(fakeRepo.getAllTodoItem()[0].title, "音楽を聴く")
        assertEquals(fakeRepo.getAllTodoItem()[0].done, false)
        assertEquals(fakeRepo.getAllTodoItem()[1].title, "卓球大会を行う")
        assertEquals(fakeRepo.getAllTodoItem()[1].done, false)
        assertNotEquals(fakeRepo.getAllTodoItem()[0].id, fakeRepo.getAllTodoItem()[1].id)
    }

    @Test
    fun `Todoリストから1項目削除する`() {
        val mockTodoItem = TodoItem(title="卓球大会を行う")
        val fakeRepo = FakeTodoRepository(listOf(mockTodoItem))

        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(fakeRepo)).build()

        val deleteId: String = mockTodoItem.id.toString()

        mockMvc.perform(
            delete("/api/todo/${deleteId}")
        )
            .andExpect(status().isOk)

        assertNull(fakeRepo.getTodoItemById(deleteId))
    }

    @Test
    fun `同じidだと追加されず、更新される`() {
        val originalItem = TodoItem(title="琴を引く", done = true)
        val fakeRepo = FakeTodoRepository(listOf(originalItem))

        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(fakeRepo)).build()

        val updateItem = TodoItem(id = originalItem.id, title="ギターを弾く" ,done=originalItem.done)
        mockMvc.perform( patch("/api/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(updateItem))
        )
        .andExpect(status().isOk)

        assertEquals(fakeRepo.getTodoItemById(updateItem.id.toString())?.title, "ギターを弾く")
        assertEquals(fakeRepo.getTodoItemById(updateItem.id.toString())?.done, true)
    }


}
