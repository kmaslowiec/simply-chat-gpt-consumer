package com.example.simplychatgptapiconsumer.main

import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.model.ChatRequest
import com.example.simplychatgptapiconsumer.common.model.ChatResponse
import com.example.simplychatgptapiconsumer.common.model.Message
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ChatGptApi) {

    suspend fun getQuestions(question: String): Result<ChatResponse> =
        runCatching {
            api.getChatCompletions(
                ChatRequest(
                    messages = listOf(
                        Message(role = "user", content = question),
                    )
                )
            ).body()!!
        }
}
