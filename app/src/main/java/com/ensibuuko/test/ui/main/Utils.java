package com.ensibuuko.test.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static final String MyPREFERENCES = "PreferenceInfo";
    public static final String USER_OBJECT_KEY = "user";
    public static final String KEY_FIRSTTIME = "first_time_launch";

    public static void save(String key, String value, Context context){
        SharedPreferences prefs = getSharedPreferencesWithin(context);
        prefs.edit().putString(key, value).apply();

    }

    public static void save(String key, boolean value, Context context){
        SharedPreferences prefs = getSharedPreferencesWithin(context);
        prefs.edit().putBoolean(key, value).apply();

    }

    public static boolean getKey(String key, Context context){
        SharedPreferences prefs = getSharedPreferencesWithin(context);
        return prefs.getBoolean(key, true);
    }

    private static SharedPreferences getSharedPreferencesWithin(Context context) {

        return context.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
    }
}
