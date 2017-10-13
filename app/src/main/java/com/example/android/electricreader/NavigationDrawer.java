package com.example.android.electricreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NavigationDrawer {

    private int icon;
    private String title;

    public NavigationDrawer(int months) {

    }

    public NavigationDrawer(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

