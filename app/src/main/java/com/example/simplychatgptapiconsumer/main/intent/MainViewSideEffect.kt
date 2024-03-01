package com.example.simplychatgptapiconsumer.main.intent

import com.example.simplychatgptapiconsumer.common.mvicomponents.MviSideEffect

sealed class MainViewSideEffect() : MviSideEffect {

    data class ShowSnackBar(val text: String) : MainViewSideEffect()
}
