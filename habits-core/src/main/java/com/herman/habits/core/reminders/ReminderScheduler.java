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

package com.herman.habits.core.reminders;

import android.support.annotation.*;

import com.herman.habits.core.*;
import com.herman.habits.core.commands.*;
import com.herman.habits.core.models.*;

import javax.inject.*;

import static com.herman.habits.core.utils.DateUtils.*;

@AppScope
public class ReminderScheduler implements CommandRunner.Listener
{
    private CommandRunner commandRunner;

    private HabitList habitList;

    private SystemScheduler sys;

    @Inject
    public ReminderScheduler(@NonNull CommandRunner commandRunner,
                             @NonNull HabitList habitList,
                             @NonNull SystemScheduler sys)
    {
        this.commandRunner = commandRunner;
        this.habitList = habitList;
        this.sys = sys;
    }

    @Override
    public void onCommandExecuted(@NonNull Command command,
                                  @Nullable Long refreshKey)
    {
        if (command instanceof ToggleRepetitionCommand) return;
        if (command instanceof ChangeHabitColorCommand) return;
        scheduleAll();
    }

    public void schedule(@NonNull Habit habit)
    {
        if (!habit.hasReminder()) return;
        Long reminderTime = habit.getReminder().getTimeInMillis();
        scheduleAtTime(habit, reminderTime);
    }

    public void scheduleAtTime(@NonNull Habit habit, @NonNull Long reminderTime)
    {
        if (reminderTime == null) throw new IllegalArgumentException();
        if (!habit.hasReminder()) return;
        if (habit.isArchived()) return;
        long timestamp = getStartOfDay(removeTimezone(reminderTime));
        sys.scheduleShowReminder(reminderTime, habit, timestamp);
    }

    public synchronized void scheduleAll()
    {
        HabitList reminderHabits =
            habitList.getFiltered(HabitMatcher.WITH_ALARM);
        for (Habit habit : reminderHabits)
            schedule(habit);
    }

    public void startListening()
    {
        commandRunner.addListener(this);
    }

    public void stopListening()
    {
        commandRunner.removeListener(this);
    }

    public void scheduleMinutesFromNow(Habit habit, long minutes)
    {
        long now = applyTimezone(getLocalTime());
        long reminderTime = now + minutes * 60 * 1000;
        scheduleAtTime(habit, reminderTime);
    }

    public interface SystemScheduler
    {
        void scheduleShowReminder(long reminderTime, Habit habit, long timestamp);
    }
}
