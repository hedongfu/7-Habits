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

package com.herman.habits.activities.common.views;

import android.graphics.*;
import android.support.test.runner.*;
import android.test.suitebuilder.annotation.*;

import com.herman.habits.*;
import com.herman.habits.utils.*;
import org.junit.*;
import org.junit.runner.*;

import java.io.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class RingViewTest extends BaseViewTest
{
    private static final String BASE_PATH = "common/RingView/";

    private RingView view;

    @Override
    @Before
    public void setUp()
    {
        super.setUp();

        view = new RingView(targetContext);
        view.setPercentage(0.6f);
        view.setText("60%");
        view.setColor(PaletteUtils.getAndroidTestColor(0));
        view.setBackgroundColor(Color.WHITE);
        view.setThickness(dpToPixels(3));
    }

    @Test
    public void testRender_base() throws IOException
    {
        measureView(view, dpToPixels(100), dpToPixels(100));
        assertRenders(view, BASE_PATH + "render.png");
    }

    @Test
    public void testRender_withDifferentParams() throws IOException
    {
        view.setPercentage(0.25f);
        view.setColor(PaletteUtils.getAndroidTestColor(5));

        measureView(view, dpToPixels(200), dpToPixels(200));
        assertRenders(view, BASE_PATH + "renderDifferentParams.png");
    }
}
