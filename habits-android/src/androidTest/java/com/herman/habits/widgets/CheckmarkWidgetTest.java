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

import android.support.test.filters.MediumTest;
import android.support.test.runner.*;
import android.test.suitebuilder.annotation.*;
import android.widget.*;

import com.herman.habits.*;
import com.herman.habits.core.models.*;
import org.junit.*;
import org.junit.runner.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static com.herman.habits.core.models.Checkmark.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class CheckmarkWidgetTest extends BaseViewTest
{
    private static final String PATH = "widgets/CheckmarkWidget/";

    private Habit habit;

    private CheckmarkList checkmarks;

    private FrameLayout view;

    @Override
    public void setUp()
    {
        super.setUp();
        setTheme(R.style.TransparentWidgetTheme);

        habit = fixtures.createShortHabit();
        checkmarks = habit.getCheckmarks();
        CheckmarkWidget widget = new CheckmarkWidget(targetContext, 0, habit);
        view = convertToView(widget, 200, 250);

        assertThat(checkmarks.getTodayValue(), equalTo(CHECKED_EXPLICITLY));
    }

    @Test
    public void testClick() throws Exception
    {
        Button button = (Button) view.findViewById(R.id.button);
        assertThat(button, is(not(nullValue())));

        // A better test would be to capture the intent, but it doesn't seem
        // possible to capture intents sent to BroadcastReceivers.
        button.performClick();
        sleep(1000);

        assertThat(checkmarks.getTodayValue(), equalTo(UNCHECKED));
    }

    @Test
    public void testIsInstalled()
    {
        assertWidgetProviderIsInstalled(CheckmarkWidgetProvider.class);
    }

    @Test
    public void testRender() throws Exception
    {
        assertRenders(view, PATH + "render.png");
    }
}
