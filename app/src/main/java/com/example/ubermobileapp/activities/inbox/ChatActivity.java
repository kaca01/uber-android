package com.example.ubermobileapp.activities.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.home.PassengerCurrentRideActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.adapters.MessageAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.MessageType;
import com.example.ubermobileapp.model.login.User;
import com.example.ubermobileapp.model.pojo.Message;
import com.example.ubermobileapp.model.pojo.MessageList;
import com.example.ubermobileapp.model.pojo.Ride;
import com.example.ubermobileapp.services.implementation.MessageService;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private List<Message> messages = new ArrayList<>();
    private Ride ride;
    private User sender; //current user is sender
    private User receiver = new User(3L, "pera@gmail.com", "Pera", "Petrovic"); //todo receiver depends from clicks from front
    private String chat_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sender = AuthService.getCurrentUser();
        setTitle();
        Drive drive = new Drive(); //initializing only to avoid warnings (delete this later)

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_chat);
        Serializable from_inbox = getIntent().getSerializableExtra("name");
        if (from_inbox != null) chat_type = from_inbox.toString();
        // ako je iz inboxa usao u chat
        if (chat_type.equals(MessageType.SUPPORT.toString())){

            //messages = Mockup.getSupportMessages();
            }
        else if (chat_type.equals(MessageType.DRIVE.toString()) || chat_type.equals(MessageType.PANIC.toString())){

            drive = (Drive)getIntent().getSerializableExtra("drive"); //dobaviti ride iz intenta
            //messages = drive.getMessages();
        }
        //ukoliko nije iz inboxa usao u chat
        else{
            //dobavlja sve poruke izmedju dva korisnika, nebitno ko je receiver, a ko sender
            setRide();
            messages = FindByRideIdAndOtherUser(ride, receiver);
        }

        MessageAdapter adapter = new MessageAdapter(this, messages);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.scrollToPosition(messages.size() - 1);
        recycler.setAdapter(adapter);

        ImageView back = (ImageView) findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from_inbox != null) {
                    Intent i = new Intent(ChatActivity.this, PassengerInboxActivity.class);
                    startActivity(i);
                }
                else{
                    if(sender.getRoles().get(0).getName().equals("ROLE_PASSENGER"))
                    {
                        Intent i = new Intent(ChatActivity.this, PassengerCurrentRideActivity.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(ChatActivity.this, DriverMainActivity.class);
                        startActivity(i);
                    }
                }
            }
        });

        Button send = (Button) findViewById(R.id.button_chat_send);
        Drive finalDrive = drive;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newChat = (EditText)findViewById(R.id.edit_chat_message);
                String text = newChat.getText().toString();
                if (text.isEmpty()) return;
                if (chat_type.equals(MessageType.SUPPORT.toString())) {
                    /*Message message = Message.generateSupportMessage(text);
                    messages.add(message);*/
                }
                else {
                    Message message = new Message(text, "RIDE", ride.getId());
                    message = MessageService.addMessageToDatabase(receiver.getId(), message);
                    messages.add(message);
                }
                recycler.scrollToPosition(messages.size() - 1);
                adapter.notifyDataSetChanged();
                newChat.setText("");
            }
        });

    }

    private void setTitle()
    {
        TextView action_bar_name = (TextView) findViewById(R.id.action_bar_name);
        String title = receiver.getName() + " " + receiver.getSurname();
        action_bar_name.setText(title);
    }

    public List<Message> FindByRideIdAndOtherUser(Ride ride, User receiver){
        setUserMessages();
        List<Message> filteredMessages = new ArrayList<>();
        for (Message m: messages) {
            if(m.getRideId() == ride.getId() && (m.getSenderId()==receiver.getId() ||
                    m.getReceiverId()==receiver.getId())){
                filteredMessages.add(m);
            }
        }
        return filteredMessages;
    }

    private void setRide(){
        if(sender.getRoles().get(0).getName().equals("ROLE_PASSENGER")) {
            ride = RideService.getPassengerActiveRide(sender.getId());
        }

        else if(sender.getRoles().get(0).getName().equals("ROLE_DRIVER")){
            ride = RideService.getDriverActiveRide(sender.getId());
        }
    }

    public void setUserMessages(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<MessageList> messageResponseCall = ApiUtils.getMessageService().getUserMessages(sender.getId());
        try {
            Response<MessageList> response = messageResponseCall.execute();
            messages = response.body().getResults();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}