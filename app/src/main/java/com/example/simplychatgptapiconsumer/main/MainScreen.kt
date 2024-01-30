package com.example.simplychatgptapiconsumer.main

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
    val scrollState = rememberScrollState(0)
    Text(
        text = "ChatGtp says: $name",
        modifier = modifier.verticalScroll(state = scrollState, enabled = true)
            .testTag("MainTextField")
    )
}
