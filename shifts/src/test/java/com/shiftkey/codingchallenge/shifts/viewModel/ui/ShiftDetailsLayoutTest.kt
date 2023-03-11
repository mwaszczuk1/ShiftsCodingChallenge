package com.shiftkey.codingchallenge.shifts.viewModel.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.shiftkey.codingchallenge.design.theme.ShiftKeyTheme
import com.shiftkey.codingchallenge.shifts.details.ShiftDetailsLayout
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
// workaround for https://github.com/robolectric/robolectric/issues/6593
@Config(instrumentedPackages = ["androidx.loader.content"])
class ShiftDetailsLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPremiumInfoVisibility() {
        composeTestRule.setContent {
            ShiftKeyTheme {
                ShiftDetailsLayout(
                    shift = shiftDetails.copy(
                        shift = shiftDomain.copy(premiumRate = true))
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("premium_shift").assertExists()
    }
}
