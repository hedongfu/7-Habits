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

import java.util.*;

/**
 * Command to delete a list of habits.
 */
public class DeleteHabitsCommand extends Command
{
    @NonNull
    final HabitList habitList;

    @NonNull
    final List<Habit> selected;

    public DeleteHabitsCommand(@NonNull HabitList habitList,
                               @NonNull List<Habit> selected)
    {
        this.selected = new LinkedList<>(selected);
        this.habitList = habitList;
    }


    @Override
    public void execute()
    {
        for (Habit h : selected)
            habitList.remove(h);
    }

    public List<Habit> getSelected()
    {
        return Collections.unmodifiableList(selected);
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
        throw new UnsupportedOperationException();
    }

    public static class Record
    {
        @NonNull
        public String id;

        @NonNull
        public String event = "DeleteHabit";

        @NonNull
        public List<Long> habits;

        public Record(DeleteHabitsCommand command)
        {
            id = command.getId();
            habits = new LinkedList<>();
            for (Habit h : command.selected)
            {
                if (!h.hasId()) throw new RuntimeException("Habit not saved");
                habits.add(h.getId());
            }
        }

        public DeleteHabitsCommand toCommand(@NonNull HabitList habitList)
        {
            List<Habit> selected = new LinkedList<>();
            for (Long id : this.habits) selected.add(habitList.getById(id));

            DeleteHabitsCommand command;
            command = new DeleteHabitsCommand(habitList, selected);
            command.setId(id);
            return command;
        }
    }
}
