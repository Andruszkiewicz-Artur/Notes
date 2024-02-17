package com.example.notes.feature_notes.presentation.notes

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.core.util.graph.Screen
import com.example.notes.di.AppModule
import com.example.notes.feature_notes.presentation.MainActivity
import com.example.notes.feature_notes.utils.test.TestTags
import com.example.notes.notes_future.presentation.notes.compose.NotesPresentation
import com.example.notes.ui.theme.NotesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesPresentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun checkListIsEmpty_isEmpty() {
        composeRule.onNodeWithTag(TestTags.IsEmptyListText_TAG).assertIsDisplayed()
    }

    @Test
    fun clickOneTimeChangeTypeOfPresentingIconOfGrid_isChanged() {
        composeRule.onNodeWithTag(TestTags.FlatCell_TAG).assertIsNotDisplayed()
        composeRule.onNodeWithTag(TestTags.GridCell_TAG).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.GridCell_TAG).performClick()

        composeRule.onNodeWithTag(TestTags.FlatCell_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.GridCell_TAG).assertIsNotDisplayed()
    }

}