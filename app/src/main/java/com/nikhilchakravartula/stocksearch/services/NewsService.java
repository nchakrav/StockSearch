package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.CompanyEarningModel;
import com.nikhilchakravartula.stocksearch.models.NewsModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsService {
    private static final String URL =
            Constants.BASE_URL + "news/?ticker=";
    public static interface NewsListener
    {
        void onResponse(List<NewsModel> newsModels);
        void onError(String error);
    };


    public static void getNews(Context context,
                                          String text,
                                          NewsListener newsListener)
    {
        Gson gson= new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    List<NewsModel> newsModels = Arrays.asList(gson.fromJson(response.toString(),
                            NewsModel[].class));
                    Log.d("News service",newsModels.toString());
                    newsListener.onResponse(newsModels);
                },
                error -> {
                    Log.d("News service","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
