package com.example.edmaths.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    private final String PREF_NAME = "EdMath";
    private final String USERNAME_PREF = "username";

    private final SharedPreferences preferences;

    public PreferenceUtil(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUsername(String username) {
        preferences.edit().putString(USERNAME_PREF, username).apply();
    }

    public String getUserName() {
        return preferences.getString(USERNAME_PREF, "");
    }
}
