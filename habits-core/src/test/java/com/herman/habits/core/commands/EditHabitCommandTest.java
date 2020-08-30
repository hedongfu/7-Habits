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
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class EditHabitCommandTest extends BaseUnitTest
{
    private EditHabitCommand command;

    private Habit habit;

    private Habit modified;

    @Override
    @Before
    public void setUp() throws Exception
    {
        super.setUp();

        habit = fixtures.createShortHabit();
        habit.setName("original");
        habit.setFrequency(Frequency.DAILY);
        habitList.add(habit);

        modified = fixtures.createEmptyHabit();
        modified.copyFrom(habit);
        modified.setName("modified");
        habitList.add(modified);
    }

    @Test
    public void testExecuteUndoRedo()
    {
        command =
            new EditHabitCommand(modelFactory, habitList, habit, modified);

        double originalScore = habit.getScores().getTodayValue();
        assertThat(habit.getName(), equalTo("original"));

        command.execute();
        assertThat(habit.getName(), equalTo("modified"));
        assertThat(habit.getScores().getTodayValue(), equalTo(originalScore));

        command.undo();
        assertThat(habit.getName(), equalTo("original"));
        assertThat(habit.getScores().getTodayValue(), equalTo(originalScore));

        command.execute();
        assertThat(habit.getName(), equalTo("modified"));
        assertThat(habit.getScores().getTodayValue(), equalTo(originalScore));
    }

    @Test
    public void testExecuteUndoRedo_withModifiedInterval()
    {
        modified.setFrequency(Frequency.TWO_TIMES_PER_WEEK);
        command =
            new EditHabitCommand(modelFactory, habitList, habit, modified);

        double originalScore = habit.getScores().getTodayValue();
        assertThat(habit.getName(), equalTo("original"));

        command.execute();
        assertThat(habit.getName(), equalTo("modified"));
        assertThat(habit.getScores().getTodayValue(),
            lessThan(originalScore));

        command.undo();
        assertThat(habit.getName(), equalTo("original"));
        assertThat(habit.getScores().getTodayValue(), equalTo(originalScore));

        command.execute();
        assertThat(habit.getName(), equalTo("modified"));
        assertThat(habit.getScores().getTodayValue(),
            lessThan(originalScore));
    }

    @Test
    public void testRecord()
    {
        command =
            new EditHabitCommand(modelFactory, habitList, habit, modified);

        EditHabitCommand.Record rec = command.toRecord();
        EditHabitCommand other = rec.toCommand(modelFactory, habitList);

        assertThat(other.getId(), equalTo(command.getId()));
        assertThat(other.savedId, equalTo(command.savedId));
        assertThat(other.original.getData(), equalTo(command.original.getData()));
        assertThat(other.modified.getData(), equalTo(command.modified.getData()));
    }
}
