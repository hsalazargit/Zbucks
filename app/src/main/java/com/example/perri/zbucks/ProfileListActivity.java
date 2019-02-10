package com.example.perri.zbucks;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class ProfileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final RecyclerView recyclerView = findViewById(R.id.recycleView_list);

        RestApi.GetChildrenOfParent("7", new ServerResponseCallback() {
            @Override
            public void onSuccessResponse(ArrayList<User> res, Context context)
            {
                Log.d("Results: ", res.toString());

                ArrayList<Item> itemList = new ArrayList<>();
                itemList.addAll(res);

                // place result list into adapter for display
                Adapter adapter = new Adapter(context, itemList, AdapterType.profileCard, new ProfileCardListener() {
                    @Override
                    public void buttonUpOnClick(View v, int position){
                        Log.d("LISTENER: ", "Up button clicked on item: " + position);

                    }

                    @Override
                    public void buttonDownOnClick(View v, int position){
                        Log.d("LISTENER: ", "Down button clicked on item: " + position);
                    }
                });

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        }, this);
    }
}
