package com.example.trial_1;

import java.util.HashMap;

public class NewsLinks{

       public HashMap<Integer, String> hm = new HashMap<Integer, String>();{
        hm.put(1, "https://newsonair.gov.in/Top_rss.aspx");
        hm.put(2, "https://newsonair.gov.in/National_rss.aspx");
        //hm.put(2, "http://newsonair.nic.in/National_rss.aspx");
       // hm.put(2, "https://www.google.com/");
        hm.put(3, "https://newsonair.gov.in/State_rss.aspx");
        hm.put(4, "https://newsonair.gov.in/International_rss.aspx");
        hm.put(5, "https://newsonair.gov.in/business_rss.aspx");
        hm.put(6, "https://newsonair.gov.in/Sports_rss.aspx");


    }
     public HashMap<Integer, String> hm_urls (){
         return hm;
     }


    
    
}
