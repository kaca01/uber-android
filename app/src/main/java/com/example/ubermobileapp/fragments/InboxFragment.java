package com.example.ubermobileapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.PassengerActivities.ChatActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.DriveAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.Message;
import com.example.ubermobileapp.model.MessageType;
import com.example.ubermobileapp.tools.Mockup;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class InboxFragment extends ListFragment {

    public InboxFragment() {}

    public static InboxFragment newInstance() {
        return new InboxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DriveAdapter adapter = new DriveAdapter(getActivity());
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
        ArrayList<Message> msgs = Mockup.getSupportMessages();
        TextView time = header.findViewById(R.id.support_message_date);
        TextView msg = header.findViewById(R.id.support_message);
        if (msgs.isEmpty()){
            time.setText("");
            String message = "Need help? Contact us!";
            msg.setText(message);
            }
        else {
            time.setText(msgs.get(msgs.size()-1).getTime());
            String message = msgs.get(msgs.size()-1).getText();
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

        //TODO item position in listview is not the same as in Mockup because of the header?
        Drive drive = Mockup.getDrives().get(position);
        String name;
        if (position==0) name = MessageType.SUPPORT.toString();
        else name = drive.getDriverName();
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("drive", drive);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.search){
            Toast.makeText(getActivity(), "Serach", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.filter){
            Toast.makeText(getActivity(), "Filter", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}