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
import android.util.*;
import android.widget.*;

import androidx.annotation.Nullable;
import com.herman.habits.*;
import com.herman.habits.R;
import com.herman.habits.activities.common.views.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.preferences.Preferences;
import com.herman.habits.core.tasks.*;
import com.herman.habits.utils.*;

import butterknife.*;

public class HistoryCard extends HabitCard
{
    @BindView(R.id.historyChart)
    HistoryChart chart;

    @BindView(R.id.title)
    TextView title;

    @Nullable
    private Controller controller;

    @Nullable
    private Preferences prefs;

    public HistoryCard(Context context)
    {
        super(context);
        init();
    }

    public HistoryCard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    @OnClick(R.id.edit)
    public void onClickEditButton()
    {
        if(controller != null) controller.onEditHistoryButtonClick();
    }

    public void setController(@Nullable Controller controller)
    {
        this.controller = controller;
    }

    private void init()
    {
        Context appContext = getContext().getApplicationContext();
        if (appContext instanceof HabitsApplication)
        {
            HabitsApplication app = (HabitsApplication) appContext;
            prefs = app.getComponent().getPreferences();
        }

        inflate(getContext(), R.layout.show_habit_history, this);
        ButterKnife.bind(this);
        controller = new Controller() {};
        if (isInEditMode()) initEditMode();
    }

    private void initEditMode()
    {
        int color = PaletteUtils.getAndroidTestColor(1);
        title.setTextColor(color);
        chart.setColor(color);
        chart.populateWithRandomData();
    }

    @Override
    protected Task createRefreshTask()
    {
        return new RefreshTask(getHabit());
    }

    public interface Controller
    {
        default void onEditHistoryButtonClick() {}
    }

    private class RefreshTask  extends CancelableTask
    {
        private final Habit habit;

        private RefreshTask(Habit habit)
        {
            this.habit = habit;
        }

        @Override
        public void doInBackground()
        {
            if (isCanceled()) return;
            int[] checkmarks = habit.getCheckmarks().getAllValues();
            if(prefs != null) chart.setFirstWeekday(prefs.getFirstWeekday());
            chart.setCheckmarks(checkmarks);
        }

        @Override
        public void onPreExecute()
        {
            int color = PaletteUtils.getColor(getContext(), habit.getColor());
            title.setTextColor(color);
            chart.setColor(color);
            if(habit.isNumerical())
            {
                chart.setTarget((int) (habit.getTargetValue() * 1000));
                chart.setNumerical(true);
            }
        }
    }
}
