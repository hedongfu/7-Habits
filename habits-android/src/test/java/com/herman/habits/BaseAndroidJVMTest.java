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

import com.herman.habits.core.commands.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.models.memory.*;
import com.herman.habits.core.tasks.*;
import com.herman.habits.core.test.*;
import com.herman.habits.core.utils.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.junit.*;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaseAndroidJVMTest
{
    protected HabitList habitList;
    protected HabitFixtures fixtures;
    protected MemoryModelFactory modelFactory;
    protected SingleThreadTaskRunner taskRunner;
    protected CommandRunner commandRunner;

    @Before
    public void setUp()
    {
        long fixed_local_time = 1422172800000L;
        DateUtils.setFixedLocalTime(fixed_local_time);

        modelFactory = new MemoryModelFactory();
        habitList = spy(modelFactory.buildHabitList());
        fixtures = new HabitFixtures(modelFactory, habitList);
        taskRunner = new SingleThreadTaskRunner();
        commandRunner = new CommandRunner(taskRunner);
    }

    @After
    public void tearDown()
    {
        DateUtils.setFixedLocalTime(null);
    }

    public long timestamp(int year, int month, int day)
    {
        GregorianCalendar cal = DateUtils.getStartOfTodayCalendar();
        cal.set(year, month, day);
        return cal.getTimeInMillis();
    }

    @Test
    public void nothing()
    {

    }
}
