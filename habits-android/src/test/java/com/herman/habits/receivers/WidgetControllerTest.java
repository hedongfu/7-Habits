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

package com.herman.habits.receivers;

import com.herman.habits.*;
import com.herman.habits.core.commands.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.ui.*;
import com.herman.habits.core.ui.widgets.*;
import com.herman.habits.core.utils.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsEqual.*;
import static com.herman.habits.core.models.Checkmark.*;
import static org.mockito.Mockito.*;

public class WidgetControllerTest extends BaseAndroidJVMTest
{
    private WidgetBehavior controller;

    private CommandRunner commandRunner;

    private Habit habit;

    private Timestamp today;

    private NotificationTray notificationTray;

    @Override
    public void setUp()
    {
        super.setUp();

        today = DateUtils.getToday();
        habit = fixtures.createEmptyHabit();
        commandRunner = mock(CommandRunner.class);
        notificationTray = mock(NotificationTray.class);
        controller =
            new WidgetBehavior(habitList, commandRunner, notificationTray);
    }

    @Test
    public void testOnAddRepetition_whenChecked() throws Exception
    {
        habit.getRepetitions().toggle(today);
        int todayValue = habit.getCheckmarks().getTodayValue();
        assertThat(todayValue, equalTo(CHECKED_EXPLICITLY));
        controller.onAddRepetition(habit, today);
        verifyZeroInteractions(commandRunner);
    }

    @Test
    public void testOnAddRepetition_whenUnchecked() throws Exception
    {
        int todayValue = habit.getCheckmarks().getTodayValue();
        assertThat(todayValue, equalTo(UNCHECKED));
        controller.onAddRepetition(habit, today);
        verify(commandRunner).execute(any(), isNull());
        verify(notificationTray).cancel(habit);
    }

    @Test
    public void testOnRemoveRepetition_whenChecked() throws Exception
    {
        habit.getRepetitions().toggle(today);
        int todayValue = habit.getCheckmarks().getTodayValue();
        assertThat(todayValue, equalTo(CHECKED_EXPLICITLY));
        controller.onRemoveRepetition(habit, today);
        verify(commandRunner).execute(any(), isNull());
    }

    @Test
    public void testOnRemoveRepetition_whenUnchecked() throws Exception
    {
        int todayValue = habit.getCheckmarks().getTodayValue();
        assertThat(todayValue, equalTo(UNCHECKED));
        controller.onRemoveRepetition(habit, today);
        verifyZeroInteractions(commandRunner);
    }

    @Test
    public void testOnToggleRepetition() throws Exception
    {
        controller.onToggleRepetition(habit, today);
        verify(commandRunner).execute(any(), isNull());
    }
}