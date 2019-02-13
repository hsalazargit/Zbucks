package com.example.perri.zbucks;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestApi
{
    // rest api root url settings
    private static final String SCHEME = "http://";
    private static final String DOMAIN = "192.168.43.219:";//"192.168.1.18:";
    private static final String PORT = "3003";
    private static final String ROOT_URL = SCHEME + DOMAIN + PORT;

    // rest api endpoints
    private static final String ENDPOINT_GET_USERS = "/users";
    private static final String ENDPOINT_GET_CHILDREN_OF_ID = "/children/%s";
    private static final String ENDPOINT_GET_USER_BY_ID = "/users/%s";
    private static final String ENDPOINT_GET_WALLET_BY_ID = "/wallet/%s";
    private static final String ENDPOINT_PUT_WALLET_DELTA = "/wallet";


    private static Adapter _adapter;

    public static void GetUsers(Context _this, ServerResponseCallback callback) {
        Log.d("REST API", "Get users");

        GetRequest(ENDPOINT_GET_USERS, _this, callback);
    }

    public static void GetChildrenOfParent(Adapter adapter, String id, Context _this, ServerResponseCallback callback) {
        Log.d("REST API", "GetChildrenOfUser: " + id);

        _adapter = adapter;
        String endpoint = String.format(ENDPOINT_GET_CHILDREN_OF_ID, id);
        GetRequest(endpoint, _this, callback);
    }

    public static void GetUserById(String id, Context _this, ServerResponseCallback callback){
        Log.d("REST API", "GET USER ID: " + id);

        String endpoint = String.format(ENDPOINT_GET_USER_BY_ID, id);
        GetRequest(endpoint, _this, callback);
    }

    public static void GetWalletBalance(String id, Context _this, ServerResponseCallback callback) {
        Log.d("REST API", "GetWalletBalance id: " + id);

        String endpoint = String.format(ENDPOINT_GET_WALLET_BY_ID, id);

        // nullify _adapter since it is not needed for this api call,
        // and callee should not depend on in
        _adapter = null;

        GetRequest(endpoint, _this, callback);
    }

    public static void PutWalletDelta(long walletId, int delta, Context _this, ServerResponseCallback callback) {
        Log.d("REST API", "PutWalletDelta id: " + String.valueOf(walletId));

        JSONObject json = new JSONObject();
        try {
            json.put("wallet_id", walletId);
            json.put("balance_delta", delta);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        String endpoint = String.format(ENDPOINT_PUT_WALLET_DELTA);
        PutRequest(endpoint, json, _this, callback);
    }

    private static void PutRequest(String ApiEndpoint, JSONObject body, final Context _this, final ServerResponseCallback callback) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                ROOT_URL + ApiEndpoint,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccessResponse(_adapter, new ArrayList<User>(), _this);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Rest api PUT(err)", error.toString());
                    }
                }
        );

        Singleton.getInstance(_this).addToRequestQueue(objectRequest);
    }

    private static void GetRequest(String ApiEndpoint, final Context _this, final ServerResponseCallback callback) {
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                ROOT_URL + ApiEndpoint,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<User> userList = new ArrayList<>(response.length());

                        for (int i = 0; i < response.length(); i++)
                        {
                            User user = null;

                            try {
                                user = new Gson().fromJson(response.getString(i), User.class);
                            }
                            catch (JSONException e) {
                                Log.e("Exception", e.getMessage());
                            }

                            if (user != null) {
                                userList.add(user);
                            }
                        }
                        Log.d("Response - raw", response.toString());

                        callback.onSuccessResponse(_adapter, userList, _this);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Rest api Response(err)", error.toString());
                    }
                }
        );

        Singleton.getInstance(_this).addToRequestQueue(objectRequest);
    }
}
