package com.example.anubhav.shuttl.model;

import java.io.Serializable;

/**
 * Created by anubhav on 03/08/17.
 */

public class Item implements Serializable {

    private String name, imageUrl, title, text, description;
    private long time;

    public Item() {
    }

    public Item(String name, String imageUrl, String title, String text, String description, long time) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.title = title;
        this.text = text;
        this.description = description;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
