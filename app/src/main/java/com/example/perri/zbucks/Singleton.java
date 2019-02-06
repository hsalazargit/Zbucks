package com.example.perri.zbucks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {
    private static Singleton _instance;
    private  RequestQueue _requestQueue;
    private static Context _ctx;

    protected Singleton(Context context){
        _ctx = context;
        _requestQueue = getRequestQueue();
    }

    public static synchronized Singleton getInstance(Context context) {
        if (_instance == null) {
            _instance = new Singleton(context);
        }

        return _instance;
    }

    public <T> void  addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    private RequestQueue getRequestQueue() {
        if (_requestQueue == null) {
            _requestQueue = Volley.newRequestQueue(_ctx.getApplicationContext());
        }

        return _requestQueue;
    }


}
