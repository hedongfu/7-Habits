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

package com.herman.habits.core.ui.widgets;

import android.support.annotation.*;

import com.herman.habits.core.commands.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.ui.*;

import javax.inject.*;

public class WidgetBehavior
{
    private HabitList habitList;

    @NonNull
    private final CommandRunner commandRunner;

    private NotificationTray notificationTray;

    @Inject
    public WidgetBehavior(@NonNull HabitList habitList,
                          @NonNull CommandRunner commandRunner,
                          @NonNull NotificationTray notificationTray)
    {
        this.habitList = habitList;
        this.commandRunner = commandRunner;
        this.notificationTray = notificationTray;
    }

    public void onAddRepetition(@NonNull Habit habit, Timestamp timestamp)
    {
        notificationTray.cancel(habit);
        Repetition rep = habit.getRepetitions().getByTimestamp(timestamp);
        if (rep != null) return;
        performToggle(habit, timestamp);
    }

    public void onRemoveRepetition(@NonNull Habit habit, Timestamp timestamp)
    {
        notificationTray.cancel(habit);
        Repetition rep = habit.getRepetitions().getByTimestamp(timestamp);
        if (rep == null) return;
        performToggle(habit, timestamp);
    }

    public void onToggleRepetition(@NonNull Habit habit, Timestamp timestamp)
    {
        performToggle(habit, timestamp);
    }

    private void performToggle(@NonNull Habit habit, Timestamp timestamp)
    {
        commandRunner.execute(
            new ToggleRepetitionCommand(habitList, habit, timestamp),
            habit.getId());
    }
}
