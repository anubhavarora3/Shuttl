package com.example.anubhav.shuttl.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anubhav.shuttl.R;
import com.example.anubhav.shuttl.model.Item;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDate, txtDescp, txtFrom, txtText;
    private ImageView imgProf;
    private Button btnChoice;
    private int state, position;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDescp = (TextView) findViewById(R.id.txtDescp);
        txtFrom = (TextView) findViewById(R.id.txtFrom);
        txtText = (TextView) findViewById(R.id.txtText);
        imgProf = (ImageView) findViewById(R.id.imgProf);
        btnChoice = (Button) findViewById(R.id.btnSelect);
        prefs = getSharedPreferences("state", MODE_PRIVATE);
        editor = getSharedPreferences("state", MODE_PRIVATE).edit();
        setUpUI();
    }

    private void setUpUI() {
        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("obj");
        final SharedPreferences prefs = getSharedPreferences("button_state", MODE_PRIVATE);
        state = prefs.getInt("state", -1);
        //editor.putInt("state", state);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String dateString = formatter.format(new Date(item.getTime()));
        txtDate.setText(dateString);
        txtFrom.setText("FROM " + item.getName());
        txtDescp.setText(item.getDescription());
        txtText.setText(item.getText());

        if (item.getImageUrl() == null) {
            imgProf.setVisibility(View.GONE);
            txtText.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (item.getText() == null) {
            imgProf.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            txtText.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            imgProf.setAdjustViewBounds(true);
            imgProf.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        if (state == 0) {
            btnChoice.setText(R.string.like);
            btnChoice.setBackgroundColor(getResources().getColor(R.color.color_gray));
            editor.putInt("state", 1);
            editor.apply();
        } else if (state == 1) {
            btnChoice.setText(R.string.unlike);
            btnChoice.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            editor.putInt("state", 1);
            editor.apply();
        }


        btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 0) {
                    btnChoice.setText(R.string.unlike);
                    btnChoice.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    editor.putInt("state", 1);
                    editor.apply();
                } else if (state == 1) {
                    btnChoice.setText(R.string.like);
                    btnChoice.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    editor.putInt("state", 0);
                    editor.apply();
                }
            }
        });
        Picasso.with(this).load(item.getImageUrl()).into(imgProf);
    }

    @Override
    public void onBackPressed() {
        editor.putInt("state", state);
        super.onBackPressed();
        finish();
    }
}
