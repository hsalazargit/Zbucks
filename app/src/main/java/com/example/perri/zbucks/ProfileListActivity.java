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
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileListActivity extends AppCompatActivity {
    // The RecycleView used in this activity
    private RecyclerView _recyclerView;
    // The adapter used to bind User objects in the recycleViewer
    private Adapter _adapter;
    // the parent id for children being displayed in this activity
    private String _parentId = "3"; // TODO: need to pass this value as param from the main activity, static for now



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile_list);

        // initialize key components
        this.initViewAdapter();
        this.initChildrenProfileCards();
    }

    private void initViewAdapter() {
        // create recycleView and adapter for this activity
        _recyclerView = findViewById(R.id.recycleView_list);

        _adapter = new Adapter(this, new ArrayList<Item>(), AdapterType.profileCard, new ProfileCardListener() {
            @Override
            public void buttonUpOnClick(View v, int position){
                Log.d("LISTENER: ", "Up button clicked on item: " + position);
            }

            @Override
            public void buttonDownOnClick(View v, int position){
                Log.d("LISTENER: ", "Down button clicked on item: " + position);
            }
        });

        // attach adapter
        _recyclerView.setAdapter(_adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initChildrenProfileCards() {
        RestApi.GetChildrenOfParent(_adapter, _parentId, this, new ServerResponseCallback() {
            @Override
            public void onSuccessResponse(Adapter adapter, ArrayList<User> res, Context context)
            {
                Log.d("Results: ", res.toString());

                ArrayList<Item> itemList = new ArrayList<>();
                itemList.addAll(res);

                // place result list into adapter for display, and notify update
                adapter.setData(itemList, true);
            }
        });
    }

}
