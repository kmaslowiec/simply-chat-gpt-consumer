package com.example.simplychatgptapiconsumer.main

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplychatgptapiconsumer.AppContainer
import com.example.simplychatgptapiconsumer.MainActivity
import com.example.simplychatgptapiconsumer.main.view.MainScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun mainSurfaceFillsWholeScreen() {
        val density = composeTestRule.density.density
        val widthDp = (composeTestRule.activity.applicationContext.resources.displayMetrics.widthPixels / density).dp
        val heightDp = (composeTestRule.activity.applicationContext.resources.displayMetrics.heightPixels / density).dp
        composeTestRule.activity.setContent {
            AppContainer { MainScreen(hiltViewModel()) }
        }

        composeTestRule.onNodeWithTag("MainSurface")
            .assertExists()
            .assertIsDisplayed()
            .assertWidthIsEqualTo(widthDp)
            .assertHeightIsEqualTo(heightDp)
    }
}
