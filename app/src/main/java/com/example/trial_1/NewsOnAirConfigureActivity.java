package com.example.trial_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * The configuration screen for the {@link NewsOnAir NewsOnAir} AppWidget.
 */
public class NewsOnAirConfigureActivity extends Activity {
    public static final String PREFS_NAME = "com.example.trial_1.NewsOnAir";

    public static final String PREF_PREFIX_KEY = "appwidget_";
    public static final String PREFS_DATE = "appwidgetd_";
    public int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    public static final  String SHARED_PREFS = "SharedPrefs";

    public Switch switch_national;
    public Switch switch_international;
    public Switch switch_state;
    public Switch switch_business;
    public Switch switch_sports;
    public Switch switch_regional;

    public static final  String SWITCH_NATIONAL = "switch_national";
    public static final  String SWITCH_INTERNATIONAL = "switch_international";
    public static final  String SWITCH_STATE = "switch_state";
    public static final  String SWITCH_BUSINESS = "switch_business";
    public static final  String SWITCH_SPORTS = "switch_sports";
    public static final  String SWITCH_REGIONAL = "switch_regional";


    public  boolean See_SWITCH_NATIONAL;
    public  boolean See_SWITCH_INTERNATIONAL;
    public  boolean See_SWITCH_STATE;
    public  boolean See_SWITCH_BUSINESS;
    public  boolean See_SWITCH_SPORTS;
    public  boolean See_SWITCH_REGIONAL;
    private static String TAG = NewsOnAirConfigureActivity.class.getSimpleName();
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onClick(View v) {
            final Context context = NewsOnAirConfigureActivity.this;
            // When the button is clicked, store switch pref locally
            saveSwitchpref(context, mAppWidgetId);
            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            try {
                NewsPuller.gatherNeed(context,mAppWidgetId );
               // Log.d(TAG, "gatherNeed()");
                NewsOnAir.updateAppWidget(context, appWidgetManager, mAppWidgetId);
                //Log.d(TAG, "updateAppWidget()");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public NewsOnAirConfigureActivity() {
        super();
    }


    // Write the switch pref to the SharedPreferences object for this widget
   public void saveSwitchpref(@org.jetbrains.annotations.NotNull Context context, int appWidgetId) {
       Log.d(TAG, "saveTitlePref()");
        SharedPreferences sharedPreferences= (SharedPreferences) context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SWITCH_NATIONAL+appWidgetId, switch_national.isChecked());
            editor.putBoolean(SWITCH_INTERNATIONAL+appWidgetId, switch_international.isChecked());
            editor.putBoolean(SWITCH_STATE+appWidgetId, switch_state.isChecked());
            editor.putBoolean(SWITCH_BUSINESS+appWidgetId, switch_business.isChecked());
            editor.putBoolean(SWITCH_SPORTS+appWidgetId, switch_sports.isChecked());
            editor.putBoolean(SWITCH_REGIONAL+appWidgetId, switch_regional.isChecked());
            editor.apply();
    }
    // Write the news to the SharedPreferences object for this widget
    public static void saveNewsPref(Context context, int appWidgetId, String news) {
        //Log.d(TAG, "saveNewsPref()");
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, news);
        prefs.apply();
    }
    // Write the date to the SharedPreferences object for this widget
    public static void saveNewsdate(Context context, int appWidgetId, String dates) {
        Log.d(TAG, "saveNewsdate()");
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        //int random_double = (int) (Math.random() * (50 - 30 + 1) + 30);
        //prefs.putString(PREFS_DATE + appWidgetId, String.valueOf(random_double));
        prefs.putString(PREFS_DATE + appWidgetId, dates);
        prefs.apply();
    }
    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadNews(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String News = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (News != null) {
            return News;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }
    static String loadDate(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String date_load = prefs.getString(PREFS_DATE + appWidgetId, null);
        if (date_load != null) {
            return date_load;
        } else {
            return context.getString(R.string.DateView);
        }
    }
    public static boolean[] loadswitchPref(Context context, int appWidgetId) {
        Log.d(TAG, "loadswitchPref()");
        boolean[] Bool_pref = new boolean[6];
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, 0);
        Bool_pref[0] =   sharedPreferences.getBoolean(SWITCH_NATIONAL+appWidgetId,false);
        Bool_pref[1] =   sharedPreferences.getBoolean(SWITCH_INTERNATIONAL+appWidgetId,true);
        Bool_pref[2] =   sharedPreferences.getBoolean(SWITCH_STATE+appWidgetId,true);
        Bool_pref[3] =   sharedPreferences.getBoolean(SWITCH_BUSINESS+appWidgetId,true);
        Bool_pref[4] =   sharedPreferences.getBoolean(SWITCH_SPORTS+appWidgetId,true);
        Bool_pref[5] =   sharedPreferences.getBoolean(SWITCH_REGIONAL+appWidgetId,true);
        return Bool_pref;
    }
public void loadData(Context context, int appWidgetId) {
    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        See_SWITCH_NATIONAL = sharedPreferences.getBoolean(SWITCH_NATIONAL+appWidgetId,false );
        See_SWITCH_INTERNATIONAL = sharedPreferences.getBoolean(SWITCH_INTERNATIONAL+appWidgetId ,false);
        See_SWITCH_STATE = sharedPreferences.getBoolean(SWITCH_STATE+appWidgetId ,false);
        See_SWITCH_BUSINESS = sharedPreferences.getBoolean(SWITCH_BUSINESS+appWidgetId,false);
        See_SWITCH_SPORTS = sharedPreferences.getBoolean(SWITCH_SPORTS+appWidgetId,false );
        See_SWITCH_REGIONAL = sharedPreferences.getBoolean(SWITCH_REGIONAL+appWidgetId,false );
}
public void updateviews(){
    Log.d(TAG, "updateviews()");
    switch_national.setChecked(true);
    switch_international.setChecked(true);
    switch_state.setChecked(true);
    switch_business.setChecked(true);
    switch_sports.setChecked(true);
    switch_regional.setChecked(true);

}
    static void deleteTitlePref(Context context, int appWidgetId) {
        Log.d(TAG, "deleteTitlePref()");
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(SWITCH_NATIONAL+appWidgetId);
        prefs.remove(SWITCH_INTERNATIONAL+appWidgetId);
        prefs.remove(SWITCH_STATE+appWidgetId);
        prefs.remove(SWITCH_BUSINESS+appWidgetId);
        prefs.remove(SWITCH_SPORTS+appWidgetId);
        prefs.remove(SWITCH_REGIONAL+appWidgetId);
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.d(TAG, "onCreate()");
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.news_on_air_configure);
        switch_national=(Switch)findViewById(R.id.switch_National);
        switch_international=(Switch)findViewById(R.id.switch_international);
        switch_state=(Switch)findViewById(R.id.switch_State);
        switch_business=(Switch)findViewById(R.id.switch_Business);
        switch_sports=(Switch)findViewById(R.id.switch_Sports);
        switch_regional=(Switch)findViewById(R.id.switch_Regional);
 //       mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this  activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        //loadData();
        Log.d(TAG, "updateviews()");
        updateviews();
//        mAppWidgetText.setText(loadTitlePref(NewsOnAirConfigureActivity.this, mAppWidgetId));
    }

}

