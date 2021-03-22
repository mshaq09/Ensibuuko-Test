package com.ensibuuko.test.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    public static final String MyPREFERENCES = "PreferenceInfo";
    public static final String USERNAME_KEY = "user_name";
    public static final String NAME_KEY = "name";
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

    public static String getStringKey(String key, Context context){
        SharedPreferences prefs = getSharedPreferencesWithin(context);
        return prefs.getString(key, "");
    }

    private static SharedPreferences getSharedPreferencesWithin(Context context) {

        return context.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
