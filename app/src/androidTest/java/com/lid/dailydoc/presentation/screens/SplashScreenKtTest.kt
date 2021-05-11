package com.lid.dailydoc.presentation.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lid.dailydoc.MainActivity
import org.junit.Assert.*

import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun splash_screen_with_app_name_exists() {
        composeTestRule.onNodeWithText("Daily Doc").assertExists()
    }
}