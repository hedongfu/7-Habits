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

package com.herman.habits.core.tasks;

import com.herman.habits.core.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.mockito.*;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class SingleThreadTaskRunnerTest extends BaseUnitTest
{
    private SingleThreadTaskRunner runner;

    private Task task;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        runner = new SingleThreadTaskRunner();
        task = mock(Task.class);
    }

    @Test
    public void test()
    {
        runner.execute(task);

        InOrder inOrder = inOrder(task);
        inOrder.verify(task).onAttached(runner);
        inOrder.verify(task).onPreExecute();
        inOrder.verify(task).doInBackground();
        inOrder.verify(task).onPostExecute();
    }
}
