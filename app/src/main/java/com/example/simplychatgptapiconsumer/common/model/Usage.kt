package com.example.simplychatgptapiconsumer.common.model

data class Usage(
    val completion_tokens: Int,
    val prompt_tokens: Int,
    val total_tokens: Int
)