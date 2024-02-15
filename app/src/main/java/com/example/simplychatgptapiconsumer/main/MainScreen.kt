package com.example.simplychatgptapiconsumer.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.simplychatgptapiconsumer.R
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState


@Composable
fun MainScreen(viewModel: MainViewModel) {
    var textState by remember { mutableStateOf("") }
    var errorState by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        TriviaSubject(
            textState, errorState = errorState,
            onValueChange = {
                textState = it
                errorState = textState.length > 30
            },
        )
        when (val state = viewModel.questionsState.collectAsState().value) {
            is ChatAnswerState.Loading -> {} //TODO add loader
            is ChatAnswerState.Success -> {} //TODO add chat result
            is ChatAnswerState.Error -> {} //TODO handle error
        }
    }
}

@Composable
fun TriviaSubject(
    value: String,
    errorState: Boolean,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(R.string.mainScreenTriviaSubjectLabelText)) },
        modifier = Modifier
            .testTag(stringResource(R.string.mainScreenTriviaSubjectTestTag))
            .wrapContentSize()
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        isError = errorState,
        supportingText = {
            if (errorState) {
                Text(
                    stringResource(R.string.mainScreenTriviaSubjectSupportingText),
                    color = Color.Red
                )
            }
        },
        keyboardOptions = KeyboardOptions().copy(
            imeAction = ImeAction.Send,
            keyboardType = KeyboardType.Text
        ),
    )
}
