/*
 * Copyright (C) 2018 Herman <ringtone.sky@gmail.com>
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

import android.content.*
import com.google.auto.factory.*
import com.herman.androidbase.activities.*
import com.herman.habits.core.models.*
import com.herman.habits.core.models.Checkmark.*
import com.herman.habits.core.preferences.*
import com.herman.habits.core.utils.*

@AutoFactory
class CheckmarkPanelView(
        @Provided @ActivityContext context: Context,
        @Provided preferences: Preferences,
        @Provided private val buttonFactory: CheckmarkButtonViewFactory
) : ButtonPanelView<CheckmarkButtonView>(context, preferences) {

    var values = IntArray(0)
        set(values) {
            field = values
            setupButtons()
        }

    var color = 0
        set(value) {
            field = value
            setupButtons()
        }

    var onToggle: (Timestamp) -> Unit = {}
        set(value) {
            field = value
            setupButtons()
        }

    override fun createButton(): CheckmarkButtonView = buttonFactory.create()

    @Synchronized
    override fun setupButtons() {
        val today = DateUtils.getToday()

        buttons.forEachIndexed { index, button ->
            val timestamp = today.minus(index + dataOffset)
            button.value = when {
                index + dataOffset < values.size -> values[index + dataOffset]
                else -> UNCHECKED
            }
            button.color = color
            button.onToggle = { onToggle(timestamp) }
        }
    }
}
