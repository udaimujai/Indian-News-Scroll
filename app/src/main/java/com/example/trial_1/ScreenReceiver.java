
package com.example.trial_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;


public class ScreenReceiver extends BroadcastReceiver {
    public static final String SCREEN_STATE = "SCREEN_STATE";
    public static IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
private static String TAG = ScreenReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d(TAG, "onReceive:ScreenReceiver");
        Intent i = new Intent(context, PingWidgetUpdateService.class);
        i.putExtra(SCREEN_STATE, intent.getAction());
        context.startService(i);
    }
}
