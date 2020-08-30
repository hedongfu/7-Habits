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

package com.herman.habits.activities

import android.content.*
import android.os.*
import com.herman.androidbase.activities.*
import com.herman.habits.*
import com.herman.habits.core.models.*

abstract class HabitsActivity : BaseActivity() {
    lateinit var component: HabitsActivityComponent
    lateinit var appComponent: HabitsApplicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent = (applicationContext as HabitsApplication).component

        val habit = getHabitFromIntent(appComponent.habitList)
                    ?: appComponent.modelFactory.buildHabit()

        component = DaggerHabitsActivityComponent
                .builder()
                .activityContextModule(ActivityContextModule(this))
                .baseActivityModule(BaseActivityModule(this))
                .habitModule(HabitModule(habit))
                .habitsApplicationComponent(appComponent)
                .build()

        component.themeSwitcher.apply()
    }

    private fun getHabitFromIntent(habitList: HabitList): Habit? {
        val data = intent.data ?: return null
        val habit = habitList.getById(ContentUris.parseId(data))
                    ?: throw RuntimeException("habit not found")
        return habit
    }
}
