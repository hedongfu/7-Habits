/*
 * Copyright (C) 2018 Herman <ringtone.sky@gmail.com>
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

package com.herman.habits.activities.settings;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.herman.androidbase.activities.BaseActivity;
import com.herman.androidbase.activities.BaseScreen;
import com.herman.androidbase.utils.StyledResources;
import com.herman.habits.R;

/**
 * Activity that allows the user to view and modify the app settings.
 */
public class SettingsActivity extends BaseActivity
{
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        setupActionBarColor();

        adView = new AdView(this, getString(R.string.FB_BANNER_PLACEMENT_ID), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        AdSettings.addTestDevice("3659d362-0005-4c55-a3c8-3e7fa302a34e"); // Samsung A20
        AdSettings.addTestDevice("59c0c46b-26fd-46f0-9051-494a6926b1db"); // One Plus

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                System.out.println("Herman_debug:onError, Error: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
                System.out.println("Herman_debug:onAdLoaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });
        // Request an ad
        adView.loadAd();
    }

    private void setupActionBarColor()
    {
        StyledResources res = new StyledResources(this);
        int color = BaseScreen.getDefaultActionBarColor(this);

        if (res.getBoolean(R.attr.useHabitColorAsPrimary))
            color = res.getColor(R.attr.aboutScreenColor);

        BaseScreen.setupActionBarColor(this, color);
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
