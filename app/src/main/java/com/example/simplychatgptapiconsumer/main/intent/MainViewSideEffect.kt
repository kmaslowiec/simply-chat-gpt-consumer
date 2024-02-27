package com.example.simplychatgptapiconsumer.main.intent

sealed class MainViewSideEffect() {

    data class ShowSnackBar(val text: String) : MainViewSideEffect()
}
