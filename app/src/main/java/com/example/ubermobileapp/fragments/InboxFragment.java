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
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.DriveAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.tools.Mockup;

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Drive drive = Mockup.getDrives().get(position);

        /*Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("name", drive.getName());
        intent.putExtra("descr", drive.getDescription());
        startActivity(intent);*/
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