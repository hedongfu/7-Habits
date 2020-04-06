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

package com.herman.habits.core.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.factory.*;

import com.herman.habits.core.io.*;
import com.herman.habits.core.models.*;

import java.io.*;
import java.util.*;

@AutoFactory(allowSubclasses = true)
public class ExportCSVTask implements Task
{
    private String archiveFilename;

    @NonNull
    private final List<Habit> selectedHabits;

    private File outputDir;

    @NonNull
    private final ExportCSVTask.Listener listener;

    @NonNull
    private final HabitList habitList;

    public ExportCSVTask(@Provided @NonNull HabitList habitList,
                         @NonNull List<Habit> selectedHabits,
                         @NonNull File outputDir,
                         @NonNull Listener listener)
    {
        this.listener = listener;
        this.habitList = habitList;
        this.selectedHabits = selectedHabits;
        this.outputDir = outputDir;
    }

    @Override
    public void doInBackground()
    {
        try
        {
            HabitsCSVExporter exporter;
            exporter = new HabitsCSVExporter(habitList, selectedHabits, outputDir);
            archiveFilename = exporter.writeArchive();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostExecute()
    {
        listener.onExportCSVFinished(archiveFilename);
    }

    public interface Listener
    {
        void onExportCSVFinished(@Nullable String archiveFilename);
    }
}
