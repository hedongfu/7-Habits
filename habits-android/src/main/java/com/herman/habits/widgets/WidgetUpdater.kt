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

package com.herman.habits.widgets

import android.appwidget.*
import android.content.*
import com.herman.androidbase.*
import com.herman.habits.core.commands.*
import com.herman.habits.core.tasks.*
import javax.inject.*

/**
 * A WidgetUpdater listens to the commands being executed by the application and
 * updates the home-screen widgets accordingly.
 */
class WidgetUpdater
@Inject constructor(
        @AppContext private val context: Context,
        private val commandRunner: CommandRunner,
        private val taskRunner: TaskRunner
) : CommandRunner.Listener {

    override fun onCommandExecuted(command: Command, refreshKey: Long?) {
        updateWidgets()
    }

    /**
     * Instructs the updater to start listening to commands. If any relevant
     * commands are executed after this method is called, the corresponding
     * widgets will get updated.
     */
    fun startListening() {
        commandRunner.addListener(this)
    }

    /**
     * Instructs the updater to stop listening to commands. Every command
     * executed after this method is called will be ignored by the updater.
     */
    fun stopListening() {
        commandRunner.removeListener(this)
    }

    fun updateWidgets() {
        taskRunner.execute {
            updateWidgets(CheckmarkWidgetProvider::class.java)
            updateWidgets(HistoryWidgetProvider::class.java)
            updateWidgets(ScoreWidgetProvider::class.java)
            updateWidgets(StreakWidgetProvider::class.java)
            updateWidgets(FrequencyWidgetProvider::class.java)
        }
    }

    fun updateWidgets(providerClass: Class<*>) {
        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(
                ComponentName(context, providerClass))
        context.sendBroadcast(Intent(context, providerClass).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        })
    }
}
