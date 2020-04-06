/*
 * Copyright (C) 2015-2018 Herman <ringtone.sky@gmail.com>
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
import com.herman.habits.core.utils.*;
import org.junit.*;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class TimestampTest extends BaseUnitTest
{
    @Test
    public void testCompare() throws Exception
    {
        Timestamp t1 = DateUtils.getToday();
        Timestamp t2 = t1.minus(1);
        Timestamp t3 = t1.plus(3);

        assertThat(t1.compare(t2), greaterThan(0));
        assertThat(t1.compare(t1), equalTo(0));
        assertThat(t1.compare(t3), lessThan(0));

        assertTrue(t1.isNewerThan(t2));
        assertFalse(t1.isNewerThan(t1));
        assertFalse(t2.isNewerThan(t1));

        assertTrue(t2.isOlderThan(t1));
        assertFalse(t1.isOlderThan(t2));
    }

    @Test
    public void testDaysUntil() throws Exception
    {
        Timestamp t = DateUtils.getToday();
        assertThat(t.daysUntil(t), equalTo(0));

        assertThat(t.daysUntil(t.plus(1)), equalTo(1));
        assertThat(t.daysUntil(t.plus(3)), equalTo(3));
        assertThat(t.daysUntil(t.plus(300)), equalTo(300));

        assertThat(t.daysUntil(t.minus(1)), equalTo(-1));
        assertThat(t.daysUntil(t.minus(3)), equalTo(-3));
        assertThat(t.daysUntil(t.minus(300)), equalTo(-300));
    }


}
