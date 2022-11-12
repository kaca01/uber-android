package com.example.ubermobileapp.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.tools.Mockup;

public class MessageAdapter extends BaseAdapter {
    private Activity activity;

    public MessageAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Mockup.getMessages().size();
    }

    @Override
    public Object getItem(int position) {
        return Mockup.getMessages().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
