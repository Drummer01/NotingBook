package com.labs.notingbook.theming;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

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

        if(instance.ctx == null) {
            return null;
        }

        if(ctx != null) {
            instance.setContext(ctx);
        }
        return instance;
    }

    public static final int DEFAULT_THEME = R.style.AppTheme;
    public static final float DEFAULT_TEXT_SCALE = 1f;

    private static final String SCALE_TEXT_KEY = "noting_current_text_scale";
    private static final String THEME_KEY = "noting_current_theme";

    private int currentTheme = -1;

    private int tempTheme = -1;

    private float currentTextScale = DEFAULT_TEXT_SCALE;

    public int getCurrentTextScale() {
        Log.d("get currenttextscale", currentTextScale+ "");

        return (int)(currentTextScale * 100);
    }

    public void setCurrentTextScale(int currentTextScale) {
        Log.d("set currenttextscale", currentTextScale + " " + currentTextScale / 100f+ "");

        this.currentTextScale = currentTextScale / 100f;
    }


    private Context ctx;

    private void setContext(Context ctx) {
        this.ctx = ctx;
    }

    private ThemeManager(Context ctx) {
        this.ctx = ctx;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        currentTheme = pref.getInt(THEME_KEY, DEFAULT_THEME);
        currentTextScale = pref.getFloat(SCALE_TEXT_KEY, DEFAULT_TEXT_SCALE);
    }

    public void setTheme(int themeId) {
        tempTheme = themeId;
    }

    public void saveTheme() {
        currentTheme = tempTheme;
        tempTheme = -1;
        SharedPreferences.Editor pref = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        pref.putInt(THEME_KEY, currentTheme);
        pref.putFloat(SCALE_TEXT_KEY, currentTextScale);
        pref.apply();
    }

    public int getCurrentThemeOrDefault() {
        return currentTheme == -1 ? DEFAULT_THEME : currentTheme;
    }

    public static void changeTextViewFontSize(TextView textView) {
        float size = textView.getTextSize();
        Log.d("TEST", size * ThemeManager.getInstance(textView.getContext()).currentTextScale + "");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size * ThemeManager.getInstance(textView.getContext()).currentTextScale);
    }

}
