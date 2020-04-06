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

package com.herman.habits.core.ui;

import androidx.annotation.*;

import com.herman.habits.core.preferences.*;

public abstract class ThemeSwitcher
{
    public static final int THEME_DARK = 1;

    public static final int THEME_LIGHT = 2;

    public static final int THEME_AUTOMATIC = 0;

    private final Preferences preferences;

    public ThemeSwitcher(@NonNull Preferences preferences)
    {
        this.preferences = preferences;
    }

    public void apply()
    {
        if (isNightMode())
        {
            if (preferences.isPureBlackEnabled()) applyPureBlackTheme();
            else applyDarkTheme();
        }
        else
        {
            applyLightTheme();
        }
    }

    public abstract void applyDarkTheme();

    public abstract void applyLightTheme();

    public abstract void applyPureBlackTheme();

    public abstract int getSystemTheme();

    public boolean isNightMode()
    {
        int systemTheme = getSystemTheme();
        int userTheme = preferences.getTheme();

        return (userTheme == THEME_DARK ||
                (systemTheme == THEME_DARK && userTheme == THEME_AUTOMATIC));
    }

    public void toggleNightMode()
    {
        int systemTheme = getSystemTheme();
        int userTheme = preferences.getTheme();

        if(userTheme == THEME_AUTOMATIC)
        {
            if(systemTheme == THEME_LIGHT) preferences.setTheme(THEME_DARK);
            if(systemTheme == THEME_DARK) preferences.setTheme(THEME_LIGHT);
        }
        else if(userTheme == THEME_LIGHT)
        {
            if (systemTheme == THEME_LIGHT) preferences.setTheme(THEME_DARK);
            if (systemTheme == THEME_DARK) preferences.setTheme(THEME_AUTOMATIC);
        }
        else if(userTheme == THEME_DARK)
        {
            if (systemTheme == THEME_LIGHT) preferences.setTheme(THEME_AUTOMATIC);
            if (systemTheme == THEME_DARK) preferences.setTheme(THEME_LIGHT);
        }
    }
}
