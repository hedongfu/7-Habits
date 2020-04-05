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

package com.herman.habits.activities.common.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.herman.habits.*;
import com.herman.habits.core.models.*;
import com.herman.habits.core.utils.*;

import java.util.Calendar;

/**
 * Dialog that allows the user to pick one or more days of the week.
 */
public class WeekdayPickerDialog extends AppCompatDialogFragment implements
                                                                 DialogInterface.OnMultiChoiceClickListener,
                                                                 DialogInterface.OnClickListener
{
    private static final String KEY_SELECTED_DAYS = "selectedDays";
    private boolean[] selectedDays;

    private OnWeekdaysPickedListener listener;

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked)
    {
        selectedDays[which] = isChecked;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            selectedDays = savedInstanceState.getBooleanArray(KEY_SELECTED_DAYS);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray(KEY_SELECTED_DAYS, selectedDays);
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if (listener != null)
            listener.onWeekdaysSet(new WeekdayList(selectedDays));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.select_weekdays)
            .setMultiChoiceItems(DateUtils.getLongWeekdayNames(Calendar.SATURDAY),
                    selectedDays,
                    this)
            .setPositiveButton(android.R.string.yes, this)
            .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                dismiss();
            });

        return builder.create();
    }

    public void setListener(OnWeekdaysPickedListener listener)
    {
        this.listener = listener;
    }

    public void setSelectedDays(WeekdayList days)
    {
        this.selectedDays = days.toArray();
    }

    public interface OnWeekdaysPickedListener
    {
        void onWeekdaysSet(WeekdayList days);
    }
}
