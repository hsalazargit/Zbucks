package com.example.perri.zbucks;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class RestApi
{
    // rest api root url settings
    private static final String SCHEME = "http://";
    private static final String DOMAIN = "192.168.1.18:";
    private static final String PORT = "3003";
    private static final String ROOT_URL = SCHEME + DOMAIN + PORT;

    // rest api endpoints
    private static final String USER_LIST_ENDPOINT = "/users";
    private static final String CHILDREN_OF_USERID_ENDPOINT = "/children/%s";
    private static final String USER_BY_ID_ENDPOINT = "users/%s";

    private RequestQueue requestQueue;

    public RestApi(MainActivity _this)
    {
        requestQueue= Volley.newRequestQueue(_this);
    }

    public void GetUsers(ServerResponseCallback callback) {
        Log.d("REST API", "Get users");

        ApiRequest(USER_LIST_ENDPOINT, callback);
    }

    public void GetChildrenOfParent(String id, ServerResponseCallback callback) {
        Log.d("REST API", "GetChildrenOfUser: " + id);

        String endpoint = String.format(CHILDREN_OF_USERID_ENDPOINT, id);
        ApiRequest(endpoint, callback);
    }

    public void GetUserById(String id, ServerResponseCallback callback){
        Log.d("REST API", "GET USER ID: " + id);

        String endpoint = String.format(USER_BY_ID_ENDPOINT, id);
        ApiRequest(endpoint, callback);
    }

    private void ApiRequest(String ApiEndpoint, final ServerResponseCallback callback) {
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

                        callback.onSuccessResponse(userList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Rest api Response(err)", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);
    }
}
