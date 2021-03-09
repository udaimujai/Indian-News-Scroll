package com.example.trial_1;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

public class RemoteViewsUtil {
    public static void initWidgetViews(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

      //  views.setViewVisibility(R.id.imageView, View.VISIBLE);
       // views.setImageViewResource(R.id.imageView, android.R.drawable.ic_media_play);

    }
    public static void updatePlayPause(RemoteViews views, boolean isRunning) {
      //  views.setImageViewResource(R.id.nxtNews, (android.R.drawable.ic_media_pause ) );
        views.setImageViewResource(R.id.updater, (R.drawable.update));
}
}
