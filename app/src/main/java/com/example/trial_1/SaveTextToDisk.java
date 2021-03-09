package com.example.trial_1;

import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.example.trial_1.NewsOnAirConfigureActivity.SHARED_PREFS;
import static org.jsoup.helper.Validate.fail;

public class SaveTextToDisk {

//    private static String TAG = SaveTextToDisk.class.getSimpleName();
private static String TAG = SaveTextToDisk.class.getSimpleName();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void saveNewsInFile(Context context, int appWidgetId, String news) throws IOException {
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        File[] list = ContextCompat.getExternalFilesDirs(context, null);
        String hotNews = news;
//        if (!list[1].exists()) {
//            fail("unable to create data directory");
//        }
//        if (!list[1].isDirectory()) {
//            fail("exists, but is not a directory");
//        }
        try {
            String fileName = "KannamaNewsMart" + appWidgetId + ".txt";
            File gpxfile = new File(context.getFilesDir(), fileName);
 //           Log.d(TAG, String.valueOf(gpxfile));
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(hotNews);
            writer.flush();
            writer.close();
            NewsOnAirConfigureActivity.saveNewsPref(context,appWidgetId,hotNews);
            String ss = Calanda.dater();
            NewsOnAirConfigureActivity.saveNewsPref(context,appWidgetId,hotNews);
            NewsOnAirConfigureActivity.saveNewsdate(context,appWidgetId,ss);
            NewsOnAir.updateAppWidget(context,appWidgetManager  ,appWidgetId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String loadNewsInFile(Context context, int appWidgetId) throws IOException {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
 //       Log.d(TAG, "1loadNewsInFile");
        File[] list = ContextCompat.getExternalFilesDirs(context, null);
        List<String> mLines = new ArrayList<String>();
        String dd1 = null;
        StringBuilder text = new StringBuilder();
        //       String path = String.valueOf(Environment.getExternalStorageDirectory()+ "/Kannama/"+"KannamaNewsMart"+appWidgetId+".txt");
        String path = String.valueOf(list[1] + "/KannamaNewsMart" + appWidgetId + ".txt");
   //     Log.d(TAG, String.valueOf(path) + "1");
        AssetManager am = context.getAssets();
        File file = context.getFileStreamPath("KannamaNewsMart" + appWidgetId + ".txt");
        File file1 = new File(list[1], "KannamaNewsMart" + appWidgetId + ".txt");
   //     Log.d(TAG, String.valueOf(file));
        Boolean ss = list[1].canRead();
        ss = list[1].canWrite();
        ss = list[1].canRead();
        //(context.getFilesDir()+File.separator+"KannamaNewsMart"+File.separator+"KannamaNewsMart"+appWidgetId+".txt");
        if (!file1.exists()) {
   //        Log.d(TAG, String.valueOf("null"));
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            String line;
            while ((line = br.readLine()) != null) {
                mLines.addAll(Arrays.asList(line));
                dd1= mLines.get(1);

                //break;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        NewsOnAirConfigureActivity.saveNewsPref(context,appWidgetId,dd1);
        NewsOnAir.updateAppWidget(context,appWidgetManager  ,appWidgetId);
        return dd1;
    }
    public static void deleterOfLocalFiles(Context context, int appWidgetId) {
        File[] list = ContextCompat.getExternalFilesDirs(context, null);
        list[1]  = new File(String.valueOf(list[1] + "/KannamaNewsMart" + appWidgetId + ".txt"));
        list[1].delete();
        if (list[1].delete()) {
          Log.d(TAG, "deleterOfLocalfiles"+"deleted");
        } else {
         Log.d(TAG, "deleterOfLocalfiles"+"NOT-deleted");
        }
    }
    }
