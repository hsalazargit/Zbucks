package com.example.perri.zbucks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RestApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initRestApi();

        api.GetChildrenOfParent("4", new ServerResponseCallback() {
            @Override
            public void onSuccessResponse(ArrayList<User> res)
            {
                Log.d("Results: ", res.toString());
            }
        });
    }

    public void initRestApi()
    {
        if (api == null)
        {
            Log.d("REST API", "Creating instance");
            api = new RestApi(this);
        }
    }
}