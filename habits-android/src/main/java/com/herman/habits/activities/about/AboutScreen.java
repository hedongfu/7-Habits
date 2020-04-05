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

package com.herman.habits.activities.about;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.herman.androidbase.activities.BaseActivity;
import com.herman.androidbase.activities.BaseScreen;
import com.herman.habits.core.ui.screens.about.AboutBehavior;
import com.herman.habits.intents.IntentFactory;

import javax.inject.Inject;

import static com.herman.habits.core.ui.screens.about.AboutBehavior.Message.YOU_ARE_NOW_A_DEVELOPER;

public class AboutScreen extends BaseScreen implements AboutBehavior.Screen
{
    @NonNull
    private final IntentFactory intents;

    @Inject
    public AboutScreen(@NonNull BaseActivity activity,
                       @NonNull IntentFactory intents)
    {
        super(activity);
        this.intents = intents;
    }

    @Override
    public void showMessage(AboutBehavior.Message message)
    {
        if (message == YOU_ARE_NOW_A_DEVELOPER) Toast
            .makeText(activity, "You are now a developer", Toast.LENGTH_LONG)
            .show();
    }

    @Override
    public void showRateAppWebsite()
    {
        activity.startActivity(intents.rateApp(activity));
    }

    @Override
    public void showSendFeedbackScreen()
    {
        activity.startActivity(intents.sendFeedback(activity));
    }

    @Override
    public void showSourceCodeWebsite()
    {
        activity.startActivity(intents.viewSourceCode(activity));
    }

    @Override
    public void showTranslationWebsite()
    {
        activity.startActivity(intents.helpTranslate(activity));
    }

    @Override
    public void showPrivacyPolicyWebsite()
    {
        activity.startActivity(intents.privacyPolicy(activity));
    }

    @Override
    public void showCodeContributorsWebsite() {
        activity.startActivity(intents.codeContributors(activity));
    }
}
