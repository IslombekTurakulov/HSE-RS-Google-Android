package com.example.task_22;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PhoneAdapter phoneAdapter;
    private List<LinkData> dataList;

    EditText person_name, phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_phones);
        dataList = new ArrayList<>();
        phoneAdapter = new PhoneAdapter(this, dataList);
        recyclerView.setAdapter(phoneAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button add = findViewById(R.id.btnEdit);
        person_name = findViewById(R.id.editTextTextPersonName);
        phone_number = findViewById(R.id.editTextPhone);

        add.setOnClickListener(view -> {
            if (person_name.getText().toString().length() != 0 && phone_number.getText().toString().length() != 0) {
                AddUserTask task = new AddUserTask();
                LinkData data = new LinkData();
                data.setName(person_name.getText().toString().substring(0, 5) + "...");
                data.setPhone(phone_number.getText().toString());
                task.execute(data);
            } else {
                Toast.makeText(this, "Incorrect data!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class AddUserTask extends AsyncTask<LinkData, LinkData, List<LinkData>> {

        @Override
        protected List<LinkData> doInBackground(LinkData... data) {
            List<LinkData> dataList = new ArrayList<>();
            dataList.add(data[0]);
            return dataList;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(List<LinkData> linkData) {
            dataList.addAll(linkData);
            phoneAdapter.notifyDataSetChanged();
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
                        getPackageManager().getPackageInfo(getPackageName(), 0).versionName + "\r\n\nЗаписная книжка\r\n\n Автор - " + getString(R.string.fio));

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