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

package com.herman.habits.activities.common.views;

import android.support.test.filters.*;
import android.support.test.runner.*;

import com.herman.habits.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.utils.*;
import com.herman.habits.utils.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class BarChartTest extends BaseViewTest
{
    private static final String BASE_PATH = "common/BarChart/";

    private BarChart view;

    @Override
    @Before
    public void setUp()
    {
        super.setUp();
        Habit habit = fixtures.createLongNumericalHabit();
        view = new BarChart(targetContext);
        Timestamp today = DateUtils.getToday();
        CheckmarkList checkmarks = habit.getCheckmarks();
        view.setCheckmarks(checkmarks.getByInterval(today.minus(20), today));
        view.setColor(PaletteUtils.getColor(targetContext, habit.getColor()));
        view.setTarget(200.0);
        measureView(view, dpToPixels(300), dpToPixels(200));
    }

    @Test
    public void testRender() throws Throwable
    {
        assertRenders(view, BASE_PATH + "render.png");
    }

    @Test
    public void testRender_withDataOffset() throws Throwable
    {
        view.onScroll(null, null, -dpToPixels(150), 0);
        view.invalidate();

        assertRenders(view, BASE_PATH + "renderDataOffset.png");
    }

    @Test
    public void testRender_withDifferentSize() throws Throwable
    {
        measureView(view, dpToPixels(200), dpToPixels(200));
        assertRenders(view, BASE_PATH + "renderDifferentSize.png");
    }

    @Test
    public void testRender_withTransparentBackground() throws Throwable
    {
        view.setIsTransparencyEnabled(true);
        assertRenders(view, BASE_PATH + "renderTransparent.png");
    }
}
