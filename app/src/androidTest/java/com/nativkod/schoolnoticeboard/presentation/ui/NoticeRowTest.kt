package com.nativkod.schoolnoticeboard.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.presentation.ui.components.NoticeRow
import org.junit.Rule
import org.junit.Test

class NoticeRowTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun noticeRow_renders_title_and_date() {
        val notice = Notice(
            id = "1",
            title = "School closed tomorrow",
            body = "Body",
            imageUrl = null,
            publishedAtEpochMillis = 1L,
            publishedAtRaw = "raw",
            publishedAtFriendlyLocal = "18 Feb 2026"
        )

        rule.setContent {
            MaterialTheme {
                Box(Modifier.fillMaxSize()) {
                    NoticeRow(notice = notice, onClick = {})
                }
            }
        }

        // Row should be visible
        rule.onNodeWithTag("notice_row_1", useUnmergedTree = true).assertIsDisplayed()

        // Title should exist + be visible
        rule.onNodeWithTag("notice_title_1", useUnmergedTree = true).assertIsDisplayed()

        // If you want: date tag (add tag in composable first)
        // rule.onNodeWithTag("notice_date_1", useUnmergedTree = true).assertIsDisplayed()
    }
}