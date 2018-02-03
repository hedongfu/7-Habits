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

package com.herman.habits;

import com.herman.androidbase.*;
import com.herman.habits.core.*;
import com.herman.habits.core.tasks.*;

import dagger.*;

@AppScope
@Component(modules = {
    AppContextModule.class,
    HabitsModule.class,
    SingleThreadModule.class,
})
public interface HabitsApplicationTestComponent
    extends HabitsApplicationComponent
{

}

@Module
class SingleThreadModule
{
    @Provides
    @AppScope
    static TaskRunner provideTaskRunner()
    {
        return new SingleThreadTaskRunner();
    }
}