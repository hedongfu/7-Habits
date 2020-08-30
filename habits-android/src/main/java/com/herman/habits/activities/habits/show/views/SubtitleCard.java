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

package com.herman.habits.activities.habits.show.views;

import android.annotation.*;
import android.content.*;
import android.content.res.*;
import android.util.*;
import android.widget.*;

import com.herman.habits.R;
import com.herman.habits.core.models.*;
import com.herman.habits.core.tasks.Task;
import com.herman.habits.utils.*;

import butterknife.*;

public class SubtitleCard extends HabitCard
{
    @BindView(R.id.questionLabel)
    TextView questionLabel;

    @BindView(R.id.frequencyLabel)
    TextView frequencyLabel;

    @BindView(R.id.reminderLabel)
    TextView reminderLabel;

    public SubtitleCard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    @Override
    protected void refreshData()
    {
        Habit habit = getHabit();
        int color = PaletteUtils.getColor(getContext(), habit.getColor());

        reminderLabel.setText(getResources().getString(R.string.reminder_off));
        questionLabel.setVisibility(VISIBLE);

        questionLabel.setTextColor(color);
        questionLabel.setText(habit.getQuestion());
        frequencyLabel.setText(toText(habit.getFrequency()));

        if (habit.hasReminder()) updateReminderText(habit.getReminder());

        if (habit.getQuestion().isEmpty()) questionLabel.setVisibility(GONE);

        invalidate();
    }

    private void init()
    {
        inflate(getContext(), R.layout.show_habit_subtitle, this);
        ButterKnife.bind(this);

        if (isInEditMode()) initEditMode();
    }

    @SuppressLint("SetTextI18n")
    private void initEditMode()
    {
        questionLabel.setTextColor(PaletteUtils.getAndroidTestColor(1));
        questionLabel.setText("Have you meditated today?");
        reminderLabel.setText("08:00");
    }

    private String toText(Frequency freq)
    {
        Resources resources = getResources();
        Integer num = freq.getNumerator();
        Integer den = freq.getDenominator();

        if (num.equals(den)) return resources.getString(R.string.every_day);

        if (num == 1)
        {
            if (den == 7) return resources.getString(R.string.every_week);
            if (den % 7 == 0)
                return resources.getString(R.string.every_x_weeks, den / 7);
            return resources.getString(R.string.every_x_days, den);
        }

        String times_every = resources.getString(R.string.times_every);
        return String.format("%d %s %d %s", num, times_every, den,
            resources.getString(R.string.days));
    }

    private void updateReminderText(Reminder reminder)
    {
        reminderLabel.setText(
            AndroidDateUtils.formatTime(getContext(), reminder.getHour(),
                reminder.getMinute()));
    }

    @Override
    protected Task createRefreshTask() {
        // Never called
        throw new IllegalStateException();
    }
}
