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

package com.herman.habits.tasks;

import android.support.test.runner.*;
import android.test.suitebuilder.annotation.*;

import com.herman.androidbase.*;
import com.herman.habits.*;
import org.junit.*;
import org.junit.runner.*;

import java.io.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class ExportDBTaskTest extends BaseAndroidTest
{
    @Override
    @Before
    public void setUp()
    {
        super.setUp();
    }

//    @Test
//    public void testExportCSV() throws Throwable
//    {
//        ExportDBTask task =
//            new ExportDBTask(targetContext, new AndroidDirFinder(targetContext),
//                filename ->
//                {
//                    assertNotNull(filename);
//                    File f = new File(filename);
//                    assertTrue(f.exists());
//                    assertTrue(f.canRead());
//                });
//
//        taskRunner.execute(task);
//    }
}
