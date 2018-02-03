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

package com.herman.habits.activities.habits.show;

import android.support.annotation.*;
import android.view.*;

import com.herman.androidbase.activities.*;
import com.herman.habits.*;
import com.herman.habits.core.ui.screens.habits.show.*;

import javax.inject.*;

import dagger.*;

@ActivityScope
public class ShowHabitsMenu extends BaseMenu
{
    @NonNull
    private Lazy<ShowHabitMenuBehavior> behavior;

    @Inject
    public ShowHabitsMenu(@NonNull BaseActivity activity,
                          @NonNull Lazy<ShowHabitMenuBehavior> behavior)
    {
        super(activity);
        this.behavior = behavior;
    }

    @Override
    public boolean onItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_edit_habit:
                behavior.get().onEditHabit();
                return true;

            case R.id.export:
                behavior.get().onExportCSV();
                return true;

            case R.id.action_delete:
                behavior.get().onDeleteHabit();
                return true;

            default:
                return false;
        }
    }

    @Override
    protected int getMenuResourceId()
    {
        return R.menu.show_habit;
    }
}
