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

package com.herman.androidbase.utils;

import android.os.*;
import android.util.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.*;

public abstract class FileUtils
{
    public static void copy(File src, File dst) throws IOException
    {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        copy(inStream, outStream);
    }

    public static void copy(InputStream inStream, File dst) throws IOException
    {
        FileOutputStream outStream = new FileOutputStream(dst);
        copy(inStream, outStream);
    }

    public static void copy(InputStream in, OutputStream out) throws IOException
    {
        int numBytes;
        byte[] buffer = new byte[1024];

        while ((numBytes = in.read(buffer)) != -1)
            out.write(buffer, 0, numBytes);
    }

    @Nullable
    public static File getDir(@NonNull File potentialParentDirs[],
                              @Nullable String relativePath)
    {
        if (relativePath == null) relativePath = "";

        File chosenDir = null;
        for (File dir : potentialParentDirs)
        {
            if (dir == null || !dir.canWrite()) continue;
            chosenDir = dir;
            break;
        }

        if (chosenDir == null)
        {
            Log.e("FileUtils",
                "getDir: all potential parents are null or non-writable");
            return null;
        }

        File dir = new File(
            String.format("%s/%s/", chosenDir.getAbsolutePath(), relativePath));
        if (!dir.exists() && !dir.mkdirs())
        {
            Log.e("FileUtils",
                "getDir: chosen dir does not exist and cannot be created");
            return null;
        }

        return dir;
    }

    @Nullable
    public static File getSDCardDir(@Nullable String relativePath)
    {
        File parents[] =
            new File[]{ Environment.getExternalStorageDirectory() };
        return getDir(parents, relativePath);
    }
}
