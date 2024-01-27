package com.example.simplychatgptapiconsumer.main

import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.model.ChatResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class MainRepositoryTest {

    private lateinit var chatGptApi: ChatGptApi
    private lateinit var mainRepository: MainRepository

    @BeforeEach
    fun setUp() {
        chatGptApi = mockk()
        mainRepository = MainRepository(chatGptApi)
    }

    @Test
    fun `getQuestions is successful when request returns response body`() {
        runTest {
            val chatResponse: ChatResponse = mockk()
            val response: Response<ChatResponse?> = mockk()
            coEvery { chatGptApi.getChatCompletions(any()) } returns response
            coEvery { response.body() } returns chatResponse

            val tested = mainRepository.getQuestions("request")

            assertTrue(tested.getOrNull() != null)
            assertTrue(tested.isSuccess)
        }
    }

    @Test
    fun `getQuestions fails when response returns null`() {
        runTest {
            val response: Response<ChatResponse?> = mockk()
            coEvery { chatGptApi.getChatCompletions(any()) } returns response
            coEvery { response.body() } returns null

            val tested = mainRepository.getQuestions("request")

            assertTrue(tested.getOrNull() == null)
            assertTrue(tested.isFailure)
        }
    }
}
