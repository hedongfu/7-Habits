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
 *
 *
 */

package com.herman.habits.core.models.sqlite;

import androidx.annotation.NonNull;

import com.herman.habits.core.database.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.models.memory.*;
import com.herman.habits.core.models.sqlite.records.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * Implementation of a {@link RepetitionList} that is backed by SQLite.
 */
public class SQLiteRepetitionList extends RepetitionList
{
    private final Repository<RepetitionRecord> repository;

    private final MemoryRepetitionList list;

    private boolean loaded = false;

    public SQLiteRepetitionList(@NonNull Habit habit,
                                @NonNull ModelFactory modelFactory)
    {
        super(habit);
        repository = modelFactory.buildRepetitionListRepository();
        list = new MemoryRepetitionList(habit);
    }

    private void loadRecords()
    {
        if (loaded) return;
        loaded = true;

        check(habit.getId());
        List<RepetitionRecord> records =
            repository.findAll("where habit = ? order by timestamp",
                habit.getId().toString());

        for (RepetitionRecord rec : records)
            list.add(rec.toRepetition());
    }

    @Override
    public void add(Repetition rep)
    {
        loadRecords();
        list.add(rep);
        check(habit.getId());
        RepetitionRecord record = new RepetitionRecord();
        record.habit_id = habit.getId();
        record.copyFrom(rep);
        repository.save(record);
        observable.notifyListeners();
    }

    @Override
    public List<Repetition> getByInterval(Timestamp timeFrom, Timestamp timeTo)
    {
        loadRecords();
        return list.getByInterval(timeFrom, timeTo);
    }

    @Override
    @Nullable
    public Repetition getByTimestamp(Timestamp timestamp)
    {
        loadRecords();
        return list.getByTimestamp(timestamp);
    }

    @Override
    public Repetition getOldest()
    {
        loadRecords();
        return list.getOldest();
    }

    @Override
    public Repetition getNewest()
    {
        loadRecords();
        return list.getNewest();
    }

    @Override
    public void remove(@NonNull Repetition repetition)
    {
        loadRecords();
        list.remove(repetition);
        check(habit.getId());
        repository.execSQL(
            "delete from repetitions where habit = ? and timestamp = ?",
            habit.getId(), repetition.getTimestamp().getUnixTime());
        observable.notifyListeners();
    }

    public void removeAll()
    {
        loadRecords();
        list.removeAll();
        check(habit.getId());
        repository.execSQL("delete from repetitions where habit = ?",
            habit.getId());
    }

    @Override
    public long getTotalCount()
    {
        loadRecords();
        return list.getTotalCount();
    }

    public void reload()
    {
        loaded = false;
    }

    @Contract("null -> fail")
    private void check(Long value)
    {
        if (value == null) throw new RuntimeException("null check failed");
    }
}
