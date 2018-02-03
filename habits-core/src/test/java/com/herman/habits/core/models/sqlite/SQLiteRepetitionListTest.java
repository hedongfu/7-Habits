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

package com.herman.habits.core.models.sqlite;

import android.support.annotation.*;

import com.herman.habits.core.*;
import com.herman.habits.core.database.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.models.sqlite.records.*;
import com.herman.habits.core.test.*;
import com.herman.habits.core.utils.*;
import org.junit.*;

import java.util.*;

import static junit.framework.TestCase.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsEqual.*;
import static com.herman.habits.core.models.Checkmark.*;

public class SQLiteRepetitionListTest extends BaseUnitTest
{
    private Habit habit;

    private Timestamp today;

    private RepetitionList repetitions;

    private long day;

    private Repository<RepetitionRecord> repository;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();

        Database db = buildMemoryDatabase();
        modelFactory = new SQLModelFactory(db);
        habitList = modelFactory.buildHabitList();
        fixtures = new HabitFixtures(modelFactory, habitList);
        repository = new Repository<>(RepetitionRecord.class, db);
        habit = fixtures.createLongHabit();

        repetitions = habit.getRepetitions();
        today = DateUtils.getToday();
    }

    @Test
    public void testAdd()
    {
        RepetitionRecord record = getByTimestamp(today.plus(1));
        assertNull(record);

        Repetition rep = new Repetition(today.plus(1), CHECKED_EXPLICITLY);
        habit.getRepetitions().add(rep);

        record = getByTimestamp(today.plus(1));
        assertNotNull(record);
        assertThat(record.value, equalTo(CHECKED_EXPLICITLY));
    }

    @Test
    public void testGetByInterval()
    {
        List<Repetition> reps =
            repetitions.getByInterval(today.minus(10), today);

        assertThat(reps.size(), equalTo(8));
        assertThat(reps.get(0).getTimestamp(), equalTo(today.minus(10)));
        assertThat(reps.get(4).getTimestamp(), equalTo(today.minus(5)));
        assertThat(reps.get(5).getTimestamp(), equalTo(today.minus(3)));
    }

    @Test
    public void testGetByTimestamp()
    {
        Repetition rep = repetitions.getByTimestamp(today);
        assertNotNull(rep);
        assertThat(rep.getTimestamp(), equalTo(today));

        rep = repetitions.getByTimestamp(today.minus(2));
        assertNull(rep);
    }

    @Test
    public void testGetOldest()
    {
        Repetition rep = repetitions.getOldest();
        assertNotNull(rep);
        assertThat(rep.getTimestamp(), equalTo(today.minus(120)));
    }

    @Test
    public void testGetOldest_withEmptyHabit()
    {
        Habit empty = fixtures.createEmptyHabit();
        Repetition rep = empty.getRepetitions().getOldest();
        assertNull(rep);
    }

    @Test
    public void testRemove()
    {
        RepetitionRecord record = getByTimestamp(today);
        assertNotNull(record);

        Repetition rep = record.toRepetition();
        repetitions.remove(rep);

        record = getByTimestamp(today);
        assertNull(record);
    }

    @Nullable
    private RepetitionRecord getByTimestamp(Timestamp timestamp)
    {
        String query = "where habit = ? and timestamp = ?";
        String params[] = {
            Long.toString(habit.getId()), Long.toString(timestamp.getUnixTime())
        };

        return repository.findFirst(query, params);
    }
}
