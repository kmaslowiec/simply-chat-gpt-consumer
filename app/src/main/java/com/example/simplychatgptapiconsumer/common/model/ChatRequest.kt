package com.example.simplychatgptapiconsumer.common.model

data class ChatRequest(
    val messages: List<Message>,
    private val model: String = "gpt-3.5-turbo",
    private val temperature: Double = 0.5
)
