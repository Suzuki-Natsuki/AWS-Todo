package com.dig.toodles

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import java.util.*

@Repository
@Primary // このRepositoryが優先される
class DynamoDBTodoRepository(
    val client: DynamoDbClient,
) : TodoRepository {
    override fun getAllTodoItem(): List<TodoItem> {
        val scanRequest = ScanRequest.builder()
            .tableName("TodoTable")
            .build()

        return client.scan(scanRequest).items().map(this::mapToTodoItem)
    }

    override fun getTodoItemById(id: String): TodoItem? {
        return getAllTodoItem().firstOrNull {it.id.equals(UUID.fromString(id))}
    }

    override fun delete(id: String) {
        val deleteRequest = DeleteItemRequest.builder()
            .tableName("TodoTable")
            .key(mapOf(
                "id" to AttributeValue.fromS(UUID.fromString(id).toString())
            ))
            .build()

        client.deleteItem(deleteRequest)
    }
    override fun post(todo: TodoItem) {
        val putItemRequest = PutItemRequest.builder()
            .tableName("TodoTable")
            .item(todoItemToMap(todo))
            .build()

        println(putItemRequest)
        println(putItemRequest.item())

        client.putItem(putItemRequest)
    }
    private fun mapToTodoItem(map: Map<String, AttributeValue>): TodoItem {
        return TodoItem(
            title = map["title"]!!.s(),
            id = UUID.fromString(map["id"]!!.s()),
            done = map["done"]!!.bool()
        )

    }

    private fun todoItemToMap(todo: TodoItem): Map<String, AttributeValue>{
        return mapOf(
            "id" to AttributeValue.fromS(todo.id.toString()),
            "title" to AttributeValue.fromS(todo.title),
            "done" to AttributeValue.fromBool(todo.done)
        )
    }

}
