package com.dig.toodles

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI


@Component
@ConfigurationProperties(prefix = "aws")
class AwsProperties {
    lateinit var region: String
    lateinit var dynamoDbEndpoint: String
    lateinit var tableName: String
}

@Configuration
class DynamodbClient(val awsProperties: AwsProperties) {
    @Bean
    fun dynamoDbClient(env: Environment): DynamoDbClient {
        val dummyCredentials = AwsBasicCredentials.create("xxx", "yyy")
        return DynamoDbClient.builder()
            .region(Region.of(awsProperties.region))
            .credentialsProvider(AwsCredentialsProviderChain.of(
                DefaultCredentialsProvider.create(),
                StaticCredentialsProvider.create(dummyCredentials)
            ))
            .endpointOverride(URI.create(awsProperties.dynamoDbEndpoint))
            .build()
    }
}
