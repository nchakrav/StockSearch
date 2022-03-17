package com.nikhilchakravartula.stocksearch.network;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    static RequestQueue requestQueue;
    public static RequestQueue newRequestQueue(Context context)
    {
        if(requestQueue==null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }
}
