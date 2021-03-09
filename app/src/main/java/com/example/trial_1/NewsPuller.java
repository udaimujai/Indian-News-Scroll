package com.example.trial_1;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public  class NewsPuller {
    private static String TAG = NewsPuller.class.getSimpleName();
    static NewsLinks x = new NewsLinks();
    private  static ReadFromWeb tasker = new ReadFromWeb();
    private  static ReadFromWeb tasker11 = new ReadFromWeb();
    public static String getStr0,getStr1,getStr2,getStr3,getStr4,getStr5;
    public static String hot_news = "";
    public static boolean[] s1 = new boolean[5];

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void gatherNeed(Context context, int appWidgetId) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Log.d(TAG, "gatherNeed()");
        getStr0 = "";
        getStr1 = "";
        getStr2 = "";
        getStr3 = "";
        getStr4 = "";
        getStr5 = "";

        HashMap<Integer, String> urls = x.hm_urls();
        s1 = NewsOnAirConfigureActivity.loadswitchPref( context, appWidgetId );
        if (s1[0]){
            Log.d(TAG, "0()");
            getStr0 = tasker.execute(urls.get(1)).get(1,TimeUnit.MINUTES);

        }
        if (s1[1]){

            Log.d(TAG, "1()");
            getStr1 = tasker11.execute(urls.get(2)).get(5, TimeUnit.MINUTES);
        }
        if (s1[2]){
            ReadFromWeb tasker1 = new ReadFromWeb();
            Log.d(TAG, "2()");
            getStr2 = tasker1.execute(urls.get(3)).get(1, TimeUnit.MINUTES);
        }
        if (s1[3]){
            ReadFromWeb tasker2 = new ReadFromWeb();
            Log.d(TAG, "3()");
            getStr3 = tasker2.execute(urls.get(4)).get(1, TimeUnit.MINUTES);
        }
        if (s1[4]){
            ReadFromWeb tasker3 = new ReadFromWeb();
            Log.d(TAG, "4()");
            getStr4 = tasker3.execute(urls.get(5)).get(1, TimeUnit.MINUTES);
        }
        if (s1[5]){
            ReadFromWeb tasker4 = new ReadFromWeb();
            Log.d(TAG, "5()");
            getStr5 = tasker4.execute(urls.get(6)).get(1, TimeUnit.MINUTES);
        }
        Log.d(TAG, "hot_news");
        hot_news= getStr0+getStr1+getStr2+getStr3+getStr4+getStr5;
        String ss = Calanda.dater();
        NewsOnAirConfigureActivity.saveNewsPref(context,appWidgetId,hot_news);
        NewsOnAirConfigureActivity.saveNewsdate(context,appWidgetId,ss);
        NewsOnAir.updateAppWidget(context,appWidgetManager  ,appWidgetId);
        //SaveTextToDisk.saveNewsInFile(context,appWidgetId,hot_news);
    }
    public static class ReadFromWeb extends AsyncTask<String, Void, String> {
        // public String words = "";
        public String line = "";
        public String words1 = "";
        boolean counter = true;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground()");
            //String  params= new String();
            String webURL = params[0];
            String  sta_des = "<description>";
            String end_des = "</description>";
            URL url = null;
            try {
                url = new URL(webURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            InputStream is = null;
            try {
                assert url != null;
                is = url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));


            while (counter) {
                try {
                    if (((line = br.readLine()) == null)) {
                        counter =false;
                    }
                    if (((line = br.readLine()) != null)){
                        if (line.contains(sta_des)) {
                            line= line.replaceAll(sta_des,"");
                            line=line.replaceAll(end_des,"");
                            line=line.trim();
                            words1 = words1 +"---"+ line;
                            //break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
            return words1;
        }

        @Override

        protected void onPostExecute(String resluts) {
            Log.d(TAG, "onPostExecute()");
            //handleResults(resluts, 0);
            super.onPostExecute(resluts);

        }
/*    public String  handleResults(String str1,int putMode){
        Log.d(TAG, "handleResults()");
            String lx1 = "Waiting...";

            int check_mode = putMode;
            if (check_mode != 1){
                lx1 = str1;
            }
        check_mode=0;
            return  lx1;
    }*/
    }
}
