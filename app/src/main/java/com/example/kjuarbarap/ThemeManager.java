package com.example.kjuarbarap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ThemeManager {
    public static void setTheme(Context context) {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int theme = sharedpreferences.getInt("theme", 0);
        context.setTheme(theme == 0 ? R.style.Theme_KjuArBarAp_NoActionBar : R.style.Theme_KjuArBarAp2_NoActionBar);
    }

    public static int getTheme(Context context) {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int theme = sharedpreferences.getInt("theme", 0);
        return theme == 0 ? R.style.Theme_KjuArBarAp : R.style.Theme_KjuArBarAp2;
    }
}
