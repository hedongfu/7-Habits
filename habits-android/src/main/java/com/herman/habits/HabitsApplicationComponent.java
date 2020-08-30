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

import com.herman.androidbase.*;
import com.herman.habits.core.*;
import com.herman.habits.core.commands.*;
import com.herman.habits.core.io.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.preferences.*;
import com.herman.habits.core.reminders.*;
import com.herman.habits.core.tasks.*;
import com.herman.habits.core.ui.*;
import com.herman.habits.core.ui.screens.habits.list.*;
import com.herman.habits.core.utils.*;
import com.herman.habits.intents.*;
import com.herman.habits.receivers.*;
import com.herman.habits.sync.*;
import com.herman.habits.tasks.*;
import com.herman.habits.widgets.*;

import dagger.*;

@AppScope
@Component(modules = {
    AppContextModule.class,
    HabitsModule.class,
    AndroidTaskRunner.class,
})
public interface HabitsApplicationComponent
{
    CommandRunner getCommandRunner();

    @AppContext
    Context getContext();

    CreateHabitCommandFactory getCreateHabitCommandFactory();

    EditHabitCommandFactory getEditHabitCommandFactory();

    GenericImporter getGenericImporter();

    HabitCardListCache getHabitCardListCache();

    HabitList getHabitList();

    IntentFactory getIntentFactory();

    IntentParser getIntentParser();

    MidnightTimer getMidnightTimer();

    ModelFactory getModelFactory();

    NotificationTray getNotificationTray();

    PendingIntentFactory getPendingIntentFactory();

    Preferences getPreferences();

    ReminderScheduler getReminderScheduler();

    ReminderController getReminderController();

    SyncManager getSyncManager();

    TaskRunner getTaskRunner();

    WidgetPreferences getWidgetPreferences();

    WidgetUpdater getWidgetUpdater();
}
