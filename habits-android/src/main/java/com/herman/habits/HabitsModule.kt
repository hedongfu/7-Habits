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

package com.herman.habits

import dagger.*
import com.herman.habits.core.*
import com.herman.habits.core.commands.*
import com.herman.habits.core.database.*
import com.herman.habits.core.models.*
import com.herman.habits.core.models.sqlite.*
import com.herman.habits.core.preferences.*
import com.herman.habits.core.reminders.*
import com.herman.habits.core.tasks.*
import com.herman.habits.core.ui.*
import com.herman.habits.database.*
import com.herman.habits.intents.*
import com.herman.habits.notifications.*
import com.herman.habits.preferences.*
import com.herman.habits.utils.*

@Module
class HabitsModule {
    @Provides
    @AppScope
    fun getPreferences(storage: SharedPreferencesStorage): Preferences {
        return Preferences(storage)
    }

    @Provides
    @AppScope
    fun getReminderScheduler(
            sys: IntentScheduler,
            commandRunner: CommandRunner,
            habitList: HabitList,
            widgetPreferences: WidgetPreferences
    ): ReminderScheduler {
        return ReminderScheduler(commandRunner, habitList, sys, widgetPreferences)
    }

    @Provides
    @AppScope
    fun getTray(
            taskRunner: TaskRunner,
            commandRunner: CommandRunner,
            preferences: Preferences,
            screen: AndroidNotificationTray
    ): NotificationTray {
        return NotificationTray(taskRunner, commandRunner, preferences, screen)
    }

    @Provides
    @AppScope
    fun getWidgetPreferences(
            storage: SharedPreferencesStorage
    ): WidgetPreferences {
        return WidgetPreferences(storage)
    }

    @Provides
    @AppScope
    fun getModelFactory(): ModelFactory {
        return SQLModelFactory(AndroidDatabase(DatabaseUtils.openDatabase()))
    }

    @Provides
    @AppScope
    fun getHabitList(list: SQLiteHabitList): HabitList {
        return list
    }

    @Provides
    @AppScope
    fun getDatabaseOpener(opener: AndroidDatabaseOpener): DatabaseOpener {
        return opener
    }
}

