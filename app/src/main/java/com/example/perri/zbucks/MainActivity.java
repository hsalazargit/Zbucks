package com.example.perri.zbucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // start parent selection activity
        Button fab = findViewById(R.id.listParentsButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showParentListActivity();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.listParentsButton:
                showParentListActivity();
                break;
            default:
                break;
        }

        return true;
    }

    public void showParentListActivity() {
        Intent parentListActivity = new Intent(this, ProfileListActivity.class);


        Bundle bundle = new Bundle();
        bundle.putString("listing", "parent");

        startActivity(parentListActivity);
    }
}