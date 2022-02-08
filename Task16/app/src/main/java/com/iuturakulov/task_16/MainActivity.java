package com.iuturakulov.task_16;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView groups;
    ArrayList<String> listOfDiscussion = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String nameOfUser;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groups = (ListView) findViewById(R.id.groupsList);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listOfDiscussion);
        groups.setAdapter(adapter);
        getUserName();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    set.add(snapshot.getKey());
                }
                adapter.clear();
                adapter.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        groups.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), DiscussionActivity.class);
            intent.putExtra("selected_topic", ((TextView) view).getText().toString());
            intent.putExtra("user_name", nameOfUser);
            startActivity(intent);
        });
    }

    private void getUserName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText username = new EditText(this);
        builder.setView(username);
        builder.setPositiveButton("OK", (dialog, which) -> nameOfUser = username.getText().toString());
        builder.setNegativeButton("Cancel", (dialog, which) -> getUserName());
        builder.show();
    }
}