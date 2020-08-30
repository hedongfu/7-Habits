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

package com.herman.habits.core.ui.screens.about;

import androidx.annotation.NonNull;

import com.herman.habits.core.preferences.*;

import javax.inject.*;

public class AboutBehavior
{
    private int developerCountdown = 5;

    @NonNull
    private Preferences prefs;

    @NonNull
    private Screen screen;

    @Inject
    public AboutBehavior(@NonNull Preferences prefs, @NonNull Screen screen)
    {
        this.prefs = prefs;
        this.screen = screen;
    }

    public void onPressDeveloperCountdown()
    {
        developerCountdown--;
        if (developerCountdown == 0)
        {
            prefs.setDeveloper(true);
            screen.showMessage(Message.YOU_ARE_NOW_A_DEVELOPER);
        }
    }

    public void onRateApp()
    {
        screen.showRateAppWebsite();
    }

    public void onSendFeedback()
    {
        screen.showSendFeedbackScreen();
    }

    public void onTranslateApp()
    {
        screen.showTranslationWebsite();
    }

    public void onViewSourceCode()
    {
        screen.showSourceCodeWebsite();
    }

    public void onClickPrivacy() {
        screen.showPrivacyPolicyWebsite();
    }

    public void onClickCodeContributors() {
        screen.showCodeContributorsWebsite();
    }

    public enum Message
    {
        YOU_ARE_NOW_A_DEVELOPER
    }

    public interface Screen
    {
        void showMessage(Message message);

        void showRateAppWebsite();

        void showSendFeedbackScreen();

        void showSourceCodeWebsite();

        void showTranslationWebsite();

        void showPrivacyPolicyWebsite();

        void showCodeContributorsWebsite();
    }

}
