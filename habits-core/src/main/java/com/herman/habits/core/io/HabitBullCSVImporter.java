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

package com.herman.habits.core.io;

import androidx.annotation.*;

import com.opencsv.*;

import com.herman.habits.core.models.*;
import com.herman.habits.core.utils.*;

import java.io.*;
import java.util.*;

import javax.inject.*;


/**
 * Class that imports data from HabitBull CSV files.
 */
public class HabitBullCSVImporter extends AbstractImporter
{
    private ModelFactory modelFactory;

    @Inject
    public HabitBullCSVImporter(@NonNull HabitList habits,
                                @NonNull ModelFactory modelFactory)
    {
        super(habits);
        this.modelFactory = modelFactory;
    }

    @Override
    public boolean canHandle(@NonNull File file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        return line.startsWith("HabitName,HabitDescription,HabitCategory");
    }

    @Override
    public void importHabitsFromFile(@NonNull final File file)
        throws IOException
    {
        CSVReader reader = new CSVReader(new FileReader(file));
        HashMap<String, Habit> map = new HashMap<>();

        for (String line[] : reader)
        {
            String name = line[0];
            if (name.equals("HabitName")) continue;

            String description = line[1];
            String dateString[] = line[3].split("-");
            int year = Integer.parseInt(dateString[0]);
            int month = Integer.parseInt(dateString[1]);
            int day = Integer.parseInt(dateString[2]);

            Calendar date = DateUtils.getStartOfTodayCalendar();
            date.set(year, month - 1, day);

            Timestamp timestamp = new Timestamp(date.getTimeInMillis());

            int value = Integer.parseInt(line[4]);
            if (value != 1) continue;

            Habit h = map.get(name);

            if (h == null)
            {
                h = modelFactory.buildHabit();
                h.setName(name);
                h.setDescription(description == null ? "" : description);
                h.setFrequency(Frequency.DAILY);
                habitList.add(h);
                map.put(name, h);
            }

            if (!h.getRepetitions().containsTimestamp(timestamp))
                h.getRepetitions().toggle(timestamp);
        }
    }
}
