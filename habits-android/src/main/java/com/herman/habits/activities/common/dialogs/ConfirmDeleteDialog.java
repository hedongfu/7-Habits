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

package com.herman.habits.activities.common.dialogs;

import android.content.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.*;

import com.google.auto.factory.*;

import com.herman.androidbase.activities.*;
import com.herman.habits.R;
import com.herman.habits.core.ui.callbacks.*;

import butterknife.*;

/**
 * Dialog that asks the user confirmation before executing a delete operation.
 */
@AutoFactory(allowSubclasses = true)
public class ConfirmDeleteDialog extends AlertDialog
{
    @BindString(R.string.delete_habits_message)
    protected String question;

    @BindString(android.R.string.yes)
    protected String yes;

    @BindString(android.R.string.no)
    protected String no;

    protected ConfirmDeleteDialog(@Provided @ActivityContext Context context,
                                  @NonNull OnConfirmedCallback callback)
    {
        super(context);
        ButterKnife.bind(this);

        setTitle(R.string.delete_habits);
        setMessage(question);
        setButton(BUTTON_POSITIVE, yes, (dialog, which) -> callback.onConfirmed());
        setButton(BUTTON_NEGATIVE, no, (dialog, which) -> {});
    }
}
