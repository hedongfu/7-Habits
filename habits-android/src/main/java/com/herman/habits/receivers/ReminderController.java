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

package com.herman.habits.receivers;

import android.content.*;
import android.net.*;
import androidx.annotation.NonNull;

import com.herman.habits.core.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.preferences.*;
import com.herman.habits.core.reminders.*;
import com.herman.habits.core.ui.*;
import com.herman.habits.core.utils.*;
import com.herman.habits.notifications.*;

import javax.inject.*;

@AppScope
public class ReminderController
{
    @NonNull
    private final ReminderScheduler reminderScheduler;

    @NonNull
    private final NotificationTray notificationTray;

    @NonNull
    private Preferences preferences;

    @Inject
    public ReminderController(@NonNull ReminderScheduler reminderScheduler,
                              @NonNull NotificationTray notificationTray,
                              @NonNull Preferences preferences)
    {
        this.reminderScheduler = reminderScheduler;
        this.notificationTray = notificationTray;
        this.preferences = preferences;
    }

    public void onBootCompleted()
    {
        reminderScheduler.scheduleAll();
    }

    public void onShowReminder(@NonNull Habit habit,
                               Timestamp timestamp,
                               long reminderTime)
    {
        notificationTray.show(habit, timestamp, reminderTime);
        reminderScheduler.scheduleAll();
    }

    public void onSnoozePressed(@NonNull Habit habit, final Context context)
    {
        showSnoozeDelayPicker(habit, context);
    }

    public void onSnoozeDelayPicked(Habit habit, int delayInMinutes)
    {
        reminderScheduler.snoozeReminder(habit, delayInMinutes);
        notificationTray.cancel(habit);
    }

    public void onSnoozeTimePicked(Habit habit, int hour, int minute)
    {
        long time = DateUtils.getUpcomingTimeInMillis(hour, minute);
        reminderScheduler.scheduleAtTime(habit, time);
        notificationTray.cancel(habit);
    }

    public void onDismiss(@NonNull Habit habit)
    {
        notificationTray.cancel(habit);
    }

    private void showSnoozeDelayPicker(@NonNull Habit habit, Context context)
    {
        context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        Intent intent = new Intent(context, SnoozeDelayPickerActivity.class);
        intent.setData(Uri.parse(habit.getUriString()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
