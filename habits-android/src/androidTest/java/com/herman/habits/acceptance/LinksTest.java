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

package com.herman.habits.acceptance;

import android.support.test.filters.*;
import android.support.test.runner.*;

import com.herman.habits.*;
import org.junit.*;
import org.junit.runner.*;

import static com.herman.habits.acceptance.steps.CommonSteps.*;
import static com.herman.habits.acceptance.steps.ListHabitsSteps.MenuItem.*;
import static com.herman.habits.acceptance.steps.ListHabitsSteps.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LinksTest extends BaseUserInterfaceTest
{
    @Test
    public void shouldLinkToSourceCode() throws Exception
    {
        launchApp();
        clickMenu(ABOUT);
        clickText("View source code at GitHub");
        verifyOpensWebsite("https://github.com/iSoron/habits");
    }

    @Test
    public void shouldLinkToTranslationWebsite() throws Exception
    {
        launchApp();
        clickMenu(ABOUT);
        clickText("Help translate this app");
        verifyOpensWebsite("translate.loophabits.org");
    }

    @Test
    public void shouldLinkToHelp() throws Exception
    {
        launchApp();
        clickMenu(HELP);
        verifyOpensWebsite("loophabits.org/faq.html");

        launchApp();
        clickMenu(SETTINGS);
        clickText("Help & FAQ");
        verifyOpensWebsite("loophabits.org/faq.html");
    }
}
