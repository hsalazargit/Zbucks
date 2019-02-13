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
    public AdapterListener onClickListener;

    public Adapter(Context context, ArrayList<Item> data, AdapterType adapterType, AdapterListener listener) {
        this.context = context;
        this.data = data;
        this.adapterType = adapterType;
        this.onClickListener = listener;
    }

    // Method allows us to update our data for this adapter. Useful when data
    // is being retrieved by an async method, and we need to init the adapter
    // first with an empty arrayList. This method will call notifyDataSetChanged() if notifyChange == true;
    public void setData(ArrayList<Item> newData, boolean notifyChange)
    {
        this.data = newData;

        if (notifyChange)
        {
            this.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_profile, parent, false);

        switch (adapterType) {
            case profileCard:
                return new ViewHolderProfileCard(view, (ProfileCardListener) onClickListener, this.context);
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

interface AdapterListener {
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


