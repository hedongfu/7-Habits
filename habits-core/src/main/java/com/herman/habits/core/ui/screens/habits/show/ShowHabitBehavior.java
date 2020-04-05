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

package com.herman.habits.core.ui.screens.habits.show;

import androidx.annotation.NonNull;

import com.herman.habits.core.commands.*;
import com.herman.habits.core.models.*;

import javax.inject.*;

public class ShowHabitBehavior
{
    private HabitList habitList;

    @NonNull
    private final Habit habit;

    @NonNull
    private final CommandRunner commandRunner;

    @NonNull
    private Screen screen;

    @Inject
    public ShowHabitBehavior(@NonNull HabitList habitList,
                             @NonNull CommandRunner commandRunner,
                             @NonNull Habit habit,
                             @NonNull Screen screen)
    {
        this.habitList = habitList;
        this.habit = habit;
        this.commandRunner = commandRunner;
        this.screen = screen;
    }

    public void onEditHistory()
    {
        screen.showEditHistoryScreen();
    }

    public void onToggleCheckmark(Timestamp timestamp)
    {
        commandRunner.execute(
            new ToggleRepetitionCommand(habitList, habit, timestamp), null);
    }

    public interface Screen
    {
        void showEditHistoryScreen();
    }
}
