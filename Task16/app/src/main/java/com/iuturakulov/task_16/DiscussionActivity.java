package com.iuturakulov.task_16;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DiscussionActivity extends AppCompatActivity {

    Button btnSendMsg;
    EditText editText;
    ListView groups;
    ArrayList<String> groupMessages = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String userName, selectedTopic, userMsgKey;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        btnSendMsg = (Button) findViewById(R.id.sendMessage);
        editText = (EditText) findViewById(R.id.messageInput);
        groups = (ListView) findViewById(R.id.chatGroup);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groupMessages);
        groups.setAdapter(adapter);
        userName = getIntent().getExtras().get("user_name").toString();
        selectedTopic = getIntent().getExtras().get("selected_topic").toString();
        setTitle("Topic : " + selectedTopic);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(selectedTopic);
        btnSendMsg.setOnClickListener(view -> {
            Map<String, Object> map = new HashMap<String, Object>();
            userMsgKey = databaseReference.push().getKey();
            databaseReference.updateChildren(map);
            DatabaseReference tempDatabase = databaseReference.child(userMsgKey);
            Map<String, Object> tempMap = new HashMap<String, Object>();
            tempMap.put("msg", editText.getText().toString());
            tempMap.put("user", userName);
            tempDatabase.updateChildren(tempMap);
            editText.setText("");
        });


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void updateConversation(DataSnapshot dataSnapshot) {
        String msg, user, conversation;
        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
        while (iterator.hasNext()) {
            msg = (String) (iterator.next()).getValue();
            user = (String) (iterator.next()).getValue();
            conversation = user + ": " + msg;
            adapter.insert(conversation, adapter.getCount());
            adapter.notifyDataSetChanged();
        }
    }
}