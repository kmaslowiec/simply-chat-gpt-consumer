package com.example.simplychatgptapiconsumer.common.apimodel

data class ChatResponse(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage,
)