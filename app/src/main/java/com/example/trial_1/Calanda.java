package com.example.trial_1;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Calanda {
    public static String dater() {
        Calendar c=Calendar.getInstance();
        SimpleDateFormat dateformat=new SimpleDateFormat("dd");
        String datetime=dateformat.format(c.getTime());
        //String i = Integer.parseInt(datetime.trim());
        return datetime;
    }


}
