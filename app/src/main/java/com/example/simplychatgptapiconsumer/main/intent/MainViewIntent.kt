package com.example.simplychatgptapiconsumer.main.intent

import com.example.simplychatgptapiconsumer.common.mvicomponents.MviIntent

sealed class MainViewIntent : MviIntent {
    data object AskChat : MainViewIntent()
}
