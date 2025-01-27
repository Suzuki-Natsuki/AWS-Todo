package com.dig.toodles

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
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
    fun dynamoDbClient(env: Environment): DynamoDbClient {
        val basicCredentials = AwsBasicCredentials.create(
            awsProperties.accessKey,
            awsProperties.secretKey
        )

//        const credentialsProvider = !!env.containsProperty("AWS_SESSION_TOKEN")
//            ? EnvironmentVariableCredentialsProvider.create()
//            : StaticCredentialsProvider.create(basicCredentials)
        val credentialsProvider = if(env.activeProfiles.contains("aws"))
            EnvironmentVariableCredentialsProvider.create()
            else StaticCredentialsProvider.create(basicCredentials)


        return DynamoDbClient.builder()
            .region(Region.of(awsProperties.region))
            .credentialsProvider(credentialsProvider)
            .endpointOverride(URI.create(awsProperties.dynamoDbEndpoint))
            .build()
    }
}
