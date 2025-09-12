package com.lucaspoltronieri.controlealimentar;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtils {
    public static final String PREFS_NAME = "prefs_app";
    public static final String KEY_DARK_MODE = "dark_mode";

    public static void applySavedTheme(Context ctx) {
        boolean dark = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(
                dark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    public static void saveDarkMode(Context ctx, boolean dark) {
        SharedPreferences.Editor ed = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        ed.putBoolean(KEY_DARK_MODE, dark);
        ed.apply();
    }
}
