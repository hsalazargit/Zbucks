package com.example.perri.zbucks;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

public class ViewHolderProfileCard extends ViewHolder {
    public long userId;

    private final int DELTA_INCREMENT_BY = 1;
    private final int DELTA_UPDATE_DELAY = 3;  // in seconds

    private ImageView profile_photo;
    private ImageView background_image;
    private TextView tv_title;
    private TextView tv_balance;
    private TextView tv_balance_delta;
    private Button button_up;
    private Button button_down;
    private Context appContext;


    // The integer value used to determine how much to adjust a profile's wallet balance by
    private int balanceDelta;
    private long startTime = 0;
    private Timer timer;

    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
      @Override
      public void run() {
          long milliseconds = System.currentTimeMillis() - startTime;
          int seconds = (int) (milliseconds / 1000);

          // should we post update to backend?
          if (seconds >= DELTA_UPDATE_DELAY) {
              // and stop timer
              timerHandler.removeCallbacks(timerRunnable);

              if (balanceDelta != 0 ) {
                  // only update if non-zero delta
                  updateDelta();
              }

              clearBalanceDelta();
          }

          timerHandler.postDelayed(this, 500);
      }
    };

    public ViewHolderProfileCard(View profileView, final ProfileCardListener listener, Context context) {
        super(profileView);
        this.appContext = context;
        this.profile_photo = profileView.findViewById(R.id.profile_img);
        this.background_image = profileView.findViewById(R.id.card_background);
        this.tv_title = profileView.findViewById(R.id.txt_profile_name);
        this.tv_balance = profileView.findViewById(R.id.txt_profile_balance);
        this.button_up = profileView.findViewById(R.id.btn_up);
        this.button_down = profileView.findViewById(R.id.btn_down);
        this.tv_balance_delta = profileView.findViewById(R.id.txt_balance_delta);

        this.balanceDelta = 0;

        // add button listeners
        this.button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Behaviour for "up" button i
                listener.buttonUpOnClick(v, getAdapterPosition());
                updateBalanceDeltaOnClick(DELTA_INCREMENT_BY);
            }
        });

        this.button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Behavior for "down" button
                listener.buttonDownOnClick(v, getAdapterPosition());
                updateBalanceDeltaOnClick(DELTA_INCREMENT_BY * -1);
            }
        });
    }

    @Override
    public void initViewHolder(ArrayList<Item> data, int position) {
        //this.background_image.setImageResource(data.get(position).getBackground());
        //this.profile_photo...
        User userObj = (User) data.get(position);
        String bal = String.valueOf(userObj.getBalance());
        this.tv_balance.setText(bal); //data.get(position).getBalance();
        this.tv_title.setText(userObj.getFirstName());
        this.userId = userObj.getId();
        this.tv_balance_delta.setEnabled(false);
    }

    private void updateBalanceDeltaOnClick(int delta){
        // 1. we want to start/restart the timer that will trigger the api call to update wallet balance
        balanceDelta += delta;

        // update the value shown in screen
        this.tv_balance_delta.setText(String.valueOf(balanceDelta));
        tv_balance_delta.setEnabled(true);

        // start the timer
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 500);
    }

    private void updateDelta() {
        // update
        RestApi.PutWalletDelta(this.userId, balanceDelta, this.appContext, new ServerResponseCallback() {
            @Override
            public void onSuccessResponse(Adapter adapter, ArrayList<User> resultList, Context context) {
                // Write was successful
                RestApi.GetWalletBalance(String.valueOf(userId), context, new ServerResponseCallback() {
                    @Override
                    public void onSuccessResponse(Adapter adapter, ArrayList<User> resultList, Context context) {
                        User user = resultList.get(0); // we only expect a single response

                        // update view for user balance
                        tv_balance.setText(String.valueOf(user.getBalance()));
                    }
                });
            }
        });
    }

    private void clearBalanceDelta() {
        // reset balanceDelta and update view
        balanceDelta = 0;
        tv_balance_delta.setEnabled(false);
        tv_balance_delta.setText("");
    }
}

interface ProfileCardListener extends AdapterListener {
    void buttonUpOnClick(View v, int position);
    void buttonDownOnClick(View v, int position);
}