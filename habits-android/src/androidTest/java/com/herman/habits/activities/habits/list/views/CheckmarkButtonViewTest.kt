/*
 * Copyright (C) 2018 Dongfu He <hedongfu@gmail.com>
 *
 * This file is part of 7-Habit Tracker.
 *
 * 7-Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * 7-Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.herman.habits.activities.habits.list.views

import android.support.test.filters.*
import android.support.test.runner.*
import com.herman.habits.*
import com.herman.habits.core.models.*
import com.herman.habits.utils.*
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
@MediumTest
class CheckmarkButtonViewTest : BaseViewTest() {

    private val PATH = "habits/list/CheckmarkButtonView"
    lateinit var view: CheckmarkButtonView

    var toggled = false

    @Before
    override fun setUp() {
        super.setUp()
        view = component.getCheckmarkButtonViewFactory().create().apply {
            value = Checkmark.UNCHECKED
            color = PaletteUtils.getAndroidTestColor(5)
            onToggle = { toggled = true }
        }
        measureView(view, dpToPixels(48), dpToPixels(48))
    }

    @Test
    fun testRender_explicitCheck() {
        view.value = Checkmark.CHECKED_EXPLICITLY
        assertRendersCheckedExplicitly()
    }

    @Test
    fun testRender_implicitCheck() {
        view.value = Checkmark.CHECKED_IMPLICITLY
        assertRendersCheckedImplicitly()
    }

    @Test
    fun testRender_unchecked() {
        view.value = Checkmark.UNCHECKED
        assertRendersUnchecked()
    }

    @Test
    fun testClick_withShortToggleDisabled() {
        prefs.isShortToggleEnabled = false
        view.performClick()
        assertFalse(toggled)
    }

    @Test
    fun testClick_withShortToggleEnabled() {
        prefs.isShortToggleEnabled = true
        view.performClick()
        assertTrue(toggled)
    }

    @Test
    fun testLongClick() {
        view.performLongClick()
        assertTrue(toggled)
    }

    private fun assertRendersCheckedExplicitly() {
        assertRenders(view, "$PATH/render_explicit_check.png")
    }

    private fun assertRendersCheckedImplicitly() {
        assertRenders(view, "$PATH/render_implicit_check.png")
    }

    private fun assertRendersUnchecked() {
        assertRenders(view, "$PATH/render_unchecked.png")
    }
}