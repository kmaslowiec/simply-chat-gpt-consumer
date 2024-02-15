package com.example.simplychatgptapiconsumer.common.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

@Composable
fun ResultText(
    content: String,
    modifier: Modifier,
) {
    Text(
        text = content,
        modifier = modifier,
    )
}

@Preview
@Composable
fun ResultTextPreview() {
    ResultText(
        content = LoremIpsum(30)
            .values
            .toString(),
        modifier = Modifier,
    )
}
