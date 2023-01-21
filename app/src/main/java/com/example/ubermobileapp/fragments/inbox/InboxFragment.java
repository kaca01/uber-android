package com.example.ubermobileapp.fragments.inbox;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.activities.inbox.ChatActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.DriveAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.MessageType;
import com.example.ubermobileapp.model.pojo.Ride;
import com.example.ubermobileapp.model.pojo.login.User;
import com.example.ubermobileapp.model.pojo.Message;
import com.example.ubermobileapp.model.pojo.MessageList;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Mockup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class InboxFragment extends ListFragment {

    List<Message> supportMessage = new ArrayList<>();
    DriveAdapter adapter;

    public InboxFragment() {}

    public static InboxFragment newInstance() {
        return new InboxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new DriveAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View header = getLayoutInflater().inflate(R.layout.inbox_support_list, null);
        getSupportMessages();
        TextView time = header.findViewById(R.id.support_message_date);
        TextView msg = header.findViewById(R.id.support_message);
        if (supportMessage.isEmpty()){
            time.setText("");
            String message = "Need help? Contact us!";
            msg.setText(message);
            }
        else {
            time.setText(supportMessage.get(supportMessage.size()-1).getTimeOfSending().substring(11, 16));
            String message = supportMessage.get(supportMessage.size()-1).getMessage();
            if(message.length() > 100) {
                message = message.substring(0, 100);
                message = message + "...";
            };
            msg.setText(message);
        }
        ListView listView = getListView();
        listView.addHeaderView(header);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String name;
        List<Message> messagesChat = adapter.getItem(position-1);
        if (position==0) {
            name = MessageType.SUPPORT.toString();
        }
        else {
            name = MessageType.DRIVE.toString();
        }

        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("name", name);
        if(!name.equals(MessageType.SUPPORT.toString()))
            intent.putExtra("rideId", messagesChat.get(0).getRideId());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.search){
            Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.filter){
            Toast.makeText(getActivity(), "Filter", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getSupportMessages(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        User currentUser = AuthService.getCurrentUser();

        Call<MessageList> messageResponseCall = ApiUtils.getMessageService().getSupportMessages(currentUser.getId());
        try {
            Response<MessageList> response = messageResponseCall.execute();
            supportMessage = response.body().getResults();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}