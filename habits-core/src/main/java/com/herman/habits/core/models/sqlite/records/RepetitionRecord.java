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
 *
 *
 */

package com.herman.habits.core.models.sqlite.records;

import com.herman.habits.core.database.*;
import com.herman.habits.core.models.*;

/**
 * The SQLite database record corresponding to a {@link Repetition}.
 */
@Table(name = "Repetitions")
public class RepetitionRecord
{
    public HabitRecord habit;

    @Column(name = "habit")
    public Long habit_id;

    @Column
    public Long timestamp;

    @Column
    public Integer value;

    @Column
    public Long id;

    public void copyFrom(Repetition repetition)
    {
        timestamp = repetition.getTimestamp().getUnixTime();
        value = repetition.getValue();
    }

    public Repetition toRepetition()
    {
        return new Repetition(new Timestamp(timestamp), value);
    }
}
