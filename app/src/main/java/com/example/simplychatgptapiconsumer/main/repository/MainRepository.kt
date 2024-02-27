package com.example.simplychatgptapiconsumer.main.repository

import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.apimodel.ChatRequest
import com.example.simplychatgptapiconsumer.common.apimodel.Message
import com.example.simplychatgptapiconsumer.common.constant.SYSTEM_INSTRUCTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ChatGptApi) {

    suspend fun getQuestions(question: String) =
        runCatching {
            withContext(Dispatchers.IO) {
                api.getChatCompletions(
                    ChatRequest(
                        messages = listOf(
                            Message(role = "system", content = SYSTEM_INSTRUCTION),
                            Message(role = "user", content = question),
                        )
                    )
                )
            }
        }
}
