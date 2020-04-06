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

package com.herman.habits.core.models.memory;

import com.herman.habits.core.database.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.models.sqlite.records.*;

public class MemoryModelFactory implements ModelFactory
{
    @Override
    public CheckmarkList buildCheckmarkList(Habit habit)
    {
        return new MemoryCheckmarkList(habit);
    }

    @Override
    public HabitList buildHabitList()
    {
        return new MemoryHabitList();
    }

    @Override
    public RepetitionList buildRepetitionList(Habit habit)
    {
        return new MemoryRepetitionList(habit);
    }

    @Override
    public ScoreList buildScoreList(Habit habit)
    {
        return new MemoryScoreList(habit);
    }

    @Override
    public StreakList buildStreakList(Habit habit)
    {
        return new MemoryStreakList(habit);
    }

    @Override
    public Repository<HabitRecord> buildHabitListRepository()
    {
        throw new IllegalStateException();
    }

    @Override
    public Repository<RepetitionRecord> buildRepetitionListRepository()
    {
        throw new IllegalStateException();
    }
}
