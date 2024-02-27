package com.example.simplychatgptapiconsumer.main.intent

sealed class MainViewIntent {
    data object AskChat : MainViewIntent()
}
