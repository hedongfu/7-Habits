/*
 * Copyright (C) 2016-2020 Álinson Santos Xavier <isoron@gmail.com>
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

package com.herman.habits.widgets

import android.content.*
import android.view.*
import com.herman.habits.core.models.*
import com.herman.habits.utils.*
import com.herman.habits.widgets.views.*

class CheckmarkWidget(
        context: Context,
        widgetId: Int,
        private val habit: Habit
) : BaseWidget(context, widgetId) {

    override fun getOnClickPendingIntent(context: Context) =
            pendingIntentFactory.toggleCheckmark(habit, null)

    override fun refreshData(v: View) {
        (v as CheckmarkWidgetView).apply {
            setBackgroundAlpha(preferedBackgroundAlpha)
            setPercentage(habit.scores.todayValue.toFloat())
            setActiveColor(PaletteUtils.getColor(context, habit.color))
            setName(habit.name)
            setCheckmarkValue(habit.checkmarks.todayValue)
            refresh()
        }
    }

    override fun buildView() = CheckmarkWidgetView(context)
    override fun getDefaultHeight() = 125
    override fun getDefaultWidth() = 125
}
