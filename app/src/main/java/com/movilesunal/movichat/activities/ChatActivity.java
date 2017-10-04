package com.movilesunal.movichat.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movilesunal.movichat.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final LinearLayout lytMessages = (LinearLayout) findViewById(R.id.lytMessages);
        final EditText edtMessage = (EditText) findViewById(R.id.edtMessage);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        findViewById(R.id.fabSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtMessage.getText().toString().isEmpty()) {
                    String message = edtMessage.getText().toString();
                    edtMessage.setText("");

                    TextView txtMessage = (TextView) getLayoutInflater().inflate(R.layout.txt_message, lytMessages, false);
                    txtMessage.setText(message);

                    lytMessages.addView(txtMessage);
                }
            }
        });
    }
}
