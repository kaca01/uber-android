package com.example.ubermobileapp.activities.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.home.PassengerCurrentRideActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.adapters.MessageAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.MessageType;
import com.example.ubermobileapp.model.pojo.login.Passenger;
import com.example.ubermobileapp.model.pojo.login.User;
import com.example.ubermobileapp.model.pojo.Message;
import com.example.ubermobileapp.model.pojo.MessageList;
import com.example.ubermobileapp.model.pojo.Ride;
import com.example.ubermobileapp.services.implementation.MessageService;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private List<Message> messages = new ArrayList<>();
    private Ride ride;
    private User sender; //current user is sender
    private User receiver = null;
    private String chat_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sender = AuthService.getCurrentUser();
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_chat);

        Serializable from_inbox = getIntent().getSerializableExtra("name");
        // ako je iz inboxa usao u chat
        if (from_inbox != null) chat_type = from_inbox.toString();
        if (chat_type.equals(MessageType.SUPPORT.toString())) {
            getSupportMessages();
        }
        else if (chat_type.equals(MessageType.RIDE.toString())){
            Long rideId = (Long)getIntent().getSerializableExtra("rideId");
            ride = RideService.getRideDetails(rideId);
            setReceiver();
            messages = FindByRideIdAndOtherUser(ride, receiver);
        }
        //ukoliko nije iz inboxa usao u chat
        else{
            setRide();
            setReceiver();
            messages = FindByRideIdAndOtherUser(ride, receiver);
        }

        setTitle();
        MessageAdapter adapter = new MessageAdapter(this, messages);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.scrollToPosition(messages.size() - 1);
        recycler.setAdapter(adapter);

        ImageView back = (ImageView) findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from_inbox != null) {
                    if(sender.getRoles().get(0).getName().equals("ROLE_PASSENGER"))
                    {
                        Intent i = new Intent(ChatActivity.this, PassengerInboxActivity.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(ChatActivity.this, DriverInboxActivity.class);
                        startActivity(i);
                    }
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
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newChat = (EditText)findViewById(R.id.edit_chat_message);
                String text = newChat.getText().toString().trim();
                if (text.isEmpty()) return;
                if (chat_type.equals(MessageType.SUPPORT.toString())) {
                    Message message = new Message(text, "SUPPORT", 0L);
                    message = MessageService.addMessageToDatabase(sender.getId(), message);
                    messages.add(message);
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

    private void setReceiver(){
        if(sender.getRoles().get(0).getName().equals("ROLE_PASSENGER")){
            receiver = ride.getDriver();
        }else{
            User user = ride.getPassengers().get(ride.getPassengers().size()-1);
            Passenger p = PassengerService.getPassenger(null, user.getId(), null);
            receiver = (User) p;
        }
    }

    private void setTitle()
    {
        TextView action_bar_name = findViewById(R.id.action_bar_name);
        String title;
        if (chat_type.equals(MessageType.SUPPORT.toString()))
            title = chat_type;
        else
            title = receiver.getName() + " " + receiver.getSurname();
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

    //dobavlja sve poruke izmedju dva korisnika, nebitno ko je receiver, a ko sender
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

    public void getSupportMessages(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        User currentUser = AuthService.getCurrentUser();

        Call<MessageList> messageResponseCall = ApiUtils.getMessageService().getSupportMessages(currentUser.getId());
        try {
            Response<MessageList> response = messageResponseCall.execute();
            messages = response.body().getResults();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        Serializable from_inbox = getIntent().getSerializableExtra("name");
        if (from_inbox != null) {
            if(sender.getRoles().get(0).getName().equals("ROLE_PASSENGER"))
            {
                Intent i = new Intent(ChatActivity.this, PassengerInboxActivity.class);
                startActivity(i);
            }
            else {
                Intent i = new Intent(ChatActivity.this, DriverInboxActivity.class);
                startActivity(i);
            }
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
}