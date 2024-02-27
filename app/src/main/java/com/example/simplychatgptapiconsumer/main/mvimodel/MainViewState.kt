package com.example.simplychatgptapiconsumer.main.mvimodel

import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.IDLE

data class MainViewState(
    val chatResponse: String = "",
    val triviaSubject: String = "",
    val type: ResponseType = IDLE
)
