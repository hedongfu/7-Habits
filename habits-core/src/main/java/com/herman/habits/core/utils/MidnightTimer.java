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

package com.herman.habits.core.utils;

import com.herman.habits.core.*;

import java.util.*;
import java.util.concurrent.*;

import javax.inject.*;

/**
 * A class that emits events when a new day starts.
 */
@AppScope
public class MidnightTimer
{
    private final List<MidnightListener> listeners;

    private ScheduledExecutorService executor;

    @Inject
    public MidnightTimer()
    {
        this.listeners = new LinkedList<>();
    }

    public synchronized void addListener(MidnightListener listener)
    {
        this.listeners.add(listener);
    }

    public synchronized void onPause()
    {
        executor.shutdownNow();
    }

    public synchronized void onResume()
    {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> notifyListeners(),
            DateUtils.millisecondsUntilTomorrow() + 1000,
            DateUtils.DAY_LENGTH, TimeUnit.MILLISECONDS);
    }

    public synchronized void removeListener(MidnightListener listener)
    {
        this.listeners.remove(listener);
    }

    private synchronized void notifyListeners()
    {
        for (MidnightListener l : listeners) l.atMidnight();
    }

    public interface MidnightListener
    {
        void atMidnight();
    }
}
