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

import dagger.*
import com.herman.androidbase.activities.*
import com.herman.habits.*
import com.herman.habits.activities.about.*
import com.herman.habits.activities.common.dialogs.*
import com.herman.habits.activities.habits.list.*
import com.herman.habits.activities.habits.list.views.*
import com.herman.habits.activities.habits.show.*
import com.herman.habits.core.ui.*
import com.herman.habits.core.ui.screens.habits.list.*

@ActivityScope
@Component(modules = arrayOf(
        ActivityContextModule::class,
        BaseActivityModule::class,
        AboutModule::class,
        HabitsActivityModule::class,
        ListHabitsModule::class,
        ShowHabitModule::class,
        HabitModule::class
), dependencies = arrayOf(HabitsApplicationComponent::class))
interface HabitsActivityComponent {
    val aboutRootView: AboutRootView
    val aboutScreen: AboutScreen
    val colorPickerDialogFactory: ColorPickerDialogFactory
    val habitCardListAdapter: HabitCardListAdapter
    val listHabitsBehavior: ListHabitsBehavior
    val listHabitsMenu: ListHabitsMenu
    val listHabitsRootView: ListHabitsRootView
    val listHabitsScreen: ListHabitsScreen
    val listHabitsSelectionMenu: ListHabitsSelectionMenu
    val showHabitScreen: ShowHabitScreen
    val themeSwitcher: AndroidThemeSwitcher
}
