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

package com.herman.habits.core.models;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.builder.*;
import com.herman.habits.core.utils.*;

import static com.herman.habits.core.utils.StringUtils.*;

public final class Reminder
{
    private final int hour;

    private final int minute;

    private final WeekdayList days;

    public Reminder(int hour, int minute, @NonNull WeekdayList days)
    {
        this.hour = hour;
        this.minute = minute;
        this.days = days;
    }

    @NonNull
    public WeekdayList getDays()
    {
        return days;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public long getTimeInMillis()
    {
        return DateUtils.getUpcomingTimeInMillis(hour, minute);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Reminder reminder = (Reminder) o;

        return new EqualsBuilder()
                .append(hour, reminder.hour)
                .append(minute, reminder.minute)
                .append(days, reminder.days)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(hour)
                .append(minute)
                .append(days)
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, defaultToStringStyle())
                .append("hour", hour)
                .append("minute", minute)
                .append("days", days)
                .toString();
    }
}
