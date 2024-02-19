package com.example.simplychatgptapiconsumer.common.api

import com.example.simplychatgptapiconsumer.common.model.ChatRequest
import com.example.simplychatgptapiconsumer.common.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ChatGptApi {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatCompletions(@Body chatRequest: ChatRequest): ChatResponse
}
