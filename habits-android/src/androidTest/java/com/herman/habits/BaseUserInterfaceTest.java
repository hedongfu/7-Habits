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

package com.herman.habits;

import android.content.*;
import android.support.test.uiautomator.*;

import com.linkedin.android.testbutler.*;

import com.herman.habits.core.models.*;
import com.herman.habits.core.preferences.*;
import com.herman.habits.core.ui.screens.habits.list.*;
import com.herman.habits.core.utils.*;
import org.junit.*;

import static android.support.test.InstrumentationRegistry.*;
import static android.support.test.uiautomator.UiDevice.*;

public class BaseUserInterfaceTest
{
    private static final String PKG = "com.herman.habits";

    public static UiDevice device;

    private HabitsApplicationComponent component;

    private HabitList habitList;

    private Preferences prefs;

    private HabitFixtures fixtures;

    private HabitCardListCache cache;

    public static void startActivity(Class cls)
    {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PKG, cls.getCanonicalName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);
    }

    @Before
    public void setUp() throws Exception
    {
        device = getInstance(getInstrumentation());
        TestButler.setup(getTargetContext());
        TestButler.verifyAnimationsDisabled(getTargetContext());

        HabitsApplication app =
            (HabitsApplication) getTargetContext().getApplicationContext();
        component = app.getComponent();
        habitList = component.getHabitList();
        prefs = component.getPreferences();
        cache = component.getHabitCardListCache();
        fixtures = new HabitFixtures(component.getModelFactory(), habitList);
        resetState();
    }

    @After
    public void tearDown() throws Exception
    {
        device.pressHome();
        device.waitForIdle();
        TestButler.teardown(getTargetContext());
    }

    private void resetState() throws Exception
    {
        prefs.clear();
        prefs.setFirstRun(false);
        prefs.updateLastHint(100, DateUtils.getToday());
        habitList.removeAll();
        cache.refreshAllHabits();
        Thread.sleep(1000);

        Habit h1 = fixtures.createEmptyHabit();
        h1.setName("Wake up early");
        h1.setDescription("Did you wake up early today?");
        h1.setColor(5);
        habitList.update(h1);

        Habit h2 = fixtures.createShortHabit();
        h2.setName("Track time");
        h2.setDescription("Did you track your time?");
        h2.setColor(5);
        habitList.update(h2);

        Habit h3 = fixtures.createLongHabit();
        h3.setName("Meditate");
        h3.setDescription("Did meditate today?");
        h3.setColor(10);
        habitList.update(h3);

        Habit h4 = fixtures.createEmptyHabit();
        h4.setName("Read books");
        h4.setDescription("Did you read books today?");
        h4.setColor(2);
        habitList.update(h4);
    }
}
