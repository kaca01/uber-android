package com.example.ubermobileapp.PassengerActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.MessageAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.tools.Mockup;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Drive drive = (Drive) getIntent().getSerializableExtra("drive");
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_chat);
        MessageAdapter adapter = new MessageAdapter(this, drive.getMessages());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.back_arrow){
            startActivity(new Intent(ChatActivity.this, PassengerInboxActivity.class));
            //overridePendingTransition(0,0);
        }
        return super.onOptionsItemSelected(item);
    }
}