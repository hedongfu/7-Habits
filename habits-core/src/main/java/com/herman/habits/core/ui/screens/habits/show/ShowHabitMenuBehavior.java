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

import android.support.annotation.*;

import com.herman.habits.core.commands.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.tasks.*;
import com.herman.habits.core.ui.callbacks.*;

import java.io.*;
import java.util.*;

import javax.inject.*;

public class ShowHabitMenuBehavior
{
    private HabitList habitList;

    @NonNull
    private final Habit habit;

    @NonNull
    private final TaskRunner taskRunner;

    @NonNull
    private Screen screen;

    @NonNull
    private System system;

    @NonNull
    private CommandRunner commandRunner;

    @Inject
    public ShowHabitMenuBehavior(@NonNull HabitList habitList,
                                 @NonNull Habit habit,
                                 @NonNull TaskRunner taskRunner,
                                 @NonNull Screen screen,
                                 @NonNull System system,
                                 @NonNull CommandRunner commandRunner)
    {
        this.habitList = habitList;
        this.habit = habit;
        this.taskRunner = taskRunner;
        this.screen = screen;
        this.system = system;
        this.commandRunner = commandRunner;
    }

    public void onEditHabit()
    {
        screen.showEditHabitScreen(habit);
    }

    public void onExportCSV()
    {
        List<Habit> selected = Collections.singletonList(habit);
        File outputDir = system.getCSVOutputDir();

        taskRunner.execute(
            new ExportCSVTask(habitList, selected, outputDir, filename ->
            {
                if (filename != null) screen.showSendFileScreen(filename);
                else screen.showMessage(Message.COULD_NOT_EXPORT);
            }));
    }

    public void onDeleteHabit()
    {
        List<Habit> selected = Collections.singletonList(habit);

        screen.showDeleteConfirmationScreen(() -> {
            commandRunner.execute(new DeleteHabitsCommand(habitList, selected),
                    null);
            screen.close();
        });
    }

    public enum Message
    {
        COULD_NOT_EXPORT, HABIT_DELETED
    }

    public interface Screen
    {
        void showEditHabitScreen(@NonNull Habit habit);

        void showMessage(Message m);

        void showSendFileScreen(String filename);

        void showDeleteConfirmationScreen(
                @NonNull OnConfirmedCallback callback);

        void close();
    }

    public interface System
    {
        File getCSVOutputDir();
    }
}
