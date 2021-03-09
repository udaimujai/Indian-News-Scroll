
package com.example.trial_1;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PingWidgetUpdateService extends Service {
    private static String TAG = PingWidgetUpdateService.class.getSimpleName();
    private static BroadcastReceiver mReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
       //Register ScreenReceiver to screen on/off broadcasts
        registerReceiver(mReceiver = new ScreenReceiver(), ScreenReceiver.filter);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.d(TAG, "onStartCommand()");
    final Context context = PingWidgetUpdateService.this;

        if (intent != null) {
          //  Log.d(TAG, "screenState1");
            //Try to get extras
            String screenState = intent.getStringExtra(ScreenReceiver.SCREEN_STATE);
            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            //Check if intent is coming from ScreenReceiver
            if (screenState != null)
                handleScreenState(screenState,intent);
                //Check if intent came from a Widget click
            else if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID)
               // Log.d(TAG, "if intent came from a Widget click");
                handleWidgetClick(context,widgetId, intent);
            else
                Log.d(TAG, "Error: Got an unknown onStartCommand() in PingWidgetUpdateService");
        }
        // If service get killed, after returning from here, restart
        return START_STICKY;
    }
    public void handleScreenState(String screenState, Intent intent) {
        Log.d(TAG, "        views.setImageViewResource(R.id.nxtNews, (R.drawable.ic_media_play));" + screenState);
        final Context context = PingWidgetUpdateService.this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context
                .getApplicationContext());
        //If screen has just been turned off, stop all threads and clear mAsyncTasks
        if(Intent.ACTION_SCREEN_OFF.equals(screenState)){
            //Log.d(TAG, "handleScreenState() ACTION_SCREEN_OFF");
                UpdateWidgetService.onStartCommander(context, intent);
            //Toast.makeText(getApplicationContext(), "service Running",Toast.LENGTH_SHORT).show();
        }
    }
    public static void handleWidgetClick(Context context, int widgetId, Intent intent) {
        //Get widget data from SharedPreferences
                //Log.d(TAG, " handleWidgetClick");
       // final Context context = PingWidgetUpdateService.this;
        UpdateWidgetService.onStartCommander(context,intent);
        //Toast.makeText(context.getApplicationContext(), "handleWidgetClick",Toast.LENGTH_SHORT).show();

    }
}

