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

package com.herman.habits.tasks;

import android.content.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.factory.*;

import com.herman.androidbase.*;
import com.herman.habits.core.tasks.*;
import com.herman.habits.utils.*;

import java.io.*;

@AutoFactory(allowSubclasses = true)
public class ExportDBTask implements Task
{
    private String filename;

    @NonNull
    private Context context;

    private AndroidDirFinder system;

    @NonNull
    private final Listener listener;

    public ExportDBTask(@Provided @AppContext @NonNull Context context,
                        @Provided @NonNull AndroidDirFinder system,
                        @NonNull Listener listener)
    {
        this.system = system;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void doInBackground()
    {
        filename = null;

        try
        {
            File dir = system.getFilesDir("Backups");
            if (dir == null) return;

            filename = DatabaseUtils.saveDatabaseCopy(context, dir);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPostExecute()
    {
        listener.onExportDBFinished(filename);
    }

    public interface Listener
    {
        void onExportDBFinished(@Nullable String filename);
    }
}
