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

package com.herman.habits.sync;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import androidx.core.app.*;

import com.herman.habits.*;
import com.herman.habits.core.preferences.*;
import com.herman.habits.receivers.*;

public class SyncService extends Service implements Preferences.Listener
{
    private SyncManager syncManager;

    private Preferences prefs;

    private ConnectivityReceiver connectivityReceiver;

    public SyncService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        Intent notificationIntent = new Intent(this, SyncService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
            .setContentTitle("7-Habit Tracker")
            .setContentText("Sync service running")
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setContentIntent(pendingIntent)
            .build();

        startForeground(99999, notification);

        connectivityReceiver = new ConnectivityReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(connectivityReceiver, filter);

        HabitsApplication app = (HabitsApplication) getApplicationContext();
        syncManager = app.getComponent().getSyncManager();
        syncManager.startListening();

        prefs = app.getComponent().getPreferences();
        prefs.addListener(this);
    }

    @Override
    public void onSyncFeatureChanged()
    {
        if(!prefs.isSyncEnabled()) stopSelf();
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(connectivityReceiver);
        syncManager.stopListening();
    }
}
