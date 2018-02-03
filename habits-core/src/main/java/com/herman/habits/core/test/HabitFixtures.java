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

package com.herman.habits.core.test;

import com.herman.habits.core.models.*;
import com.herman.habits.core.models.sqlite.*;
import com.herman.habits.core.utils.*;

public class HabitFixtures
{
    public boolean NON_DAILY_HABIT_CHECKS[] = {
        true, false, false, true, true, true, false, false, true, true
    };

    private final ModelFactory modelFactory;

    private HabitList habitList;

    public HabitFixtures(ModelFactory modelFactory, HabitList habitList)
    {
        this.modelFactory = modelFactory;
        this.habitList = habitList;
    }

    public Habit createEmptyHabit()
    {
        Habit habit = modelFactory.buildHabit();
        habit.setName("Meditate");
        habit.setDescription("Did you meditate this morning?");
        habit.setColor(3);
        habit.setFrequency(Frequency.DAILY);
        saveIfSQLite(habit);

        return habit;
    }

    public Habit createLongHabit()
    {
        Habit habit = createEmptyHabit();
        habit.setFrequency(new Frequency(3, 7));
        habit.setColor(4);

        Timestamp today = DateUtils.getToday();
        int marks[] = { 0, 1, 3, 5, 7, 8, 9, 10, 12, 14, 15, 17, 19, 20, 26, 27,
            28, 50, 51, 52, 53, 54, 58, 60, 63, 65, 70, 71, 72, 73, 74, 75, 80,
            81, 83, 89, 90, 91, 95, 102, 103, 108, 109, 120};

        for (int mark : marks)
            habit.getRepetitions().toggle(today.minus(mark));

        return habit;
    }

    public Habit createNumericalHabit()
    {
        Habit habit = modelFactory.buildHabit();
        habit.setType(Habit.NUMBER_HABIT);
        habit.setName("Run");
        habit.setDescription("How many miles did you run today?");
        habit.setUnit("miles");
        habit.setTargetType(Habit.AT_LEAST);
        habit.setTargetValue(2.0);
        habit.setColor(1);
        saveIfSQLite(habit);

        Timestamp today = DateUtils.getToday();
        int times[] = { 0, 1, 3, 5, 7, 8, 9, 10 };
        int values[] = { 100, 200, 300, 400, 500, 600, 700, 800 };

        for(int i = 0; i < times.length; i++)
        {
            Timestamp timestamp = today.minus(times[i]);
            habit.getRepetitions().add(new Repetition(timestamp, values[i]));
        }

        return habit;
    }

    public Habit createShortHabit()
    {
        Habit habit = modelFactory.buildHabit();
        habit.setName("Wake up early");
        habit.setDescription("Did you wake up before 6am?");
        habit.setFrequency(new Frequency(2, 3));
        saveIfSQLite(habit);

        Timestamp timestamp = DateUtils.getToday();
        for (boolean c : NON_DAILY_HABIT_CHECKS)
        {
            if (c) habit.getRepetitions().toggle(timestamp);
            timestamp = timestamp.minus(1);
        }

        return habit;
    }

    private void saveIfSQLite(Habit habit)
    {
        if (!(habit.getRepetitions() instanceof SQLiteRepetitionList)) return;
        habitList.add(habit);
    }
}
