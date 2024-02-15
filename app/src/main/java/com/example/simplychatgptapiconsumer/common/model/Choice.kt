package com.example.simplychatgptapiconsumer.common.model

data class Choice(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val message: MessageResponse,
)