package com.labs.notingbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.labs.notingbook.theming.ThemeManager;
import com.smartous.notingbook.MainActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] ThemeArray = {
            R.style.AppTheme,
            R.style.Theme_HoloDark
    };

    private Spinner mThemeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeManager.getInstance(this).getCurrentThemeOrDefault());
        setContentView(R.layout.activity_settings);

        findViewById(R.id.save_theme_btn).setOnClickListener(this);

        ArrayList<String> themes = new ArrayList<>();
        themes.add("Default theme");
        themes.add("Dark theme");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, themes);

        mThemeSpinner = (Spinner) findViewById(R.id.theme_spinner);
        mThemeSpinner.setAdapter(spinnerAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_theme_btn : {
                ThemeManager tm = ThemeManager.getInstance(this);
                int index = mThemeSpinner.getSelectedItemPosition();
                Log.d("index", index + "");

                tm.setTheme(ThemeArray[index]);
                tm.saveTheme();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
    }
}
