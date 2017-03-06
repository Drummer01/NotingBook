package com.labs.notingbook.theming;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.labs.notingbook.R;

/**
 * Created by Drummer on 05.03.2017.
 */

public class ThemeManager {
    private static ThemeManager instance;

    public static ThemeManager getInstance(@Nullable Context ctx) {
        if(instance == null) {
            instance = new ThemeManager(ctx);
        }
        instance.setContext(ctx);
        return instance;
    }

    public static final int DEFAULT_THEME = R.style.AppTheme;
    private static final String THEME_KEY = "noting_current_theme";

    private int currentTheme = -1;

    private int tempTheme = -1;

    private Context ctx;

    private void setContext(Context ctx) {
        this.ctx = ctx;
    }

    private ThemeManager(Context ctx) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        currentTheme = pref.getInt(THEME_KEY, DEFAULT_THEME);
    }

    public void setTheme(int themeId) {
        tempTheme = themeId;
    }

    public void saveTheme() {
        currentTheme = tempTheme;
        tempTheme = -1;
        SharedPreferences.Editor pref = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        pref.putInt(THEME_KEY, currentTheme);
        pref.apply();
    }

    public int getCurrentThemeOrDefault() {
        return currentTheme == -1 ? DEFAULT_THEME : currentTheme;
    }


}
