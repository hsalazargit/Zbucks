package com.example.perri.zbucks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private RestApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initRestApi();

        api.GetUsers();
    }


    public void initRestApi()
    {
        if (api == null)
        {
            Log.i("REST API", "Creating instance");
            api = new RestApi(this);
        }
    }
}
