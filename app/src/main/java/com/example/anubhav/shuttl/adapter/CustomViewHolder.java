package com.example.anubhav.shuttl.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anubhav.shuttl.R;
import com.example.anubhav.shuttl.activity.DetailActivity;
import com.example.anubhav.shuttl.model.Item;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by anubhav on 04/08/17.
 */

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtTitle, txtText, txtFrom, txtDate;
    public ImageView imgProfile;
    public Button btnChoice;
    private Context context;
    private List<Item> itemList;
    public LinearLayout llDate;
    private CardView card;
    private int state = 0;
    private static final int REQUEST_CODE = 100;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    public CustomViewHolder(View itemView, final Context context, List<Item> itemList) {
        super(itemView);
        this.context = context;
        this.itemList = itemList;
        txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtFrom = (TextView) itemView.findViewById(R.id.txtFrom);
        txtText = (TextView) itemView.findViewById(R.id.txtText);
        imgProfile = (ImageView) itemView.findViewById(R.id.imgProf);
        btnChoice = (Button) itemView.findViewById(R.id.btnSelect);
        llDate = (LinearLayout) itemView.findViewById(R.id.llDate);
        card = (CardView) itemView.findViewById(R.id.card);
        itemView.setOnClickListener(this);
        btnChoice.setOnClickListener(this);

        prefs = context.getSharedPreferences("button_state", MODE_PRIVATE);
        editor = context.getSharedPreferences("button_state", MODE_PRIVATE).edit();
        editor.putInt("state", 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSelect:
                if (prefs.getInt("state", -1) == 0) {
                    btnChoice.setText(R.string.unlike);
                    btnChoice.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    editor.putInt("state", 1);
                } else if (prefs.getInt("state", -1) == 1) {
                    btnChoice.setText(R.string.like);
                    btnChoice.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
                    editor.putInt("state", 0);
                }
                editor.apply();
                break;
            case R.id.card:
                Item item = itemList.get(getAdapterPosition());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("obj", item);
                context.startActivity(intent);
                //((Activity) context).startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }
}
