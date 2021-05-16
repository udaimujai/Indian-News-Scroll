package com.example.trial_1;

import java.util.HashMap;

public class NewsLinks{

       public HashMap<Integer, String> hm = new HashMap<Integer, String>();{
        hm.put(1, "http://newsonair.com/Top_rss.aspx");
        hm.put(2, "http://newsonair.com/National_rss.aspx");
        //hm.put(2, "http://newsonair.nic.in/National_rss.aspx");
       // hm.put(2, "https://www.google.com/");
        hm.put(3, "http://newsonair.com/State_rss.aspx");
        hm.put(4, "http://newsonair.com/International_rss.aspx");
        hm.put(5, "http://newsonair.com/business_rss.aspx");
        hm.put(6, "http://newsonair.com/Sports_rss.aspx");


    }
     public HashMap<Integer, String> hm_urls (){
         return hm;
     }


    
    
}
