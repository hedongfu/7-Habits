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

package com.herman.habits.activities.habits.list

import android.view.*
import com.herman.androidbase.activities.*
import com.herman.habits.*
import com.herman.habits.core.preferences.*
import com.herman.habits.core.ui.*
import com.herman.habits.core.ui.screens.habits.list.*
import javax.inject.*

@ActivityScope
class ListHabitsMenu @Inject constructor(
        activity: BaseActivity,
        private val preferences: Preferences,
        private val themeSwitcher: ThemeSwitcher,
        private val behavior: ListHabitsMenuBehavior
) : BaseMenu(activity) {

    override fun onCreate(menu: Menu) {
        val nightModeItem = menu.findItem(R.id.actionToggleNightMode)
        val hideArchivedItem = menu.findItem(R.id.actionHideArchived)
        val hideCompletedItem = menu.findItem(R.id.actionHideCompleted)
        val addNumericalHabit = menu.findItem(R.id.actionCreateNumeralHabit)

        addNumericalHabit.isVisible = preferences.isDeveloper
        nightModeItem.isChecked = themeSwitcher.isNightMode
        hideArchivedItem.isChecked = !preferences.showArchived
        hideCompletedItem.isChecked = !preferences.showCompleted
    }

    override fun onItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionToggleNightMode -> {
                behavior.onToggleNightMode()
                return true
            }

            R.id.actionCreateBooleanHabit -> {
                behavior.onCreateBooleanHabit()
                return true
            }

            R.id.actionCreateNumeralHabit -> {
                behavior.onCreateNumericalHabit()
                return true
            }

            R.id.actionFAQ -> {
                behavior.onViewFAQ()
                return true
            }

            R.id.actionAbout -> {
                behavior.onViewAbout()
                return true
            }

            R.id.actionSettings -> {
                behavior.onViewSettings()
                return true
            }

            R.id.actionHideArchived -> {
                behavior.onToggleShowArchived()
                invalidate()
                return true
            }

            R.id.actionHideCompleted -> {
                behavior.onToggleShowCompleted()
                invalidate()
                return true
            }

            R.id.actionSortColor -> {
                behavior.onSortByColor()
                return true
            }

            R.id.actionSortManual -> {
                behavior.onSortByManually()
                return true
            }

            R.id.actionSortName -> {
                behavior.onSortByName()
                return true
            }

            R.id.actionSortScore -> {
                behavior.onSortByScore()
                return true
            }

            else -> return false
        }
    }

    override fun getMenuResourceId() = R.menu.list_habits
}
