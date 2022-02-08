package com.iuturakulov.task2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.fullName);
        final EditText editText = (EditText) findViewById(R.id.inputTextName);
        final Button button = (Button) findViewById(R.id.editTextButton);
        button.setOnClickListener(view -> {
            textView.setText(editText.getText());
        });
    }

}