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

package com.herman.habits.activities.habits.list.views;

import android.support.test.filters.*;
import android.support.test.runner.*;

import com.herman.habits.*;
import com.herman.habits.core.preferences.*;
import com.herman.habits.core.utils.*;
import org.junit.*;
import org.junit.runner.*;

import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class HeaderViewTest extends BaseViewTest
{
    public static final String PATH = "habits/list/HeaderView/";

    private HeaderView view;

    private Preferences prefs;

    private MidnightTimer midnightTimer;

    @Override
    @Before
    public void setUp()
    {
        super.setUp();
        prefs = mock(Preferences.class);
        midnightTimer = mock(MidnightTimer.class);
        view = new HeaderView(targetContext, prefs, midnightTimer);
        view.setButtonCount(5);
        measureView(view, dpToPixels(600), dpToPixels(48));
    }

    @Test
    public void testRender() throws Exception
    {
        when(prefs.isCheckmarkSequenceReversed()).thenReturn(false);

        assertRenders(view, PATH + "render.png");

        verify(prefs).isCheckmarkSequenceReversed();
        verifyNoMoreInteractions(prefs);
    }

    @Test
    public void testRender_reverse() throws Exception
    {
        when(prefs.isCheckmarkSequenceReversed()).thenReturn(true);

        assertRenders(view, PATH + "render_reverse.png");

        verify(prefs).isCheckmarkSequenceReversed();
        verifyNoMoreInteractions(prefs);
    }
}