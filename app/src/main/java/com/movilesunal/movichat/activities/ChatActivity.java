package com.movilesunal.movichat.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.movilesunal.movichat.R;
import com.movilesunal.movichat.model.Message;

import java.util.LinkedList;

public class ChatActivity extends AppCompatActivity {

    private LinearLayout lytMessages;
    private FirebaseUser user;
    private LinkedList<String> sending = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lytMessages = (LinearLayout) findViewById(R.id.lytMessages);
        final EditText edtMessage = (EditText) findViewById(R.id.edtMessage);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("Room").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!sending.contains(dataSnapshot.getKey())) {
                    addMessageToScreen(dataSnapshot.getValue(Message.class).getText());
                } else {
                    sending.remove(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.fabSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtMessage.getText().toString().isEmpty()) {
                    String text = edtMessage.getText().toString();
                    edtMessage.setText("");
                    addMessageToScreen(text);
                    addMessageToFirebase(text);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMessageToScreen(String text) {
        TextView txtMessage = (TextView) getLayoutInflater().inflate(R.layout.txt_message, lytMessages, false);
        txtMessage.setText(text);

        lytMessages.addView(txtMessage);
    }

    private void addMessageToFirebase(String text) {
        Message message = new Message();
        message.setUser(user.getDisplayName());
        message.setText(text);
        String key = FirebaseDatabase.getInstance().getReference().child("Room").push().getKey();
        sending.add(key);
        FirebaseDatabase.getInstance().getReference().child("Room").child(key).setValue(message);
    }
}
