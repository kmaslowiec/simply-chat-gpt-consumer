package com.example.simplychatgptapiconsumer.main

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun textExists() {
        composeRule.setContent { Greeting("", Modifier) }
        composeRule.onNodeWithTag("MainTextField")
            .assertExists()
    }
}
