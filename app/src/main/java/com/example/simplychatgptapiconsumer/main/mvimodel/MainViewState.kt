package com.example.simplychatgptapiconsumer.main.mvimodel

import com.example.simplychatgptapiconsumer.common.mvicomponents.MviState
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.IDLE

data class MainViewState(
    val chatResponse: String = "",
    val triviaSubject: String = "",
    val isTriviaSubjectTooLong: Boolean = false,
    override val type: ResponseType = IDLE
) : MviState()
