package com.example.simplychatgptapiconsumer.main

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.simplychatgptapiconsumer.common.component.TriviaSubject
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun textChangeWHENTextIsWritten() {
        composeRule.setContent {
            TriviaSubject(
                value = "Some text",
                errorState = false,
                onValueChange = {},
                onSend = {},
            )
        }
        composeRule.onNodeWithTag("Trivia subject").performTextInput("Some text")

        composeRule.onNodeWithTag("Trivia subject").assertExists()
        composeRule.onNodeWithTag("Trivia subject").assert(hasText("Some text"))
    }

    @Test
    fun showsErrorMessageWHENTextIsTooLong() {
        composeRule.setContent {
            TriviaSubject(
                value = "",
                errorState = true,
                onValueChange = {},
                onSend = {},
            )
        }
        composeRule.onNodeWithTag("Trivia subject").performTextInput("This is way too long text that cause the error")

        composeRule.onNodeWithText("Too long subject").assertExists()
    }

    @Test
    fun doesNotShowsErrorMessageWHENTextIsShortEnough() {
        composeRule.setContent {
            TriviaSubject(
                value = "",
                errorState = false,
                onValueChange = {},
                onSend = {},
            )
        }
        composeRule.onNodeWithTag("Trivia subject").performTextInput("Some short text")

        composeRule.onNodeWithText("Too long subject").assertDoesNotExist()
    }
}
