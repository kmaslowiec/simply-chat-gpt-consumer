package com.example.simplychatgptapiconsumer.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.simplychatgptapiconsumer.common.component.ResultText
import com.example.simplychatgptapiconsumer.common.component.TriviaSubject
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Error
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Loading
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Success


@Composable
fun MainScreen(viewModel: MainViewModel) {
    var textState by rememberSaveable { mutableStateOf("") }
    var errorState by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState(0)

    val controller = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier.fillMaxSize()) {
        TriviaSubject(
            textState, errorState = errorState,
            onValueChange = {
                textState = it
                errorState = textState.length > 30
            },
            onSend = {
                viewModel.getResponse(textState)
                textState = ""
                controller?.hide()
            },
        )
        when (val state = viewModel.questionsState.collectAsState().value) {
            is Loading -> {}//TODO add loader
            is Success -> {
                Column(
                    Modifier.padding(
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ).border(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(4.dp),
                    )
                ) {
                    ResultText(
                        content = state.questions.content,
                        modifier = Modifier.padding(4.dp)
                            .verticalScroll(scrollState),
                    )
                }
            }

            is Error -> {} //TODO handle error
        }
    }
}
