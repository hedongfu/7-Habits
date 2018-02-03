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

package com.herman.habits.activities.habits.show.views;

import android.content.*;
import android.support.annotation.*;
import android.util.*;
import android.widget.*;

import com.herman.androidbase.utils.*;
import com.herman.habits.*;
import com.herman.habits.R;
import com.herman.habits.activities.common.views.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.tasks.*;
import com.herman.habits.core.utils.*;
import com.herman.habits.utils.*;

import butterknife.*;

public class OverviewCard extends HabitCard
{
    @NonNull
    private Cache cache;

    @BindView(R.id.scoreRing)
    RingView scoreRing;

    @BindView(R.id.scoreLabel)
    TextView scoreLabel;

    @BindView(R.id.monthDiffLabel)
    TextView monthDiffLabel;

    @BindView(R.id.yearDiffLabel)
    TextView yearDiffLabel;

    @BindView(R.id.totalCountLabel)
    TextView totalCountLabel;

    @BindView(R.id.title)
    TextView title;

    private int color;

    @Nullable
    private TaskRunner taskRunner;

    public OverviewCard(Context context)
    {
        super(context);
        init();
    }

    public OverviewCard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    @Override
    protected void refreshData()
    {
        if(taskRunner == null) return;
        taskRunner.execute(new RefreshTask());
    }

    private String formatPercentageDiff(float percentageDiff)
    {
        return String.format("%s%.0f%%", (percentageDiff >= 0 ? "+" : "\u2212"),
            Math.abs(percentageDiff) * 100);
    }

    private void init()
    {
        Context appContext = getContext().getApplicationContext();
        if (appContext instanceof HabitsApplication)
        {
            HabitsApplication app = (HabitsApplication) appContext;
            taskRunner = app.getComponent().getTaskRunner();
        }

        inflate(getContext(), R.layout.show_habit_overview, this);
        ButterKnife.bind(this);
        cache = new Cache();

        if (isInEditMode()) initEditMode();
    }

    private void initEditMode()
    {
        color = PaletteUtils.getAndroidTestColor(1);
        cache.todayScore = 0.6f;
        cache.lastMonthScore = 0.42f;
        cache.lastYearScore = 0.75f;
        refreshColors();
        refreshScore();
    }

    private void refreshColors()
    {
        scoreRing.setColor(color);
        scoreLabel.setTextColor(color);
        title.setTextColor(color);
    }

    private void refreshScore()
    {
        float todayPercentage = cache.todayScore;
        float monthDiff = todayPercentage - cache.lastMonthScore;
        float yearDiff = todayPercentage - cache.lastYearScore;

        scoreRing.setPercentage(todayPercentage);
        scoreLabel.setText(String.format("%.0f%%", todayPercentage * 100));

        monthDiffLabel.setText(formatPercentageDiff(monthDiff));
        yearDiffLabel.setText(formatPercentageDiff(yearDiff));
        totalCountLabel.setText(String.valueOf(cache.totalCount));

        StyledResources res = new StyledResources(getContext());
        int inactiveColor = res.getColor(R.attr.mediumContrastTextColor);

        monthDiffLabel.setTextColor(monthDiff >= 0 ? color : inactiveColor);
        yearDiffLabel.setTextColor(yearDiff >= 0 ? color : inactiveColor);
        totalCountLabel.setTextColor(yearDiff >= 0 ? color : inactiveColor);

        postInvalidate();
    }

    private class Cache
    {
        public float todayScore;

        public float lastMonthScore;

        public float lastYearScore;

        public long totalCount;
    }

    private class RefreshTask implements Task
    {
        @Override
        public void doInBackground()
        {
            Habit habit = getHabit();

            ScoreList scores = habit.getScores();

            Timestamp today = DateUtils.getToday();
            Timestamp lastMonth = today.minus(30);
            Timestamp lastYear = today.minus(365);

            cache.todayScore = (float) scores.getTodayValue();
            cache.lastMonthScore = (float) scores.getValue(lastMonth);
            cache.lastYearScore = (float) scores.getValue(lastYear);
            cache.totalCount = habit.getRepetitions().getTotalCount();
        }

        @Override
        public void onPostExecute()
        {
            refreshScore();
        }

        @Override
        public void onPreExecute()
        {
            color = PaletteUtils.getColor(getContext(), getHabit().getColor());
            refreshColors();
        }
    }
}
