package com.example.trial_1;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Arrays;
import java.util.Collections;


    public class UpdateWidgetService extends Service {
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        private static String TAG = "service_Udaiservice_Udai";

        public static void onStartCommander(Context context, Intent intent) {
            //final Context context = UpdateWidgetService.this;

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context
                    .getApplicationContext());

            Bundle extras = intent.getExtras();
            //if (extras != null)
            int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            if (extras != null) {
                if (widgetId != 0) {
                   Log.d(TAG, "buttonclick");
                    spliter(appWidgetManager, context, intent, widgetId);
                } else if (widgetId == 0) {
                    int Widged_IDS_A[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewsOnAir.class));
                    for (int w:Widged_IDS_A) {
                        if(w != AppWidgetManager.INVALID_APPWIDGET_ID) {
                            Log.d(TAG, "screenUnlock");
                            spliter(appWidgetManager, context, intent, w);
                        }
                    }
                }
            }
        }
        private static void spliter( AppWidgetManager appWidgetManager,Context context, Intent intent, int widgetId)  {
                String dd2 = NewsOnAirConfigureActivity.loadNews(context, widgetId);
                String loader2 = NewsOnAirConfigureActivity.loadDate(context,widgetId);
                // Log.d(TAG, "checknews" + dd2);
                String[] parts = dd2.split("\u272f" + "\u272f" + "\u272f");
                // Log.d(TAG, "afinder" + parts.length);
                dd2 ="";
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i] + "\u272f" + "\u272f" + "\u272f";
                }
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.news_on_air);
                Collections.shuffle(Arrays.asList(parts));
                parts[0]= "                          "+parts[0];
                //dd2 = parts.toString();
                String dd3 = TextUtils.join("", parts);
                //PingWidgetUpdateService.handleWidgetClick(context, widgetId, intent);
                //UpdateWidgetService.onStartCommander(context,intent);
                remoteViews.setTextViewText(R.id.appwidget_text, dd3);
                remoteViews.setTextViewText(R.id.DateView, loader2);
                remoteViews.setImageViewResource(R.id.nxtNews, (android.R.drawable.ic_media_pause));
                remoteViews.setImageViewResource(R.id.updater, (R.drawable.update));
                Util.registerWidgetStartPauseOnClickListener(context, widgetId, remoteViews);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);

            }

        }
