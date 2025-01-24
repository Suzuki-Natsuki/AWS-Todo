package com.dig.toodles

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*

@SpringBootTest
class DynamoDBTodoRepositoryTest {

    @Autowired
    lateinit var dynamoDbClient: DynamoDbClient
    private val tableName = "TodoTable"

    @BeforeEach
    fun setupTable() {
        val listTablesResponse = dynamoDbClient.listTables()

        if (!listTablesResponse.tableNames().contains(tableName)) {
        dynamoDbClient.createTable(
            CreateTableRequest.builder()
                .tableName(tableName)
                .keySchema(
                    KeySchemaElement.builder()
                        .attributeName("id")     // パーティションキーの属性名
                        .keyType(KeyType.HASH)   // HASH = パーティションキー
                        .build()
                )
                .attributeDefinitions(
                    AttributeDefinition.builder()
                        .attributeName("id")           // 上記と同じキー名
                        .attributeType(ScalarAttributeType.S)  // S=String
                        .build()
                )
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build()
        )
        }

    }

    @Test
    fun `postできる`() {
//        val newItem = TodoItem(title = "ほげ")
//        val repo = DynamoDBTodoRepository(client = dynamoDbClient)

//        assertEquals(repo.getAllTodoItem().size, 0);
//        repo.post(newItem)
//        assertEquals(repo.getAllTodoItem().size, 1);

    }

    @AfterEach
    fun cleanupTable() {
        dynamoDbClient.deleteTable(
            DeleteTableRequest.builder()
                .tableName(tableName)
                .build()
        )
    }
}
