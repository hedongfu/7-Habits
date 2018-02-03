/*
 * Copyright (C) 2015-2018 Dongfu He <hedongfu@gmail.com>
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

package com.herman.habits.automation

import android.content.*
import dagger.*
import com.herman.habits.*
import com.herman.habits.core.models.*
import com.herman.habits.core.ui.widgets.*
import com.herman.habits.core.utils.*
import com.herman.habits.receivers.*

const val ACTION_CHECK = 0
const val ACTION_UNCHECK = 1
const val ACTION_TOGGLE = 2
const val EXTRA_BUNDLE = "com.twofortyfouram.locale.intent.extra.BUNDLE"
const val EXTRA_STRING_BLURB = "com.twofortyfouram.locale.intent.extra.BLURB"

class FireSettingReceiver : BroadcastReceiver() {

    private lateinit var allHabits: HabitList

    override fun onReceive(context: Context, intent: Intent) {
        val app = context.applicationContext as HabitsApplication
        val component = DaggerFireSettingReceiver_ReceiverComponent
                .builder()
                .habitsApplicationComponent(app.component)
                .build()
        allHabits = app.component.habitList
        val args = parseIntent(intent) ?: return
        val timestamp = DateUtils.getToday()
        val controller = component.widgetController

        when (args.action) {
            ACTION_CHECK -> controller.onAddRepetition(args.habit, timestamp)
            ACTION_UNCHECK -> controller.onRemoveRepetition(args.habit, timestamp)
            ACTION_TOGGLE -> controller.onToggleRepetition(args.habit, timestamp)
        }
    }

    private fun parseIntent(intent: Intent): Arguments? {
        val bundle = intent.getBundleExtra(EXTRA_BUNDLE) ?: return null
        val action = bundle.getInt("action")
        if (action < 0 || action > 2) return null
        val habit = allHabits.getById(bundle.getLong("habit")) ?: return null
        return Arguments(action, habit)
    }

    @ReceiverScope
    @Component(dependencies = arrayOf(HabitsApplicationComponent::class))
    internal interface ReceiverComponent {
        val widgetController: WidgetBehavior
    }

    private class Arguments(var action: Int, var habit: Habit)
}
