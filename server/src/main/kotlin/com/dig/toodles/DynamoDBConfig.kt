package com.dig.toodles

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI


@Component
@ConfigurationProperties(prefix = "aws")
class AwsProperties {
    lateinit var region: String
    lateinit var accessKey: String
    lateinit var secretKey: String
    lateinit var dynamoDbEndpoint: String
}

@Configuration
class DynamodbClient(val awsProperties: AwsProperties) {
    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        val credentials = AwsBasicCredentials.create(
            awsProperties.accessKey,
            awsProperties.secretKey
        )

        return DynamoDbClient.builder()
            .region(Region.of(awsProperties.region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .endpointOverride(URI.create(awsProperties.dynamoDbEndpoint))
//            .endpointOverride(URI.create("http://localhost:8080"))
            .build()
    }
}
