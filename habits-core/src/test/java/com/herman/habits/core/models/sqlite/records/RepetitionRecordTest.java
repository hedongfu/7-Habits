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
 *
 *
 */

package com.herman.habits.core.models.sqlite.records;

import com.herman.habits.core.*;
import com.herman.habits.core.models.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RepetitionRecordTest extends BaseUnitTest
{
    @Test
    public void testRecord() throws Exception
    {
        Repetition rep = new Repetition(Timestamp.ZERO.plus(100), 50);
        RepetitionRecord record = new RepetitionRecord();
        record.copyFrom(rep);
        assertThat(rep, equalTo(record.toRepetition()));
    }
}
