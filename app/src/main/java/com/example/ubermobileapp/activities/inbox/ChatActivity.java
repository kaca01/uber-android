package com.example.ubermobileapp.activities.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.MessageAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.Message;
import com.example.ubermobileapp.model.MessageType;
import com.example.ubermobileapp.tools.Mockup;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Drive drive = new Drive(); //initializing only to avoid warnings
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_chat);
        String name = getIntent().getSerializableExtra("name").toString();
        if (name.equals(MessageType.SUPPORT.toString()))
            messages = Mockup.getSupportMessages();
        else {
            drive = (Drive)getIntent().getSerializableExtra("drive");
            messages = drive.getMessages();
        }
        MessageAdapter adapter = new MessageAdapter(this, messages);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.scrollToPosition(messages.size() - 1);
        recycler.setAdapter(adapter);

        ImageView back = (ImageView) findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatActivity.this, PassengerInboxActivity.class);
                startActivity(i);
            }
        });

        TextView action_bar_name = (TextView) findViewById(R.id.action_bar_name);
        action_bar_name.setText(name);

        Button send = (Button) findViewById(R.id.button_chat_send);
        Drive finalDrive = drive;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newChat = (EditText)findViewById(R.id.edit_chat_message);
                String text = newChat.getText().toString();
                if (text.isEmpty()) return;
                if (name.equals(MessageType.SUPPORT.toString())) {
                    Message message = Message.generateSupportMessage(text);
                    messages.add(message);
                }
                else {
                    Message message = Message.generateDriveMessage(text, finalDrive);
                    messages.add(message);
                }
                recycler.scrollToPosition(messages.size() - 1);
                adapter.notifyDataSetChanged();
                newChat.setText("");
            }
        });

    }

}