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

package com.herman.habits.preferences

import android.content.*
import android.preference.*
import com.herman.androidbase.*
import com.herman.habits.R
import com.herman.habits.core.*
import com.herman.habits.core.preferences.*
import javax.inject.*

@AppScope
class SharedPreferencesStorage
@Inject constructor(
        @AppContext context: Context
) : SharedPreferences.OnSharedPreferenceChangeListener, Preferences.Storage {

    private val sharedPrefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    private var preferences: Preferences? = null

    init {
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false)
    }

    override fun clear() = sharedPrefs.edit().clear().apply()

    override fun getBoolean(key: String, defValue: Boolean) =
            sharedPrefs.getBoolean(key, defValue)

    override fun getInt(key: String, defValue: Int) =
            sharedPrefs.getInt(key, defValue)

    override fun getLong(key: String, defValue: Long) =
            sharedPrefs.getLong(key, defValue)

    override fun getString(key: String, defValue: String): String =
            sharedPrefs.getString(key, defValue)!!

    override fun onAttached(preferences: Preferences) {
        this.preferences = preferences
    }

    override fun putBoolean(key: String, value: Boolean) =
            sharedPrefs.edit().putBoolean(key, value).apply()

    override fun putInt(key: String, value: Int) =
            sharedPrefs.edit().putInt(key, value).apply()

    override fun putLong(key: String, value: Long) =
            sharedPrefs.edit().putLong(key, value).apply()

    override fun putString(key: String, value: String) =
            sharedPrefs.edit().putString(key, value).apply()

    override fun remove(key: String) =
            sharedPrefs.edit().remove(key).apply()

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences,
                                           key: String) {
        val preferences = this.preferences ?: return
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        when (key) {
            "pref_checkmark_reverse_order" ->
                preferences.isCheckmarkSequenceReversed = getBoolean(key, false)
            "pref_sticky_notifications" ->
                preferences.setNotificationsSticky(getBoolean(key, false))
            "pref_led_notifications" ->
                preferences.setNotificationsLed(getBoolean(key, false))
            "pref_feature_sync" ->
                preferences.isSyncEnabled = getBoolean(key, false)
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
}
