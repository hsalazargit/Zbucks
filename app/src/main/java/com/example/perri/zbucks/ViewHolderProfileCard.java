package com.example.perri.zbucks;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewHolderProfileCard extends ViewHolder {
    private ImageView profile_photo;
    private ImageView background_image;
    private TextView tv_title;
    private TextView tv_balance;

    private Button button_up;
    private Button button_down;

    public long userId;

    public ViewHolderProfileCard(View profileView, final ProfileCardListener listener) {
        super(profileView);
        profile_photo = profileView.findViewById(R.id.profile_img);
        background_image = profileView.findViewById(R.id.card_background);
        tv_title = profileView.findViewById(R.id.txt_profile_name);
        tv_balance = profileView.findViewById(R.id.txt_profile_balance);
        button_up = profileView.findViewById(R.id.btn_up);
        button_down = profileView.findViewById(R.id.btn_down);
        // add button listeners
        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Behaviour for "up" button
                listener.buttonUpOnClick(v, getAdapterPosition());
            }
        });

        button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Behavior for "down" button
                listener.buttonDownOnClick(v, getAdapterPosition());
            }
        });
    }

    @Override
    public  void initViewHolder(ArrayList<Item> data, int position) {
        //this.background_image.setImageResource(data.get(position).getBackground());
        //this.profile_photo...
        User userObj = (User) data.get(position);
        String bal = String.valueOf(userObj.getBalance());
        this.tv_balance.setText(bal); //data.get(position).getBalance();
        this.tv_title.setText(userObj.getFirstName());
        this.userId = userObj.getId();
    }
}



interface ProfileCardListener extends AdapterListener {
    void buttonUpOnClick(View v, int position);
    void buttonDownOnClick(View v, int position);
}