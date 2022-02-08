package com.example.task_21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int currentIndex = 0;
    int[] images = {
            R.drawable.photo, R.drawable.photo_2,
            R.drawable.photo_1, R.drawable.photo_3,
            R.drawable.photo_4, R.drawable.photo_5
    };

    ImageView imageView;
    Button buttonNext;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        buttonBack = findViewById(R.id.buttonBack);
        buttonNext = findViewById(R.id.buttonNext);
        imageView.setImageResource(images[currentIndex]);
        buttonBack.setOnClickListener(view -> {
            --currentIndex;
            if (currentIndex == 0) {
                buttonBack.setVisibility(View.INVISIBLE);
            }
            buttonNext.setVisibility(View.VISIBLE);
            imageView.setImageResource(images[currentIndex]);
        });

        buttonNext.setOnClickListener(view -> {
            ++currentIndex;
            if (currentIndex == images.length - 1) {
                buttonNext.setVisibility(View.INVISIBLE);
            }
            buttonBack.setVisibility(View.VISIBLE);
            imageView.setImageResource(images[currentIndex]);
        });

        if (currentIndex == 0) {
            buttonBack.setVisibility(View.INVISIBLE);
        }

        if (images.length == 1) {
            buttonNext.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.help) {
            AlertDialog.Builder dialog = new
                    AlertDialog.Builder(MainActivity.this);
            try {
                dialog.setMessage(getTitle().toString() + " версия " +
                        getPackageManager().getPackageInfo(getPackageName(), 0).versionName + "\r\n\nTask 21\r\n\n Туракулов Исломбек Улугбекович");

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            dialog.setTitle("О программе");
            dialog.setNeutralButton("OK", (dialog1, which) -> dialog1.dismiss());
            dialog.setIcon(R.mipmap.ic_launcher_round);
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}