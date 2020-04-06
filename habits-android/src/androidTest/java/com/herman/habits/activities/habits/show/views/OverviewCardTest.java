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

package com.herman.habits.activities.habits.show.views;

import android.support.test.runner.*;
import android.test.suitebuilder.annotation.*;
import android.view.*;

import com.herman.habits.*;
import com.herman.habits.core.models.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class OverviewCardTest extends BaseViewTest
{
    public static final String PATH = "habits/show/OverviewCard/";

    private OverviewCard view;

    private Habit habit;

    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        habit = fixtures.createLongHabit();

        view = (OverviewCard) LayoutInflater
            .from(targetContext)
            .inflate(R.layout.show_habit, null)
            .findViewById(R.id.overviewCard);

        view.setHabit(habit);
        view.refreshData();
        measureView(view, 800, 300);
    }

    @Test
    public void testRender() throws Exception
    {
        assertRenders(view, PATH + "render.png");
    }
}
