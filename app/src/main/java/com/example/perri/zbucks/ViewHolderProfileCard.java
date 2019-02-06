package com.example.perri.zbucks;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewHolderProfileCard extends ViewHolder {
    private ImageView profile_photo;
    private ImageView background_image;
    private TextView tv_title;
    private TextView tv_balance;
    public long userId;

    public ViewHolderProfileCard(View profileView) {
        super(profileView);
        profile_photo = profileView.findViewById(R.id.profile_img);
        background_image = profileView.findViewById(R.id.card_background);
        tv_title = profileView.findViewById(R.id.txt_profile_name);
        tv_balance = profileView.findViewById(R.id.txt_profile_balance);
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
