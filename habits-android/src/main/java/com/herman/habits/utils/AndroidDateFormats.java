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
package com.herman.habits.utils;

import androidx.annotation.NonNull;

import com.herman.habits.core.utils.*;

import java.text.*;
import java.util.*;

import static android.text.format.DateFormat.getBestDateTimePattern;

public class AndroidDateFormats
{
    @NonNull
    public static SimpleDateFormat fromSkeleton(@NonNull String skeleton)
    {
        Locale locale = Locale.getDefault();
        skeleton = getBestDateTimePattern(locale, skeleton);
        return DateFormats.fromSkeleton(skeleton, locale);
    }
}
