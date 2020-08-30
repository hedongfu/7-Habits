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

package com.herman.habits.core.commands;

import com.herman.habits.core.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.utils.*;
import org.junit.*;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ToggleRepetitionCommandTest extends BaseUnitTest
{

    private ToggleRepetitionCommand command;
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
        command = new ToggleRepetitionCommand(habitList, habit, today);
    }

    @Test
    public void testExecuteUndoRedo()
    {
        assertTrue(habit.getRepetitions().containsTimestamp(today));

        command.execute();
        assertFalse(habit.getRepetitions().containsTimestamp(today));

        command.undo();
        assertTrue(habit.getRepetitions().containsTimestamp(today));

        command.execute();
        assertFalse(habit.getRepetitions().containsTimestamp(today));
    }

    @Test
    public void testRecord()
    {
        ToggleRepetitionCommand.Record rec = command.toRecord();
        ToggleRepetitionCommand other = rec.toCommand(habitList);

        assertThat(command.getId(), equalTo(other.getId()));
        assertThat(command.timestamp, equalTo(other.timestamp));
        assertThat(command.habit, equalTo(other.habit));
    }
}
