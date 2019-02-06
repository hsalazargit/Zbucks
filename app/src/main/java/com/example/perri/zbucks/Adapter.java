package com.example.perri.zbucks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private ArrayList<Item> data;
    private AdapterType adapterType;

    public Adapter(Context context, ArrayList<Item> data, AdapterType adapterType) {
        this.context = context;
        this.data = data;
        this.adapterType = adapterType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_profile, parent, false);

        if (adapterType == AdapterType.profileCard)
        {
            return new ViewHolderProfileCard(view);
        }

        switch (adapterType) {
            case profileCard:
                return new ViewHolderProfileCard(view);
            case parentCard:
                // see ViewHolderProfileCard class for example
            default:
                throw new UnsupportedOperationException("Need to extend ViewHolder class and override initViewHolder method");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.initViewHolder(data, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

// wrapper class used to data lists intended to
// be used with Adapter
class Item {
}

enum AdapterType {
    profileCard,
    parentCard
    // add new adapterTypes here
}


