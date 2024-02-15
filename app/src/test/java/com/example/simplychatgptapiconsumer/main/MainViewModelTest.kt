package com.example.simplychatgptapiconsumer.main

import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Error
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Loading
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Success
import com.example.simplychatgptapiconsumer.common.model.ChatResponse
import com.example.simplychatgptapiconsumer.common.model.Choice
import com.example.simplychatgptapiconsumer.common.model.MessageResponse
import com.example.simplychatgptapiconsumer.common.model.Usage
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mainRepository: MainRepository by lazy { mockk() }
    private val choice by lazy { mockk<Choice>() }
    private val message by lazy { MessageResponse(role = "", content = "") }
    private val usage by lazy { mockk<Usage>() }
    private val chatResponse: ChatResponse by lazy {
        ChatResponse(
            choices = listOf(choice),
            created = 0,
            id = "0",
            model = "",
            `object` = "",
            usage = usage
        )
    }
    private lateinit var mainViewModel: MainViewModel


    @BeforeEach()
    fun init() {
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(mainRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `questionsState is Success WHEN questions are fetched correctly`() {
        runTest {
            coEvery { choice.message } returns message
            coEvery { mainRepository.getQuestions("Szczecin") } returns Result.success(chatResponse)

            mainViewModel.getResponse("Szczecin")

            advanceUntilIdle()
            assertTrue(mainViewModel.questionsState.value is Success)
        }
    }

    @Test
    fun `questionsState is Error WHEN questions are not fetched correctly`() {
        runTest {
            coEvery { mainRepository.getQuestions("Szczecin") } returns Result.failure(Exception())

            mainViewModel.getResponse("Szczecin")

            advanceUntilIdle()
            assertTrue(mainViewModel.questionsState.value is Error)
        }
    }

    @Test
    fun `questionsState is Loading WHEN questions were not fetched yet`() {
        runTest {
            assertTrue(mainViewModel.questionsState.value is Loading)
        }
    }

    @Test
    fun `questionsState is Loading WHEN the success is not called yet`() {
        runTest {
            coEvery { choice.message } returns message
            coEvery { mainRepository.getQuestions("Szczecin") } returns Result.success(chatResponse)

            mainViewModel.getResponse("Szczecin")
            val state = mainViewModel.questionsState.take(2).toList()

            advanceUntilIdle()
            assertEquals(Loading, state[0])
        }
    }

    @Test
    fun `questionsState is Loading WHEN the failure is not called yet`() {
        runTest {
            coEvery { choice.message } returns message
            coEvery { mainRepository.getQuestions("Szczecin") } returns Result.failure(Exception())

            mainViewModel.getResponse("Szczecin")
            val state = mainViewModel.questionsState.take(1).toList()

            advanceUntilIdle()
            assertEquals(Loading, state.first())
        }
    }
}
