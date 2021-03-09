package com.example.trial_1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class Util {

    public static void registerWidgetStartPauseOnClickListener(Context context, int widgetId, RemoteViews views) {

        //Register an Intent so that onClicks on the widget are received by PingWidgetProvider.onReceive()
        //Create an Intent, set PING_WIDGET_TOGGLE action to it, put EXTRA_APPWIDGET_ID as extra
        Intent clickIntent = new Intent(context, NewsOnAir.class);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        clickIntent.setAction(Constants.PING_WIDGET_TOGGLE);
        //Construct a PendingIntent using the Intent above
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, clickIntent, 0);
        //Register pendingIntent in RemoteViews onClick
       // views.setImageViewResource(R.id.imageView, android.R.drawable.ic_media_play);
        views.setOnClickPendingIntent(R.id.nxtNews, pendingIntent);
        //register intent for update button
        Intent clickIntent_1 = new Intent(context, NewsOnAir.class);
        clickIntent_1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        clickIntent_1.setAction(Constants.ACTION_UPDATE);
        //Construct a PendingIntent using the Intent above
        PendingIntent pendingIntent_1 = PendingIntent.getBroadcast(context, widgetId, clickIntent_1, 0);
        //Register pendingIntent in RemoteViews onClick
        // views.setImageViewResource(R.id.imageView, android.R.drawable.ic_media_play);
        views.setOnClickPendingIntent(R.id.updater, pendingIntent_1);
    }
}
