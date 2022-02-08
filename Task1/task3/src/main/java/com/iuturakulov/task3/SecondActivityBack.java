package com.iuturakulov.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class SecondActivityBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Добавляет кнопку назад
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_second_back);
        final TextView textView = findViewById(R.id.textFromActivity);
        String newString;
        if (savedInstanceState != null) {
            newString = (String) savedInstanceState.getSerializable("text");
        } else {
            Bundle extras = getIntent().getExtras();
            newString = extras == null ? null : extras.getString("text");
        }
        textView.setText(newString);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(view -> finish());
    }
}