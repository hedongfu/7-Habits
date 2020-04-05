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
import android.util.*
import com.herman.androidbase.*
import com.herman.habits.*
import com.herman.habits.core.*
import com.herman.habits.core.models.*
import com.herman.habits.core.reminders.*
import com.herman.habits.core.utils.DateFormats
import java.util.*
import javax.inject.*

@AppScope
class IntentScheduler
@Inject constructor(
        @AppContext context: Context,
        private val pendingIntents: PendingIntentFactory
) : ReminderScheduler.SystemScheduler {

    private val manager =
            context.getSystemService(ALARM_SERVICE) as AlarmManager

    fun schedule(timestamp: Long, intent: PendingIntent) {
        Log.d("IntentScheduler",
              "timestamp=" + timestamp + " current=" + System.currentTimeMillis())
        if (timestamp < System.currentTimeMillis()) {
            Log.e("IntentScheduler",
                  "Ignoring attempt to schedule intent in the past.")
            return;
        }
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
        logReminderScheduled(habit, reminderTime)
    }

    override fun log(componentName: String, msg: String) {
        Log.d(componentName, msg)
    }

    private fun logReminderScheduled(habit: Habit, reminderTime: Long) {
        val min = Math.min(5, habit.name.length)
        val name = habit.name.substring(0, min)
        val df = DateFormats.getBackupDateFormat()
        val time = df.format(Date(reminderTime))
        Log.i("ReminderHelper",
              String.format("Setting alarm (%s): %s", time, name))
    }
}
