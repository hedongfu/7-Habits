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

package com.herman.habits;

import android.os.*;
import android.support.test.runner.*;
import android.test.suitebuilder.annotation.*;

import com.herman.androidbase.*;
import com.herman.habits.core.models.*;
import org.junit.*;
import org.junit.runner.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class HabitLoggerTest extends BaseAndroidTest
{
    @Test
    public void testLogReminderScheduled() throws IOException
    {
        if (!isLogcatAvailable()) return;

        long time = 1422277200000L; // 13:00 jan 26, 2015 (UTC)
        Habit habit = fixtures.createEmptyHabit();
        habit.setName("Write journal");

        logger.logReminderScheduled(habit, time);

        String expectedMsg = "Setting alarm (2015-01-26 130000): Wri\n";
        assertLogcatContains(expectedMsg);
    }

    protected void assertLogcatContains(String expectedMsg) throws IOException
    {
        String logcat = new AndroidBugReporter(targetContext).getLogcat();
        assertThat(logcat, containsString(expectedMsg));
    }

    protected boolean isLogcatAvailable()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}
