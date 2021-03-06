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

package com.herman.habits.widgets;

import android.support.test.runner.*;
import android.test.suitebuilder.annotation.*;
import android.widget.*;

import com.herman.habits.*;
import com.herman.habits.core.models.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class HistoryWidgetTest extends BaseViewTest
{
    private static final String PATH = "widgets/HistoryWidget/";

    private Habit habit;

    private FrameLayout view;

    @Override
    public void setUp()
    {
        super.setUp();
        setTheme(R.style.TransparentWidgetTheme);

        habit = fixtures.createLongHabit();
        HistoryWidget widget = new HistoryWidget(targetContext, 0, habit);
        view = convertToView(widget, 400, 400);
    }

    @Test
    public void testIsInstalled()
    {
        assertWidgetProviderIsInstalled(HistoryWidgetProvider.class);
    }

//    @Test
//    public void testRender() throws Exception
//    {
//        assertRenders(view, PATH + "render.png");
//    }
}
