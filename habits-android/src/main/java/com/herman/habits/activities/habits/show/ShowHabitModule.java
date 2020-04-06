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

package com.herman.habits.activities.habits.show;

import com.herman.habits.activities.*;
import com.herman.habits.core.ui.screens.habits.show.*;

import dagger.*;

@Module
public abstract class ShowHabitModule
{
    @Binds
    abstract ShowHabitBehavior.Screen getScreen(ShowHabitScreen screen);

    @Binds
    abstract ShowHabitMenuBehavior.Screen getMenuScreen(ShowHabitScreen screen);

    @Binds
    abstract ShowHabitMenuBehavior.System getSystem(HabitsDirFinder system);
}
