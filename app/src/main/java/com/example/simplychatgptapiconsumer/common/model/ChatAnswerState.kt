package com.example.simplychatgptapiconsumer.common.model

sealed class ChatAnswerState {
    data object Loading : ChatAnswerState()
    data class Success(val questions: MessageResponse) : ChatAnswerState()
    data object Error : ChatAnswerState()
}
