package com.example.chatapp

import com.example.chatapp.dal.document.UserDocument
import com.example.chatapp.utilities.MongoDbHelper
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import java.lang.RuntimeException
import java.time.Clock
import java.time.Instant
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ChatAppApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@ActiveProfiles("it")
@EnableMongoRepositories("com.example.chatapp.dal.repository")
abstract class ChatAppApplicationTests {

    companion object {
        private const val NOW = "2021-05-12T10:00:00Z"

        private val log = LoggerFactory.getLogger(ChatAppApplicationTests::class.java)

        private val nowInstant = Instant.parse(NOW)
    }

    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var mongoDbHelper: MongoDbHelper

    @MockBean
    protected lateinit var clock: Clock

    @BeforeEach
    fun initClock() {
        `when`(clock.instant()).thenReturn(nowInstant)
    }

    @BeforeEach
    fun initMongoCollections() {
        parseFile("/mongodb/userCollection.json", Array<UserDocument>::class.java)
                .forEach { mongoDbHelper.saveUser(it) }
    }

    @AfterEach
    fun clearMongoCollections() {
        mongoDbHelper.deleteAllUser()
    }

    fun <T> parseFile(fileName: String, clazz: Class<T>): T {
        val content = readJsonFile(fileName)
        return parseString(content, clazz)
    }

    fun readJsonFile(fileName: String): String {
        val scanner = Scanner(ChatAppApplicationTests::class.java.getResourceAsStream(fileName))
                .useDelimiter("\\A")
        return if (scanner.hasNext()) scanner.next() else ""
    }

    fun <T> parseString(content: String, clazz: Class<T>): T {
        try {
            return objectMapper.readValue(content, clazz)
        } catch (exception: JsonProcessingException) {
            log.error("content=$content cannot be parsed to class=$clazz")
            throw RuntimeException(exception.message)
        }
    }

    fun getObjectAsString(any: Any): String {
        try {
            return objectMapper.writeValueAsString(any)
        } catch (e: JsonProcessingException) {
            log.error("object=$any cannot be write to string")
            throw RuntimeException(e.message)
        }
    }


}
