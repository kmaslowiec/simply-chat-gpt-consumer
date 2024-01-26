package com.example.simplychatgptapiconsumer.main

import android.util.Log
import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.constants.SYSTEM_INSTRUCTION
import com.example.simplychatgptapiconsumer.common.model.ChatRequest
import com.example.simplychatgptapiconsumer.common.model.ChatResponse
import com.example.simplychatgptapiconsumer.common.model.Message
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ChatGptApi) {

    suspend fun getQuestions(question: String): Result<ChatResponse> =
        runCatching {
            val call = api.getChatCompletions(
                ChatRequest(
                    messages = listOf(
                        Message(role = "system", content = SYSTEM_INSTRUCTION),
                        Message(role = "user", content = question),
                    )
                )
            )
            Log.d("BUGHUNT", "The code ${call.code()}, message ${call.message()}")
            Log.d("BUGHUNT", "The response ${call.body()!!}")
            call.body()!!
        }
}
