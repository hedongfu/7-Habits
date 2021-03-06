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

package com.herman.habits.activities.habits.edit.views;

import android.content.*;
import android.text.*;
import android.util.*;
import android.view.*;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.herman.androidbase.utils.*;
import com.herman.habits.*;

import static com.herman.habits.utils.AttributeSetUtils.*;

/**
 * An EditText that shows an example usage when there is no text
 * currently set. The example disappears when the widget gains focus.
 */
public class ExampleEditText extends AppCompatEditText
    implements View.OnFocusChangeListener
{

    private String example;

    private String realText;

    private int color;

    private int exampleColor;

    private int inputType;

    public ExampleEditText(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        if (attrs != null)
            example = getAttribute(context, attrs, "example", "");

        inputType = getInputType();
        realText = getText().toString();
        color = getCurrentTextColor();
        init();
    }

    public String getRealText()
    {
        if(hasFocus()) return getText().toString();
        else return realText;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        if (!hasFocus) realText = getText().toString();
        updateText();
    }

    public void setExample(String example)
    {
        this.example = example;
        updateText();
    }

    public void setRealText(String realText)
    {
        this.realText = realText;
        updateText();
    }

    private void init()
    {
        StyledResources sr = new StyledResources(getContext());
        exampleColor = sr.getColor(R.attr.mediumContrastTextColor);
        setOnFocusChangeListener(this);
        updateText();
    }

    private void updateText()
    {
        if (realText.isEmpty() && !isFocused())
        {
            setTextColor(exampleColor);
            setText(example);
            setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
        else
        {
            setText(realText);
            setTextColor(color);
            setInputType(inputType);
        }

    }
}
