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

package com.herman.habits.intents

import android.app.*
import android.app.AlarmManager.*
import android.content.*
import android.content.Context.*
import android.os.Build.VERSION.*
import android.os.Build.VERSION_CODES.*
import com.herman.androidbase.*
import com.herman.habits.*
import com.herman.habits.core.*
import com.herman.habits.core.models.*
import com.herman.habits.core.reminders.*
import javax.inject.*

@AppScope
class IntentScheduler
@Inject constructor(
        @AppContext context: Context,
        private val pendingIntents: PendingIntentFactory,
        private val logger: HabitLogger
) : ReminderScheduler.SystemScheduler {

    private val manager =
            context.getSystemService(ALARM_SERVICE) as AlarmManager

    fun schedule(timestamp: Long, intent: PendingIntent) {
        if (SDK_INT >= M)
            manager.setExactAndAllowWhileIdle(RTC_WAKEUP, timestamp, intent)
        else
            manager.setExact(RTC_WAKEUP, timestamp, intent)
    }

    override fun scheduleShowReminder(reminderTime: Long,
                                      habit: Habit,
                                      timestamp: Long) {
        val intent = pendingIntents.showReminder(habit, reminderTime, timestamp)
        schedule(reminderTime, intent)
        logger.logReminderScheduled(habit, reminderTime)
    }
}
