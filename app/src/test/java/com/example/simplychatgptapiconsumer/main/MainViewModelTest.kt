package com.example.simplychatgptapiconsumer.main

import com.example.simplychatgptapiconsumer.common.apimodel.ChatResponse
import com.example.simplychatgptapiconsumer.common.apimodel.Choice
import com.example.simplychatgptapiconsumer.common.apimodel.MessageResponse
import com.example.simplychatgptapiconsumer.common.apimodel.Usage
import com.example.simplychatgptapiconsumer.main.intent.MainViewIntent
import com.example.simplychatgptapiconsumer.main.intent.MainViewSideEffect
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType
import com.example.simplychatgptapiconsumer.main.repository.MainRepository
import com.example.simplychatgptapiconsumer.main.view.MainViewModel
import io.mockk.awaits
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
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
    fun `update trivia subject WHEN new subject is provided`() {
        runTest {
            coEvery { choice.message } returns message
            coEvery { mainRepository.getQuestions("Szczecin") } returns Result.success(chatResponse)
            mainViewModel.updateTriviaSubject("Szczecin")

            assertEquals(mainViewModel.state.value.triviaSubject, "Szczecin")
        }
    }

    @Test
    fun `MainViewState type is Success WHEN data is fetched correctly`() {
        runTest {
            coEvery { choice.message } returns message
            coEvery { mainRepository.getQuestions("") } returns Result.success(chatResponse)
            val intent = MainViewIntent.AskChat

            mainViewModel.emitIntent(intent)

            assertEquals(ResponseType.SUCCESS, mainViewModel.state.value.type)
        }
    }

    @Test
    fun `MainViewState type is Error WHEN data is not fetched correctly`() {
        runTest {
            coEvery { mainRepository.getQuestions("") } returns Result.failure(Exception())
            val intent = MainViewIntent.AskChat

            mainViewModel.emitIntent(intent)

            assertEquals(ResponseType.FAILURE, mainViewModel.state.value.type)
        }
    }


    @Test
    fun `MainViewState type is Idle WHEN data was not fetched yet`() {
        runTest {
            assertEquals(ResponseType.IDLE, mainViewModel.state.value.type)
        }
    }


    @Test
    fun `MainViewState type is Loading WHEN data is being fetched`() {
        runTest {
            coEvery { choice.message } returns message
            coEvery { mainRepository.getQuestions("") } just awaits
            val intent = MainViewIntent.AskChat

            mainViewModel.emitIntent(intent)

            assertEquals(ResponseType.LOADING, mainViewModel.state.value.type)
        }
    }


    @Test
    fun `sideEffect is ShowSnackBar WHEN ShowSnackBar is emitted`() {
        runTest {
            coEvery { choice.message } returns message
            coEvery { mainRepository.getQuestions("") } returns Result.success(chatResponse)
            val expectedSideEffect = MainViewSideEffect.ShowSnackBar("")
            val sideEffects = mutableListOf<MainViewSideEffect>(MainViewSideEffect.ShowSnackBar(""))
            val job = launch {
                mainViewModel.sideEffects.toList(sideEffects)
            }

            mainViewModel.emitSideEffect(expectedSideEffect)

            assertEquals(expectedSideEffect, sideEffects.first())
            job.cancel()
        }
    }
}
