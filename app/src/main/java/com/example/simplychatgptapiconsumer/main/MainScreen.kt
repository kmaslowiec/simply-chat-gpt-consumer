package com.example.simplychatgptapiconsumer.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Error
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Loading
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Success

@Composable
fun MainScreen(viewModel: MainViewModel) {

    when (val state = viewModel.questionsState.collectAsState().value) {
        is Loading -> {}
        is Success -> {
            Greeting(state.questions.content)
        }

        is Error -> {}
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "ChatGtp says: $name!",
        modifier = modifier
    )
}
