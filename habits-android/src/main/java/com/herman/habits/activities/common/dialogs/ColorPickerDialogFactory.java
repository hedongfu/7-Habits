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

import android.content.*;

import com.herman.androidbase.activities.*;
import com.herman.androidbase.utils.*;
import com.herman.habits.R;
import com.herman.habits.utils.*;

import javax.inject.*;

@ActivityScope
public class ColorPickerDialogFactory
{
    private final Context context;

    @Inject
    public ColorPickerDialogFactory(@ActivityContext Context context)
    {
        this.context = context;
    }

    public ColorPickerDialog create(int paletteColor)
    {
        ColorPickerDialog dialog = new ColorPickerDialog();
        StyledResources res = new StyledResources(context);
        int color = PaletteUtils.getColor(context, paletteColor);

        dialog.initialize(R.string.color_picker_default_title, res.getPalette(),
            color, 4, com.android.colorpicker.ColorPickerDialog.SIZE_SMALL);

        return dialog;
    }
}
