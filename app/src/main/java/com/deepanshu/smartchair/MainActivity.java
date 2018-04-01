package com.deepanshu.smartchair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editor = this.getSharedPreferences("value", MODE_PRIVATE).edit();

        ToggleButton toggle = (ToggleButton) findViewById(R.id.alarm);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    editor.putString("enable", "1");
                    editor.apply();
                    startService(new Intent(getBaseContext(), Service_to_request.class));

                } else {
                    // The toggle is disabled
                    editor.putString("enable", "0");
                    editor.apply();
                    stopService(new Intent(getBaseContext(), Service_to_request.class));

                }
            }
        });
    }





}
