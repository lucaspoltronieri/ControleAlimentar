package com.lucaspoltronieri.controlealimentar;

import android.os.Bundle;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {
    private SwitchCompat switchDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.configuracoes));

        switchDark = findViewById(R.id.switchDarkMode);

        boolean dark = getSharedPreferences(ThemeUtils.PREFS_NAME, MODE_PRIVATE)
                .getBoolean(ThemeUtils.KEY_DARK_MODE, false);
        switchDark.setChecked(dark);

        switchDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ThemeUtils.saveDarkMode(SettingsActivity.this, isChecked);
                AppCompatDelegate.setDefaultNightMode(
                        isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
                );
                recreate(); // atualiza esta tela
            }
        });
    }
}
