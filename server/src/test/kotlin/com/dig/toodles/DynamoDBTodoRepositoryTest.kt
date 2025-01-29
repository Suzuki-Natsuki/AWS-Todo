package com.dig.toodles

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.net.URI
import java.util.*

@SpringBootTest
class DynamoDBTodoRepositoryTest {

    lateinit var dynamoDbClient: DynamoDbClient

    @Autowired
    lateinit var awsProperties: AwsProperties

/*

*/
    @BeforeEach
    fun setupTable() {
        val credentials = AwsBasicCredentials.create("xxx", "yyy")
        val credentialsProvider = StaticCredentialsProvider.create(credentials)
        dynamoDbClient = DynamoDbClient.builder()
            .region(Region.of(awsProperties.region))
            .credentialsProvider(credentialsProvider)
            .endpointOverride(URI.create(awsProperties.dynamoDbEndpoint))
            .build()

        val listTablesResponse = dynamoDbClient.listTables()

        if (!listTablesResponse.tableNames().contains(awsProperties.tableName)) {
        dynamoDbClient.createTable(
            CreateTableRequest.builder()
                .tableName(awsProperties.tableName)
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
    fun `We can get all TodoItem`() {
        val newItem = TodoItem(
            title = "ほげ",
            id = UUID.fromString("12345678-1234-1234-1234-123456789ABC"),
            done = true
        )
        val repo = DynamoDBTodoRepository(
            dynamoDbClient,
            awsProperties
        )

        repo.post(newItem)
        assertEquals(repo.getAllTodoItem()[0].title, "ほげ")
        assertEquals(repo.getAllTodoItem()[0].id, UUID.fromString("12345678-1234-1234-1234-123456789ABC"))
        assertEquals(repo.getAllTodoItem()[0].done, true)
    }

    @Test
    fun `postとgetAllTodoItemできる`() {
        val newItem = TodoItem(title = "ほげ")
        val repo = DynamoDBTodoRepository(dynamoDbClient, awsProperties)

        assertEquals(repo.getAllTodoItem().size, 0)
        repo.post(newItem)
        assertEquals(repo.getAllTodoItem().size, 1)
    }

    @Test
    fun `IdでGetできる`() {
        val repo = DynamoDBTodoRepository(dynamoDbClient, awsProperties)
        val newItems = listOf(
            TodoItem(
                title = "走る",
                id = UUID.fromString("119846CB-D49F-4DAA-A129-3C18FC6347E2"),
                done = true
            ),
            TodoItem(
                title = "歩く",
                id = UUID.fromString("65B1217A-FFA6-401D-AB73-0BC4DB9A2C0F"),
                done = true
            ),
            TodoItem(
                title = "寝る",
                id = UUID.fromString("B12F259C-6D92-473B-AAB7-58694AA15361"),
                done = true
            ),

        )

        newItems.forEach {repo.post(it)}
        assertEquals(repo.getTodoItemById("119846CB-D49F-4DAA-A129-3C18FC6347E2"), newItems[0])
        assertEquals(repo.getTodoItemById("65B1217A-FFA6-401D-AB73-0BC4DB9A2C0F"), newItems[1])
        assertEquals(repo.getTodoItemById("B12F259C-6D92-473B-AAB7-58694AA15361"), newItems[2])

    }

    @Test
    fun `Idがないの時Getは大丈夫`() {
        val repo = DynamoDBTodoRepository(dynamoDbClient, awsProperties)
        assertEquals(repo.getTodoItemById(UUID.randomUUID().toString()), null)
    }

    @Test
    fun `Idを使ってItemを削除する`() {
        val repo = DynamoDBTodoRepository(dynamoDbClient, awsProperties)
        val newItems = listOf(
            TodoItem(
                title = "走る",
                id = UUID.fromString("119846CB-D49F-4DAA-A129-3C18FC6347E2"),
                done = true
            ),
            TodoItem(
                title = "歩く",
                id = UUID.fromString("65B1217A-FFA6-401D-AB73-0BC4DB9A2C0F"),
                done = true
            ),
        )
        newItems.forEach {repo.post(it)}
        assertEquals(repo.getAllTodoItem().size, 2)

        repo.delete(newItems[0].id.toString())
        assertEquals(repo.getAllTodoItem()[0].title, "歩く")
        assertEquals(repo.getAllTodoItem().size, 1)
    }

    @Test
    fun `deleteしたいidがない時も大丈夫`() {
        val repo = DynamoDBTodoRepository(dynamoDbClient, awsProperties)
        val newItem = TodoItem(
            title = "ほげ",
            id = UUID.fromString("12345678-1234-1234-1234-123456789ABC"),
            done = true
        )
        repo.post(newItem)

        repo.delete(UUID.randomUUID().toString())
        assertEquals(repo.getAllTodoItem(), listOf(newItem))
    }


    @AfterEach
    fun cleanupTable() {
        dynamoDbClient.deleteTable(
            DeleteTableRequest.builder()
                .tableName(awsProperties.tableName)
                .build()
        )
    }
}
