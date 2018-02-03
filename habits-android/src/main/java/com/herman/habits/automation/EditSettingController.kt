/*
 * Copyright (C) 2015-2018 Dongfu He <hedongfu@gmail.com>
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

package com.herman.habits.automation

import android.app.*
import android.content.*
import android.os.*
import com.herman.habits.*
import com.herman.habits.automation.FireSettingReceiver.*
import com.herman.habits.core.models.*

class EditSettingController(private val activity: Activity) {

    fun onSave(habit: Habit, action: Int) {
        if (habit.getId() == null) return
        val actionName = getActionName(action)
        val blurb = String.format("%s: %s", actionName, habit.name)

        val bundle = Bundle()
        bundle.putInt("action", action)
        bundle.putLong("habit", habit.getId()!!)

        activity.setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(EXTRA_STRING_BLURB, blurb)
            putExtra(EXTRA_BUNDLE, bundle)
        })
        activity.finish()
    }

    private fun getActionName(action: Int): String {
        when (action) {
            ACTION_CHECK -> return activity.getString(R.string.check)
            ACTION_UNCHECK -> return activity.getString(R.string.uncheck)
            ACTION_TOGGLE -> return activity.getString(R.string.toggle)
            else -> return "???"
        }
    }
}
