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

package com.herman.habits.core.commands;

import android.support.annotation.*;

import com.herman.habits.core.models.*;

/**
 * Command to toggle a repetition.
 */
public class ToggleRepetitionCommand extends Command
{
    @NonNull
    private HabitList list;

    final Timestamp timestamp;

    @NonNull
    final Habit habit;

    public ToggleRepetitionCommand(@NonNull HabitList list,
                                   @NonNull Habit habit,
                                   Timestamp timestamp)
    {
        super();
        this.list = list;
        this.timestamp = timestamp;
        this.habit = habit;
    }

    @Override
    public void execute()
    {
        habit.getRepetitions().toggle(timestamp);
        list.update(habit);
    }

    @NonNull
    public Habit getHabit()
    {
        return habit;
    }

    @Override
    @NonNull
    public Record toRecord()
    {
        return new Record(this);
    }

    @Override
    public void undo()
    {
        execute();
    }

    public static class Record
    {
        @NonNull
        public String id;

        @NonNull
        public String event = "Toggle";

        public long habit;

        public long repTimestamp;

        public Record(@NonNull ToggleRepetitionCommand command)
        {
            id = command.getId();
            Long habitId = command.habit.getId();
            if (habitId == null) throw new RuntimeException("Habit not saved");

            this.repTimestamp = command.timestamp.getUnixTime();
            this.habit = habitId;
        }

        public ToggleRepetitionCommand toCommand(@NonNull HabitList habitList)
        {
            Habit h = habitList.getById(habit);
            if (h == null) throw new HabitNotFoundException();

            ToggleRepetitionCommand command;
            command = new ToggleRepetitionCommand(
                habitList, h, new Timestamp(repTimestamp));
            command.setId(id);
            return command;
        }
    }
}