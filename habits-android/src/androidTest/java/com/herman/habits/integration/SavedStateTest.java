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

package com.herman.habits.integration;

import android.support.test.filters.*;
import android.support.test.runner.*;

import com.herman.habits.*;
import com.herman.habits.activities.about.*;
import com.herman.habits.activities.habits.list.*;
import org.junit.*;
import org.junit.runner.*;

import static java.lang.Thread.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SavedStateTest extends BaseUserInterfaceTest
{
    /**
     * Make sure that the main activity can be recreated by using
     * BundleSavedState after being destroyed. See bug:
     * https://github.com/iSoron/habits/issues/287
     */
    @Test
    public void testBundleSavedState() throws Exception
    {
        startActivity(ListHabitsActivity.class);
        device.waitForIdle();
        startActivity(AboutActivity.class);
        sleep(1000);
        device.pressBack();
    }
}
