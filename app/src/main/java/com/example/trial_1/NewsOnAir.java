package com.example.trial_1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link NewsOnAirConfigureActivity NewsOnAirConfigureActivity}
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class NewsOnAir<ss> extends AppWidgetProvider {
    private static String TAG = NewsOnAir.class.getSimpleName();
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) throws IOException {
        Log.d(TAG, "updateAppWidget");
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //SharedPreferences prefs = context.getSharedPreferences("SharedPrefs", 0);
        // Construct the RemoteViews object
        String loader1 = NewsOnAirConfigureActivity.loadNews(context,appWidgetId);
        String loader2 = NewsOnAirConfigureActivity.loadDate(context,appWidgetId);
        Log.d(TAG, "NewsOnAirConfigureActivity.loadTitlePref");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_on_air);
        Log.d(TAG, "RemoteViews");
           // views.setTextViewText(R.id.appwidget_text, widgetText); 
        views.setTextViewText(R.id.appwidget_text, loader1);
        views.setTextViewText(R.id.DateView, loader2);
        Log.d(TAG, "setTextViewText");
        Util.registerWidgetStartPauseOnClickListener(context, appWidgetId, views);
        Intent serviceIntent = new Intent(context.getApplicationContext(), PingWidgetUpdateService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        ContextCompat.startForegroundService(context, serviceIntent);
            // Instruct the widget manager to update the widget
       // appWidgetManager.updateAppWidget(appWidgetId, views);
        views.setImageViewResource(R.id.nxtNews, (R.drawable.ic_media_play));
        appWidgetManager.updateAppWidget(appWidgetId,views);
        Log.d(TAG, "updateAppWidget");
        Toast.makeText(context, "News Updated",Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_on_air);
            Log.d(TAG, "onUpdate");
            Util.registerWidgetStartPauseOnClickListener(context, appWidgetId, views);
            String loader2 = NewsOnAirConfigureActivity.loadNews(context, appWidgetId);
            Log.d(TAG, "onUpdate_outer" + loader2.getClass().getSimpleName());
            String jkl = "100";
            if (!loader2.equals((jkl))) {
                try {
                    NewsPuller.gatherNeed(context, appWidgetId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "gatherNeed()");
                try {
                    updateAppWidget(context, appWidgetManager, appWidgetId);
                    views.setImageViewResource(R.id.nxtNews, (R.drawable.ic_media_play));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   public void onReceive(Context context, Intent intent){
       AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context
               .getApplicationContext());
       //Log.d(TAG, "123onReceive()");
       RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.news_on_air);
      // remoteViews.setImageViewResource(R.id.nxtNews, (android.R.drawable.ic_media_pause));
       remoteViews.setImageViewResource(R.id.updater, (R.drawable.update));
       Bundle extras = intent.getExtras();
       String received_tocken;
       if (extras != null) {
           int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

           Util.registerWidgetStartPauseOnClickListener(context, widgetId, remoteViews);

       if( Constants.PING_WIDGET_TOGGLE.equals (intent.getAction())) {
           Log.d(TAG, "12020onReceive()");
           UpdateWidgetService.onStartCommander(context,intent);
/*           int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
           Intent serviceIntent = new Intent(context.getApplicationContext(), PingWidgetUpdateService.class);
           serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
           ContextCompat.startForegroundService(context, serviceIntent);*/
           }else if (Constants.ACTION_UPDATE.equals (intent.getAction())) {
           Log.d(TAG, "ACTION_UPDATE(pressed)");
           int[] Widged_IDS_A = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewsOnAir.class));
           received_tocken = "100";
           onUpdate(context, appWidgetManager, Widged_IDS_A);

          /* try {
               NewsPuller.gatherNeed(context, widgetId);
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (ExecutionException e) {
               e.printStackTrace();
           } catch (TimeoutException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }*/
       }

       super.onReceive(context, intent);
       //Toast.makeText(context, "receiving broadcast",Toast.LENGTH_SHORT).show();
    }
   }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            NewsOnAirConfigureActivity.deleteTitlePref(context, appWidgetId);
            //SaveTextToDisk.deleterOfLocalFiles(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG, "onEnabled()");
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled()");
        super.onDisabled(context);
    }
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        Log.d(TAG, "onRestored()");

        super.onRestored(context, oldWidgetIds, newWidgetIds);

    }
}

