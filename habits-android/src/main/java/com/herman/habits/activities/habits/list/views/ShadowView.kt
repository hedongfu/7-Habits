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

package com.herman.habits.activities.habits.list.views

import android.content.*
import android.view.*
import android.view.View.MeasureSpec.*
import com.herman.habits.*
import com.herman.habits.utils.*

@Suppress("DEPRECATION")
class ShadowView(context: Context) : View(context) {
    init {
        alpha = 0.2f
        background = resources.getDrawable(R.drawable.shadow)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec,
                        dp(2.0f).toInt().toMeasureSpec(EXACTLY))
    }
}