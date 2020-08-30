/*
 * Copyright (C) 2016-2020 Álinson Santos Xavier <isoron@gmail.com>
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

package com.herman.habits;

import com.herman.habits.core.models.*;
import com.herman.habits.core.utils.DateUtils;

public class HabitFixtures
{
    public boolean LONG_HABIT_CHECKS[] = {
        true, false, false, true, true, true, false, false, true, true
    };

    public int LONG_NUMERICAL_HABIT_CHECKS[] = {
        200000, 0, 150000, 137000, 0, 0, 500000, 30000, 100000, 0, 300000,
        100000, 0, 100000
    };

    private ModelFactory modelFactory;

    private final HabitList habitList;

    public HabitFixtures(ModelFactory modelFactory, HabitList habitList)
    {
        this.modelFactory = modelFactory;
        this.habitList = habitList;
    }

    public Habit createEmptyHabit()
    {
        return createEmptyHabit(null);
    }

    public Habit createEmptyHabit(Long id)
    {
        Habit habit = modelFactory.buildHabit();
        habit.setName("Meditate");
        habit.setDescription("Did you meditate this morning?");
        habit.setColor(5);
        habit.setFrequency(Frequency.DAILY);
        habit.setId(id);
        habitList.add(habit);
        return habit;
    }

    public Habit createLongHabit()
    {
        Habit habit = createEmptyHabit();
        habit.setFrequency(new Frequency(3, 7));
        habit.setColor(7);

        Timestamp today = DateUtils.getToday();
        int marks[] = { 0, 1, 3, 5, 7, 8, 9, 10, 12, 14, 15, 17, 19, 20, 26, 27,
            28, 50, 51, 52, 53, 54, 58, 60, 63, 65, 70, 71, 72, 73, 74, 75, 80,
            81, 83, 89, 90, 91, 95, 102, 103, 108, 109, 120};

        for (int mark : marks)
            habit.getRepetitions().toggle(today.minus(mark));

        return habit;
    }

    public Habit createLongNumericalHabit()
    {
        Habit habit = modelFactory.buildHabit();
        habit.setName("Take a walk");
        habit.setDescription("How many steps did you walk today?");
        habit.setType(Habit.NUMBER_HABIT);
        habit.setTargetType(Habit.AT_LEAST);
        habit.setTargetValue(200.0);
        habit.setUnit("steps");
        habitList.add(habit);

        Timestamp timestamp = DateUtils.getToday();
        for (int value : LONG_NUMERICAL_HABIT_CHECKS)
        {
            Repetition r = new Repetition(timestamp, value);
            habit.getRepetitions().add(r);
            timestamp = timestamp.minus(1);
        }

        return habit;
    }

    public Habit createShortHabit()
    {
        Habit habit = modelFactory.buildHabit();
        habit.setName("Wake up early");
        habit.setDescription("Did you wake up before 6am?");
        habit.setFrequency(new Frequency(2, 3));
        habitList.add(habit);

        Timestamp timestamp = DateUtils.getToday();
        for (boolean c : LONG_HABIT_CHECKS)
        {
            if (c) habit.getRepetitions().toggle(timestamp);
            timestamp = timestamp.minus(1);
        }

        return habit;
    }

    public synchronized void purgeHabits(HabitList habitList)
    {
        habitList.removeAll();
    }
}
