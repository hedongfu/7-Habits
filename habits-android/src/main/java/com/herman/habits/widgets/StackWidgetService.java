package com.herman.habits.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.NonNull;

import com.herman.habits.HabitsApplication;
import com.herman.habits.core.models.Habit;
import com.herman.habits.core.models.HabitList;
import com.herman.habits.core.models.HabitNotFoundException;
import com.herman.habits.core.preferences.Preferences;
import com.herman.habits.core.utils.StringUtils;

import java.util.ArrayList;

import static android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT;
import static android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH;
import static android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT;
import static android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH;
import static com.herman.androidbase.utils.InterfaceUtils.dpToPixels;
import static com.herman.habits.widgets.StackWidgetService.HABIT_IDS;
import static com.herman.habits.widgets.StackWidgetService.WIDGET_TYPE;

public class StackWidgetService extends RemoteViewsService
{
    public static final String WIDGET_TYPE = "WIDGET_TYPE";
    public static final String HABIT_IDS = "HABIT_IDS";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context context;
    private int widgetId;
    private long[] habitIds;
    private StackWidgetType widgetType;
    private ArrayList<RemoteViews> remoteViews = new ArrayList<>();

    public StackRemoteViewsFactory(Context context, Intent intent)
    {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        int widgetTypeValue = intent.getIntExtra(WIDGET_TYPE, -1);
        String habitIdsStr = intent.getStringExtra(HABIT_IDS);

        if (widgetTypeValue < 0) throw new RuntimeException("invalid widget type");
        if (habitIdsStr == null) throw new RuntimeException("habitIdsStr is null");

        widgetType = StackWidgetType.getWidgetTypeFromValue(widgetTypeValue);
        habitIds = StringUtils.splitLongs(habitIdsStr);
    }

    public void onCreate()
    {

    }

    public void onDestroy()
    {

    }

    public int getCount()
    {
        return habitIds.length;
    }

    @NonNull
    public WidgetDimensions getDimensionsFromOptions(@NonNull Context ctx,
                                                     @NonNull Bundle options)
    {
        int maxWidth = (int) dpToPixels(ctx, options.getInt(OPTION_APPWIDGET_MAX_WIDTH));
        int maxHeight = (int) dpToPixels(ctx, options.getInt(OPTION_APPWIDGET_MAX_HEIGHT));
        int minWidth = (int) dpToPixels(ctx, options.getInt(OPTION_APPWIDGET_MIN_WIDTH));
        int minHeight = (int) dpToPixels(ctx, options.getInt(OPTION_APPWIDGET_MIN_HEIGHT));

        return new WidgetDimensions(minWidth, maxHeight, maxWidth, minHeight);
    }

    public RemoteViews getViewAt(int position)
    {
        Log.i("StackRemoteViewsFactory", "getViewAt " + position);
        if (position < 0 || position > remoteViews.size()) return null;
        return remoteViews.get(position);
    }

    @NonNull
    private BaseWidget constructWidget(@NonNull Habit habit,
                                       @NonNull Preferences prefs)
    {
        switch (widgetType)
        {
            case CHECKMARK:
                return new CheckmarkWidget(context, widgetId, habit);
            case FREQUENCY:
                return new FrequencyWidget(context, widgetId, habit, prefs.getFirstWeekday());
            case SCORE:
                return new ScoreWidget(context, widgetId, habit);
            case HISTORY:
                return new HistoryWidget(context, widgetId, habit, prefs.getFirstWeekday());
            case STREAKS:
                return new StreakWidget(context, widgetId, habit);
        }

        throw new IllegalStateException();
    }

    public RemoteViews getLoadingView()
    {
        Bundle options = AppWidgetManager.getInstance(context).getAppWidgetOptions(widgetId);
        EmptyWidget widget = new EmptyWidget(context, widgetId);
        widget.setDimensions(getDimensionsFromOptions(context, options));
        RemoteViews landscapeViews = widget.getLandscapeRemoteViews();
        RemoteViews portraitViews = widget.getPortraitRemoteViews();
        return new RemoteViews(landscapeViews, portraitViews);
    }

    public int getViewTypeCount()
    {
        return 1;
    }

    public long getItemId(int position)
    {
        return habitIds[position];
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public void onDataSetChanged()
    {
        Log.i("StackRemoteViewsFactory", "onDataSetChanged started");

        HabitsApplication app = (HabitsApplication) context.getApplicationContext();
        Preferences prefs = app.getComponent().getPreferences();
        HabitList habitList = app.getComponent().getHabitList();
        Bundle options = AppWidgetManager.getInstance(context).getAppWidgetOptions(widgetId);
        ArrayList<RemoteViews> newRemoteViews = new ArrayList<>();

        if (Looper.myLooper() == null) Looper.prepare();

        for (long id : habitIds)
        {
            Habit h = habitList.getById(id);
            if (h == null) throw new HabitNotFoundException();

            BaseWidget widget = constructWidget(h, prefs);
            widget.setDimensions(getDimensionsFromOptions(context, options));

            RemoteViews landscapeViews = widget.getLandscapeRemoteViews();
            RemoteViews portraitViews = widget.getPortraitRemoteViews();
            newRemoteViews.add(new RemoteViews(landscapeViews, portraitViews));

            Log.i("StackRemoteViewsFactory", "onDataSetChanged constructed widget " + id);
        }

        remoteViews = newRemoteViews;
        Log.i("StackRemoteViewsFactory", "onDataSetChanged ended");
    }
}
