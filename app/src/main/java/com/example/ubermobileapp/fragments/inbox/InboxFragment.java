package com.example.ubermobileapp.fragments.inbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.activities.inbox.ChatActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.DriveAdapter;
import com.example.ubermobileapp.models.enumeration.MessageType;
import com.example.ubermobileapp.models.pojo.communication.Message;
import com.example.ubermobileapp.models.pojo.communication.MessageList;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class InboxFragment extends ListFragment {

    List<Message> supportMessage = new ArrayList<>();
    DriveAdapter adapter;
    private String filterBy = "";
    private String search = "";

    public InboxFragment() {}

    public static InboxFragment newInstance() {
        return new InboxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        adapter = new DriveAdapter(getActivity());
//        setListAdapter(adapter);
        run();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        setOnClickListeners(view);
        return view;
    }

    private void setOnClickListeners(View view) {
        ActionMenuItemView filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFilterDialog();
//                adapter.filter = "panic";
//                filterBy = "panic";
            }
        });

        ActionMenuItemView search = view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSearchDialog();
            }
        });
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
            name = MessageType.RIDE.toString();
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

        System.out.println("iddddddd");
        System.out.println(id);

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

    public void run() {
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                // TODO : search
                // TODO : bind search to input in fragment
                adapter = new DriveAdapter(getActivity(), filterBy, search);
//                adapter.filter = filterBy;
                setListAdapter(adapter);

                handler.postDelayed(this, 3000);
            }
        });
    }

    void createFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filter by")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setItems(R.array.filters_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (which == 0) filterBy = "none";
                        else if(which == 1) filterBy = "ride";
                        else if (which == 2) filterBy = "panic";
                    }
                });
        builder.create();
        builder.show();
    }

    void createSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_inbox_search, null);
        builder.setTitle("Search by name")
                .setView(newView)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText s = newView.findViewById(R.id.search);
                        search = s.getText().toString();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
}