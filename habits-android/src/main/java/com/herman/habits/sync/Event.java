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

package com.herman.habits.sync;

import android.support.annotation.*;

import com.herman.habits.core.database.*;

@Table(name = "Events")
public class Event
{
    @Nullable
    @Column
    public Long id;

    @NonNull
    @Column(name = "timestamp")
    public Long timestamp;

    @NonNull
    @Column(name = "message")
    public String message;

    @NonNull
    @Column(name = "server_id")
    public String serverId;

    public Event()
    {
        timestamp = 0L;
        message = "";
        serverId = "";
    }

    public Event(@NonNull String serverId, long timestamp, @NonNull String message)
    {
        this.serverId = serverId;
        this.timestamp = timestamp;
        this.message = message;
    }
}
