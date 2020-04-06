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

package com.herman.habits.core.models;

import com.herman.habits.core.*;
import org.junit.*;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsEqual.*;

public class WeekdayListTest extends BaseUnitTest
{
    @Test
    public void test()
    {
        int daysInt = 124;
        boolean[] daysArray = new boolean[]{
            false, false, true, true, true, true, true
        };

        WeekdayList list = new WeekdayList(daysArray);
        assertThat(list.toArray(), equalTo(daysArray));
        assertThat(list.toInteger(), equalTo(daysInt));

        list = new WeekdayList(daysInt);
        assertThat(list.toArray(), equalTo(daysArray));
        assertThat(list.toInteger(), equalTo(daysInt));
    }

    @Test
    public void testEmpty()
    {
        WeekdayList list = new WeekdayList(0);
        assertTrue(list.isEmpty());

        assertFalse(WeekdayList.EVERY_DAY.isEmpty());
    }
}
