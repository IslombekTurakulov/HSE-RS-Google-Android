package com.iuturakulov.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        final Button button = (Button) findViewById(R.id.buttonToActivity);
        final EditText editText = (EditText) findViewById(R.id.inputTextName);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivityBack.class);
            intent.putExtra("text", editText.getText().toString());
            startActivity(intent);
        });
    }

}