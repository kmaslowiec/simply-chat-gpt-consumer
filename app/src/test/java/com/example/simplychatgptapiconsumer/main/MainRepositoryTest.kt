package com.example.simplychatgptapiconsumer.main

import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.model.ChatResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class MainRepositoryTest {

    private val chatGptApi: ChatGptApi by lazy { mockk() }
    private val response: Response<ChatResponse?> by lazy { mockk() }
    private lateinit var mainRepository: MainRepository

    @BeforeEach
    fun setUp() {
        mainRepository = MainRepository(chatGptApi)
    }

    @Test
    fun `getQuestions is successful WHEN request returns response body`() {
        runTest {
            val chatResponse: ChatResponse = mockk()
            coEvery { chatGptApi.getChatCompletions(any()) } returns response
            coEvery { response.body() } returns chatResponse

            val result = mainRepository.getQuestions("request")

            assertNotNull(result.getOrNull())
            assertTrue(result.isSuccess)
        }
    }

    @Test
    fun `getQuestions fails WHEN response returns null`() {
        runTest {
            val response: Response<ChatResponse?> = mockk()
            coEvery { chatGptApi.getChatCompletions(any()) } returns response
            coEvery { response.body() } returns null

            val result = mainRepository.getQuestions("request")

            assertNull(result.getOrNull())
            assertTrue(result.isFailure)
        }
    }
}
