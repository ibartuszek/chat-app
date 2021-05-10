package com.example.chatapp.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["com.example.chatapp.dal.repository"])
@Profile("!it")
class MongoDbConfiguration(
        val properties: MongoConfigurationProperties) : AbstractMongoClientConfiguration() {

    override fun getDatabaseName() = properties.databaseName

    override fun mongoClient() = MongoClients.create(createMongoClientSettings())

    fun createMongoClientSettings() = MongoClientSettings.builder()
            .applyConnectionString(properties.connectionString())
            .build()
}

@Configuration
@ConfigurationProperties(prefix = "mongo")
data class MongoConfigurationProperties(var address: String = "", var databaseName: String = "", var port: Int = 0) {

    fun connectionString() = ConnectionString("mongodb://$address:$port/$databaseName")

}
