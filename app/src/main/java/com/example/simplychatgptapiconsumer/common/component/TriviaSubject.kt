package com.example.simplychatgptapiconsumer.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplychatgptapiconsumer.R

@Composable
fun TriviaSubject(
    value: String,
    errorState: Boolean,
    onValueChange: (String) -> Unit,
    onSend: KeyboardActionScope.() -> Unit,
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
        keyboardActions = KeyboardActions(onSend = onSend),
    )
}

@Preview()
@Composable
fun TriviaSubjectCorrectInputPreview() {
    TriviaSubject(
        value = "Subject",
        errorState = false,
        onValueChange = {},
        onSend = {},
    )
}

@Preview()
@Composable
fun TriviaSubjectTooLongInputPreview() {
    TriviaSubject(
        value = "Subject",
        errorState = true,
        onValueChange = {},
        onSend = {},
    )
}
