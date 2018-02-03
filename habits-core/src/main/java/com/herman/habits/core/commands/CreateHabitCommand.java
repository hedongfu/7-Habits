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

import com.google.auto.factory.*;

import com.herman.habits.core.models.*;

/**
 * Command to create a habit.
 */
@AutoFactory
public class CreateHabitCommand extends Command
{
    ModelFactory modelFactory;

    HabitList habitList;

    @NonNull
    Habit model;

    @Nullable
    Long savedId;

    public CreateHabitCommand(@Provided @NonNull ModelFactory modelFactory,
                              @NonNull HabitList habitList,
                              @NonNull Habit model)
    {
        this.modelFactory = modelFactory;
        this.habitList = habitList;
        this.model = model;
    }

    @Override
    public void execute()
    {
        Habit savedHabit = modelFactory.buildHabit();
        savedHabit.copyFrom(model);
        savedHabit.setId(savedId);

        habitList.add(savedHabit);
        savedId = savedHabit.getId();
    }

    @NonNull
    @Override
    public Record toRecord()
    {
        return new Record(this);
    }

    @Override
    public void undo()
    {
        if (savedId == null) throw new IllegalStateException();

        Habit habit = habitList.getById(savedId);
        if (habit == null) throw new HabitNotFoundException();

        habitList.remove(habit);
    }

    public static class Record
    {
        @NonNull
        public String id;

        @NonNull
        public String event = "CreateHabit";

        @NonNull
        public Habit.HabitData habit;

        @Nullable
        public Long savedId;

        public Record(CreateHabitCommand command)
        {
            id = command.getId();
            habit = command.model.getData();
            savedId = command.savedId;
        }

        public CreateHabitCommand toCommand(@NonNull ModelFactory modelFactory,
                                            @NonNull HabitList habitList)
        {
            Habit h = modelFactory.buildHabit(habit);

            CreateHabitCommand command;
            command = new CreateHabitCommand(modelFactory, habitList, h);
            command.savedId = savedId;
            command.setId(id);
            return command;
        }
    }
}