package com.example.anubhav.shuttl.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.anubhav.shuttl.R;
import com.example.anubhav.shuttl.adapter.ItemAdapter;
import com.example.anubhav.shuttl.model.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> itemList;
    private ItemAdapter itemAdapter;
    private RecyclerView recycler;
    private int state;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readDateFromFile();

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, itemList);
        recycler.setAdapter(itemAdapter);

        prefs = getSharedPreferences("state", MODE_PRIVATE);
    }

    private void readDateFromFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("data.json")));
            Gson gson = new GsonBuilder().create();
            Item[] response = gson.fromJson(reader, Item[].class);

            itemList = new ArrayList<>(Arrays.asList(response));
            sortListItems();
            modifyList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyList() {
        long itemTime = itemList.get(0).getTime();

        for (int i = 1; i < itemList.size(); i++) {
            long currentItemTime = itemList.get(i).getTime();
            if (currentItemTime == itemTime) {
                itemList.get(i).setTime(-1);
            } else {
                itemTime = currentItemTime;
            }
        }
    }

    private void sortListItems() {
        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return String.valueOf(o2.getTime()).compareTo(String.valueOf(o1.getTime()));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        state = prefs.getInt("state", -1);
    }
}