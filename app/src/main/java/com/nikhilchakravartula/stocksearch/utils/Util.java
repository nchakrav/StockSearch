package com.nikhilchakravartula.stocksearch.utils;

import android.util.Log;

import com.nikhilchakravartula.stocksearch.models.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static String getTimeAgo(NewsModel newsModel, int position) {
        String timeAgo = "";
        Date today = new Date();
        long diffInSeconds = today.getTime()/1000-newsModel.getDatetime();
        Log.d("today time : newsModel time",today.getTime()+" : "+ newsModel.getDatetime());

        Log.d("getTimeAgo",diffInSeconds+"");
        if (diffInSeconds < 0) {
            return timeAgo;
        }
        if (diffInSeconds < 60*60) {
            int mins = (int) (diffInSeconds / 60);
            timeAgo = mins + " mins ago";
        }
        else if (diffInSeconds < 60*60*24) {
            int hours = (int) (diffInSeconds / (60*60));
            timeAgo = hours + " hours ago";
        }
        else {
            int days = (int) (diffInSeconds / (60*60*24));
            timeAgo = days + " days ago";
        }
        return timeAgo;
    }
}
