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

import android.os.*

import com.herman.androidbase.activities.*
import com.herman.habits.*
import com.herman.habits.core.models.*

class EditSettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = applicationContext as HabitsApplication
        val habits = app.component.habitList.getFiltered(
                HabitMatcherBuilder()
                        .setArchivedAllowed(false)
                        .setCompletedAllowed(true)
                        .build())

        val controller = EditSettingController(this)
        val rootView = EditSettingRootView(this, habits, controller)
        val screen = BaseScreen(this)
        screen.setRootView(rootView)
        setScreen(screen)
    }
}
