package com.example.simplychatgptapiconsumer.main

import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.apimodel.ChatResponse
import com.example.simplychatgptapiconsumer.main.repository.MainRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainRepositoryTest {

    private val chatGptApi: ChatGptApi by lazy { mockk() }
    private lateinit var mainRepository: MainRepository

    @BeforeEach
    fun setUp() {
        mainRepository = MainRepository(chatGptApi)
    }

    @Test
    fun `getQuestions is successful WHEN request returns chatResponse`() {
        runTest {
            val chatResponse: ChatResponse = mockk()
            coEvery { chatGptApi.getChatCompletions(any()) } returns chatResponse

            val result = mainRepository.getQuestions("request")

            assertNotNull(result.getOrNull())
            assertTrue(result.isSuccess)
        }
    }

    @Test
    fun `getQuestions fails WHEN response throws exception`() {
        runTest {
            coEvery { chatGptApi.getChatCompletions(any()) } throws Exception("")

            val result = mainRepository.getQuestions("request")

            assertNull(result.getOrNull())
            assertTrue(result.isFailure)
        }
    }
}
