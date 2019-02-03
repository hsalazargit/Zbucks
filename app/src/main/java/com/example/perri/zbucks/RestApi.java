package com.example.perri.zbucks;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestApi
{
    private static final String SCHEME = "http://";
    private static final String DOMAIN = "192.168.1.18:";
    private static final String PORT = "3003";

    private static final String ROOT_URL = SCHEME + DOMAIN + PORT + "/";

    // rest api endpoints
    private static final String USER_LIST_ENDPOINT = "users";

    private RequestQueue requestQueue;

    public RestApi(MainActivity _this)
    {
        requestQueue= Volley.newRequestQueue(_this);
    }

    public JsonObjectRequest GetUsers()
    {
        Log.i("REST API", "Get users");

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                ROOT_URL + USER_LIST_ENDPOINT,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("Rest api Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Rest api Response(err)", error.toString());
                    }
                }

        );
        requestQueue.add(objectRequest);


        Log.i("REST API", "Returning object");
        return null;
    }


    //public static AsyncHttpClient client = new AsyncHttpClient();
}
