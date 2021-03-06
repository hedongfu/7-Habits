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

package com.herman.habits.activities.habits.list.views;

import android.support.test.runner.*;
import android.test.suitebuilder.annotation.*;

import com.herman.habits.*;
import com.herman.habits.core.ui.screens.habits.list.*;
import org.junit.*;
import org.junit.runner.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class HintViewTest extends BaseViewTest
{
    public static final String PATH = "habits/list/HintView/";

    private HintView view;

    private HintList list;

    @Before
    @Override
    public void setUp()
    {
        super.setUp();

        list = mock(HintList.class);
        view = new HintView(targetContext, list);
        measureView(view, 400, 200);

        String text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        when(list.shouldShow()).thenReturn(true);
        when(list.pop()).thenReturn(text);

        view.showNext();
        skipAnimation(view);
    }

    @Test
    public void testRender() throws Exception
    {
        assertRenders(view, PATH + "render.png");
    }

    @Test
    public void testClick() throws Exception
    {
        assertThat(view.getAlpha(), equalTo(1f));
        view.performClick();
        skipAnimation(view);
        assertThat(view.getAlpha(), equalTo(0f));
    }
}
