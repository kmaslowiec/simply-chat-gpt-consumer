package com.example.simplychatgptapiconsumer.common.model

import com.example.simplychatgptapiconsumer.common.constant.CHAT_GPT_MODEL_ID
import com.example.simplychatgptapiconsumer.common.constant.CHAT_GPT_TEMPERATURE

data class ChatRequest(
    val messages: List<Message>,
    private val model: String = CHAT_GPT_MODEL_ID,
    private val temperature: Double = CHAT_GPT_TEMPERATURE,
)
