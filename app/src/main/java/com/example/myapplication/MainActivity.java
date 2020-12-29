package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button but_set = (Button) findViewById(R.id.button_setting);

    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void onClick_SP(View view) {
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
    }

    public void onClick_RP(View view) {
        Intent intent = new Intent(MainActivity.this, RefereeActivity.class);
        startActivity(intent);
    }

    public void onClick_FP(View view) {
        Intent intent = new Intent(MainActivity.this, ResultGroupActivity.class);
        startActivity(intent);
    }





}
