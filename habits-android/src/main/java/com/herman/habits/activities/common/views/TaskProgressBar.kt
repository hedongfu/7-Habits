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

package com.herman.habits.activities.common.views

import android.content.*
import android.widget.*
import com.herman.androidbase.activities.*
import com.herman.habits.core.tasks.*

class TaskProgressBar(
        context: Context,
        private val runner: TaskRunner
) : ProgressBar(
        context,
        null,
        android.R.attr.progressBarStyleHorizontal
), TaskRunner.Listener {

    init {
        visibility = BaseRootView.GONE
        isIndeterminate = true
    }

    override fun onTaskStarted(task: Task?) = update()
    override fun onTaskFinished(task: Task?) = update()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        runner.addListener(this)
        update()
    }

    override fun onDetachedFromWindow() {
        runner.removeListener(this)
        super.onDetachedFromWindow()
    }

    fun update() {
        val callback = {
            val activeTaskCount = runner.activeTaskCount
            val newVisibility = when (activeTaskCount) {
                0 -> GONE
                else -> VISIBLE
            }
            if (visibility != newVisibility) visibility = newVisibility
        }
        postDelayed(callback, 500)
    }
}
