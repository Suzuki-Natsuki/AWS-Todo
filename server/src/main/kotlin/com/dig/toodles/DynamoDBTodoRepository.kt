package com.dig.toodles

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

@Repository
@Primary // このRepositoryが優先される
class DynamoDBTodoRepository(
    val client: DynamoDbClient,
) : TodoRepository {
    override fun getAllTodoItem(): List<TodoItem> {
        TODO("Not yet implemented")
    }

    override fun getTodoItemById(id: String): TodoItem? {
        TODO("Not yet implemented")
    }

    override fun post(todo: TodoItem) {
        val putItemRequest = PutItemRequest.builder()
            .tableName("TodoTable")
            .item(
                mapOf(
                    "id" to AttributeValue.fromS(todo.id.toString()),
                    "title" to AttributeValue.fromS(todo.title),
                    "done" to AttributeValue.fromBool(todo.done)
                )
            )
            .build()

        client.putItem(putItemRequest)
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

}
