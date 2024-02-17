package com.example.notes.feature_notes.presentation.addEditNote

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.example.notes.di.AppModule
import com.example.notes.feature_notes.domain.use_case.local.NotesUseCases
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import com.example.notes.feature_notes.presentation.MainActivity
import com.example.notes.feature_notes.utils.test.TestTags
import com.example.notes.notes_future.presentation.addEditNote.compose.AddEditPresentation
import com.example.notes.ui.theme.NotesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AddEditNotePresentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeRule.onNodeWithContentDescription("Add/Edit note").performClick()
    }

    @Test
    fun openAddEditPresentation_titleAndContentIsDisplayedAndSaveButtonIsNotDisplayed() {
        composeRule.onNodeWithTag(TestTags.TitleTextField_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ContentTextField_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.SaveNoteButton_TAG).assertIsNotDisplayed()
    }

    @Test
    fun checkIsEmptyTitleAndContent_isEmptyTitleAndContent() {
        composeRule.onNodeWithTag(TestTags.TitleTextField_TAG).assert(hasText(""))
        composeRule.onNodeWithTag(TestTags.ContentTextField_TAG).assert(hasText(""))
    }

    @Test
    fun enteredTestToTitleAndContent_isDisplayedSaveButtonAndEnteredContentToTextFields() {
        val contentText = "contentText"
        val titleText = "titleText"

        composeRule.onNodeWithTag(TestTags.TitleTextField_TAG).performTextInput(titleText)
        composeRule.onNodeWithTag(TestTags.ContentTextField_TAG).performTextInput(contentText)

        composeRule.onNodeWithTag(TestTags.TitleTextField_TAG).assert(hasText(titleText))
        composeRule.onNodeWithTag(TestTags.ContentTextField_TAG).assert(hasText(contentText))
        composeRule.onNodeWithTag(TestTags.SaveNoteButton_TAG).assertIsDisplayed()
    }

    @Test
    fun saveNote_isSavedNote() {
        val contentText = "contentText"
        val titleText = "titleText"

        composeRule.onNodeWithTag(TestTags.TitleTextField_TAG).performTextInput(titleText)
        composeRule.onNodeWithTag(TestTags.ContentTextField_TAG).performTextInput(contentText)
        composeRule.onNodeWithTag(TestTags.SaveNoteButton_TAG).performClick()

        composeRule.onNodeWithContentDescription("Add/Edit note").assertIsDisplayed()
    }

}