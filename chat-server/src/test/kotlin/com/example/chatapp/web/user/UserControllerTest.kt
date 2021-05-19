package com.example.chatapp.web.user

import com.example.chatapp.ChatAppApplicationTests
import com.example.chatapp.domain.NewUserRequest
import com.example.chatapp.domain.UserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

internal class UserControllerTest : ChatAppApplicationTests() {

    companion object {
        private const val BASE_URL = "/chat/rest/user"
        private const val CREATE_URL = "$BASE_URL/create"
        private const val USER_NAME = "John Rambo"
        private const val USER_EMAIL = "john.rambo@mail.com"
        private const val EXISTING_USER_ID = "609e2343d4b6a54baa32c475"
        private const val EXISTING_USER_NAME = "Spiderman"
        private const val EXISTING_USER_EMAIL = "peter.parker@spider-net.com"
        private const val UPDATED_NAME = "Amazing Spiderman"
        private const val UPDATED_EMAIL = "amazing.spiderman@spider-net2.com"

        @JvmStatic
        fun newUserRequests() = listOf<Arguments>(
                Arguments.of(NewUserRequest("", "email@email.com"), "User name cannot be empty"),
                Arguments.of(NewUserRequest("Name", ""), "User email cannot be empty"),
                Arguments.of(NewUserRequest("Name", "email"), "User email must be valid"),
        )

        @JvmStatic
        fun updateUserRequests() = listOf<Arguments>(
                Arguments.of(UserModel("", UPDATED_NAME, UPDATED_EMAIL, true), "User id cannot be empty"),
                Arguments.of(UserModel(EXISTING_USER_ID, "", UPDATED_EMAIL, true), "User name cannot be empty"),
                Arguments.of(UserModel(EXISTING_USER_ID, UPDATED_NAME, "", true), "User email cannot be empty"),
                Arguments.of(UserModel(EXISTING_USER_ID, UPDATED_NAME, "email", true), "User email must be valid"),
        )
    }

    @ParameterizedTest
    @MethodSource("newUserRequests")
    fun `test create with invalid input data`(userRequest: NewUserRequest, errorMessage: String) {
        // given
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                .content(getObjectAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(), actualResponse.status)
        assertEquals(errorMessage, actualResponse.contentAsString)
    }

    @Test
    fun `test create`() {
        // given
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                .content(getObjectAsString(NewUserRequest(USER_NAME, USER_EMAIL)))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.OK.value(), actualResponse.status)
        val actual = parseString(actualResponse.contentAsString, UserModel::class.java)
        val expected = mongoDbHelper.findUserByEmail(USER_EMAIL)
        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.email, actual.email)
        assertTrue(actual.active)
    }

    @Test
    fun `test get by id should return 404`() {
        // given
        val invalidUserId = "fake"
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.get("$BASE_URL/$invalidUserId")
                .content(getObjectAsString(NewUserRequest(USER_NAME, USER_EMAIL)))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.status)
        assertEquals("User with id=$invalidUserId cannot be found", actualResponse.contentAsString)
    }

    @Test
    fun `test get by id`() {
        // given
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.get("$BASE_URL/$EXISTING_USER_ID")
                .content(getObjectAsString(NewUserRequest(USER_NAME, USER_EMAIL)))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.OK.value(), actualResponse.status)
        val actual = parseString(actualResponse.contentAsString, UserModel::class.java)
        assertEquals(EXISTING_USER_NAME, actual.name)
        assertEquals(EXISTING_USER_EMAIL, actual.email)
        assertTrue(actual.active)
    }

    @Test
    fun `test get all`() {
        // given
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.get("$BASE_URL/all")
                .content(getObjectAsString(NewUserRequest(USER_NAME, USER_EMAIL)))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.OK.value(), actualResponse.status)
        val actual = parseString(actualResponse.contentAsString, Array<UserModel>::class.java)
        assertEquals(5, actual.size)
    }

    @ParameterizedTest
    @MethodSource("updateUserRequests")
    fun `test update with invalid input data`(updatedUser: UserModel, errorMessage: String) {
        // given
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.patch(BASE_URL)
                .content(getObjectAsString(updatedUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(), actualResponse.status)
        assertEquals(errorMessage, actualResponse.contentAsString)
    }

    @Test
    fun `test update`() {
        // given
        val original = mongoDbHelper.getUserById(EXISTING_USER_ID)
        val request = UserModel(EXISTING_USER_ID, UPDATED_NAME, UPDATED_EMAIL, true)
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.patch(BASE_URL)
                .content(getObjectAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.OK.value(), actualResponse.status)
        val actual = parseString(actualResponse.contentAsString, UserModel::class.java)
        assertEquals(EXISTING_USER_ID, actual.id)
        assertEquals(UPDATED_NAME, actual.name)
        assertEquals(UPDATED_EMAIL, actual.email)
        assertTrue(actual.active)
        val actualDocument = mongoDbHelper.getUserById(EXISTING_USER_ID)
        assertEquals(EXISTING_USER_ID, actualDocument.id)
        assertEquals(UPDATED_NAME, actualDocument.name)
        assertEquals(UPDATED_EMAIL, actualDocument.email)
        assertTrue(actualDocument.active)
        assertEquals(original.version + 1, actualDocument.version)
    }

    @Test
    fun `test delete user should return 404`() {
        // given
        val invalidUserId = "fake"
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.delete("$BASE_URL/$invalidUserId")
                .content(getObjectAsString(NewUserRequest(USER_NAME, USER_EMAIL)))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.status)
        assertEquals("User with id=$invalidUserId cannot be found", actualResponse.contentAsString)
    }

    @Test
    fun `test delete`() {
        // given
        val originalDocument = mongoDbHelper.getUserById(EXISTING_USER_ID)
        // when
        val actualResponse = mvc.perform(MockMvcRequestBuilders.delete("$BASE_URL/$EXISTING_USER_ID")
                .content(getObjectAsString(NewUserRequest(USER_NAME, USER_EMAIL)))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        // then
        assertEquals(HttpStatus.OK.value(), actualResponse.status)
        assertNotNull(originalDocument)
        val anonymizedUser = mongoDbHelper.getAllUser().firstOrNull { it.id == EXISTING_USER_ID }!!
        assertEquals("¯\\_(ツ)_/¯", anonymizedUser.name)
        assertEquals("***@***.***", anonymizedUser.email)
        assertFalse(anonymizedUser.active)
    }

}
