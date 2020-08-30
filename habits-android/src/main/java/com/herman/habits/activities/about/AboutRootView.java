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

package com.herman.habits.activities.about;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.herman.androidbase.activities.ActivityContext;
import com.herman.androidbase.activities.BaseRootView;
import com.herman.androidbase.utils.StyledResources;
import com.herman.habits.BuildConfig;
import com.herman.habits.R;
import com.herman.habits.core.ui.screens.about.AboutBehavior;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutRootView extends BaseRootView
{
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    @NonNull
    private final AboutBehavior behavior;

    @Inject
    public AboutRootView(@NonNull @ActivityContext Context context,
                         @NonNull AboutBehavior behavior)
    {
        super(context);
        this.behavior = behavior;

        addView(inflate(getContext(), R.layout.about, null));
        ButterKnife.bind(this);

        String version = getResources().getString(R.string.version_n);
        tvVersion.setText(String.format(version, BuildConfig.VERSION_NAME));

        setDisplayHomeAsUp(true);
    }

    @Override
    public int getToolbarColor()
    {
        StyledResources res = new StyledResources(getContext());
        if (!res.getBoolean(R.attr.useHabitColorAsPrimary))
            return super.getToolbarColor();

        return res.getColor(R.attr.aboutScreenColor);
    }

    @OnClick(R.id.tvFeedback)
    public void onClickFeedback()
    {
        behavior.onSendFeedback();
    }

    @OnClick(R.id.tvVersion)
    public void onClickIcon()
    {
        behavior.onPressDeveloperCountdown();
    }

    @OnClick(R.id.tvRate)
    public void onClickRate()
    {
        behavior.onRateApp();
    }

    @OnClick(R.id.tvSource)
    public void onClickSource()
    {
        behavior.onViewSourceCode();
    }

    @OnClick(R.id.tvTranslate)
    public void onClickTranslate()
    {
        behavior.onTranslateApp();
    }

    @OnClick(R.id.tvPrivacy)
    public void onClickPrivacy()
    {
        behavior.onClickPrivacy();
    }

    @OnClick(R.id.tvContributors)
    public void onClickContributors()
    {
        behavior.onClickCodeContributors();
    }


    @Override
    protected void initToolbar()
    {
        super.initToolbar();
        getToolbar().setTitle(getResources().getString(R.string.about));
    }
}
