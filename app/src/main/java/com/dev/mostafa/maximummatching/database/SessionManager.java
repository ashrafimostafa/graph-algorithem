package com.dev.mostafa.maximummatching.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private String pr_name = "prefrences";

    private Context context;
    private SharedPreferences preferences;

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(pr_name, Context.MODE_PRIVATE);
    }

    public boolean getFirstTime() {
        return preferences.getBoolean("first_time", true);
    }

    public void setFirstTime(boolean firstTime) {
        preferences.edit().putBoolean("first_time", firstTime).apply();
    }

}
