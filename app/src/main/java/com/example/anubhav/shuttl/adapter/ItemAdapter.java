package com.example.anubhav.shuttl.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.anubhav.shuttl.R;
import com.example.anubhav.shuttl.model.Item;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by anubhav on 04/08/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private final int WO_IMAGE = 0, W_IMAGE = 1;
    private static final int REQUEST_CODE = 100;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position).getImageUrl() == null || itemList.get(position).getText() == null) {
            return WO_IMAGE;
        } else {
            return W_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = null;
        switch (viewType) {
            case W_IMAGE:
                v = inflater.inflate(R.layout.item_row_image_and_title, parent, false);
                break;

            case WO_IMAGE:
                v = inflater.inflate(R.layout.item_row_image_or_text, parent, false);
                break;
        }
        viewHolder = new CustomViewHolder(v, context, itemList);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder vh = (CustomViewHolder) holder;
        switch (holder.getItemViewType()) {
            case W_IMAGE:
                configureWithImageHolder(vh, position);
                break;
            case WO_IMAGE:
                configureWithoutImageHolder(vh, position);
                break;
        }
    }

    private void configureWithImageHolder(final CustomViewHolder holder, int position) {
        Item currentItem = itemList.get(position);
        holder.txtTitle.setText(currentItem.getTitle());
        holder.txtText.setText(currentItem.getText());
        holder.txtFrom.setText("From " + currentItem.getName());
        String imgUrl = currentItem.getImageUrl();
        if (imgUrl != null) {
            Picasso.with(context).load(imgUrl).resize(250, 250).centerCrop().into(holder.imgProfile);
        } else {
            holder.imgProfile.setVisibility(View.GONE);
        }
        if (currentItem.getTime() == -1) {
            holder.llDate.setVisibility(View.GONE);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            String dateString = formatter.format(new Date(currentItem.getTime()));
            holder.txtDate.setText(dateString);
            holder.llDate.setVisibility(View.VISIBLE);
        }
    }

    private void configureWithoutImageHolder(final CustomViewHolder holder, int position) {
        Item currentItem = itemList.get(position);
        holder.txtTitle.setText(currentItem.getTitle());
        holder.txtText.setText(currentItem.getText());
        holder.txtFrom.setText("From " + currentItem.getName());
        String imgUrl = currentItem.getImageUrl();
        if (imgUrl != null) {
            Glide.with(context).load(imgUrl).into(holder.imgProfile);
            holder.imgProfile.setVisibility(View.VISIBLE);
            holder.txtText.setVisibility(View.GONE);
        } else {
            holder.txtText.setText(currentItem.getText());
            holder.txtText.setVisibility(View.VISIBLE);
            holder.imgProfile.setVisibility(View.GONE);
        }
        if (currentItem.getTime() == -1) {
            holder.llDate.setVisibility(View.GONE);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            String dateString = formatter.format(new Date(currentItem.getTime()));
            holder.txtDate.setText(dateString);
            holder.llDate.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
