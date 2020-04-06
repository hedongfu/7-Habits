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

package com.herman.habits.core.ui.screens.habits.list;

import com.herman.habits.core.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.preferences.*;
import com.herman.habits.core.utils.*;
import org.junit.*;
import org.mockito.*;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class HintListTest extends BaseUnitTest
{
    private HintList hintList;

    private String[] hints;

    @Mock
    private Preferences prefs;

    private Timestamp today;

    private Timestamp yesterday;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        today = DateUtils.getToday();
        yesterday = today.minus(1);

        hints = new String[]{ "hint1", "hint2", "hint3" };
        hintList = new HintList(prefs, hints);
    }

    @Test
    public void pop() throws Exception
    {
        when(prefs.getLastHintNumber()).thenReturn(-1);
        assertThat(hintList.pop(), equalTo("hint1"));
        verify(prefs).updateLastHint(0, today);

        when(prefs.getLastHintNumber()).thenReturn(2);
        assertNull(hintList.pop());
    }

    @Test
    public void shouldShow() throws Exception
    {
        when(prefs.getLastHintTimestamp()).thenReturn(today);
        assertFalse(hintList.shouldShow());

        when(prefs.getLastHintTimestamp()).thenReturn(yesterday);
        assertTrue(hintList.shouldShow());
    }
}