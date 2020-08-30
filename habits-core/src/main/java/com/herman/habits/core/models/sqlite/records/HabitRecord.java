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
 * The SQLite database record corresponding to a {@link Habit}.
 */
@Table(name = "habits")
public class HabitRecord
{
    @Column
    public String description;

    @Column
    public String question;

    @Column
    public String name;

    @Column(name = "freq_num")
    public Integer freqNum;

    @Column(name = "freq_den")
    public Integer freqDen;

    @Column
    public Integer color;

    @Column
    public Integer position;

    @Column(name = "reminder_hour")
    public Integer reminderHour;

    @Column(name = "reminder_min")
    public Integer reminderMin;

    @Column(name = "reminder_days")
    public Integer reminderDays;

    @Column
    public Integer highlight;

    @Column
    public Integer archived;

    @Column
    public Integer type;

    @Column(name = "target_value")
    public Double targetValue;

    @Column(name = "target_type")
    public Integer targetType;

    @Column
    public String unit;

    @Column
    public Long id;

    public void copyFrom(Habit model)
    {
        this.id = model.getId();
        this.name = model.getName();
        this.description = model.getDescription();
        this.highlight = 0;
        this.color = model.getColor();
        this.archived = model.isArchived() ? 1 : 0;
        this.type = model.getType();
        this.targetType = model.getTargetType();
        this.targetValue = model.getTargetValue();
        this.unit = model.getUnit();
        this.position = model.getPosition();
        this.question = model.getQuestion();

        Frequency freq = model.getFrequency();
        this.freqNum = freq.getNumerator();
        this.freqDen = freq.getDenominator();
        this.reminderDays = 0;
        this.reminderMin = null;
        this.reminderHour = null;

        if (model.hasReminder())
        {
            Reminder reminder = model.getReminder();
            this.reminderHour = reminder.getHour();
            this.reminderMin = reminder.getMinute();
            this.reminderDays = reminder.getDays().toInteger();
        }
    }

    public void copyTo(Habit habit)
    {
        habit.setId(this.id);
        habit.setName(this.name);
        habit.setDescription(this.description);
        habit.setQuestion(this.question);
        habit.setFrequency(new Frequency(this.freqNum, this.freqDen));
        habit.setColor(this.color);
        habit.setArchived(this.archived != 0);
        habit.setType(this.type);
        habit.setTargetType(this.targetType);
        habit.setTargetValue(this.targetValue);
        habit.setUnit(this.unit);
        habit.setPosition(this.position);

        if (reminderHour != null && reminderMin != null)
        {
            habit.setReminder(new Reminder(reminderHour, reminderMin,
                new WeekdayList(reminderDays)));
        }
    }
}
