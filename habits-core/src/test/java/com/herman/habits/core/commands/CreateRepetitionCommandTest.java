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

import com.herman.habits.core.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.utils.*;
import org.junit.*;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static com.herman.habits.core.models.Checkmark.CHECKED_EXPLICITLY;

public class CreateRepetitionCommandTest extends BaseUnitTest
{
    private CreateRepetitionCommand command;

    private Habit habit;

    private Timestamp today;

    @Override
    @Before
    public void setUp() throws Exception
    {
        super.setUp();

        habit = fixtures.createShortHabit();
        habitList.add(habit);

        today = DateUtils.getToday();
        command = new CreateRepetitionCommand(habit, today, 100);
    }

    @Test
    public void testExecuteUndoRedo()
    {
        RepetitionList reps = habit.getRepetitions();

        Repetition rep = reps.getByTimestamp(today);
        assertNotNull(rep);
        assertEquals(CHECKED_EXPLICITLY, rep.getValue());

        command.execute();
        rep = reps.getByTimestamp(today);
        assertNotNull(rep);
        assertEquals(100, rep.getValue());

        command.undo();
        rep = reps.getByTimestamp(today);
        assertNotNull(rep);
        assertEquals(CHECKED_EXPLICITLY, rep.getValue());
    }

    @Test
    public void testRecord()
    {
        CreateRepetitionCommand.Record rec = command.toRecord();
        CreateRepetitionCommand other = rec.toCommand(habitList);

        assertThat(command.getId(), equalTo(other.getId()));
        assertThat(command.timestamp, equalTo(other.timestamp));
        assertThat(command.value, equalTo(other.value));
        assertThat(command.habit, equalTo(other.habit));
    }
}
