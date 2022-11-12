package com.example.ubermobileapp.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.Message;
import com.example.ubermobileapp.tools.Mockup;

public class DriveAdapter extends BaseAdapter {

    private Activity activity;

    public DriveAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Mockup.getDrives().size();
    }

    @Override
    public Object getItem(int position) {
        return Mockup.getDrives().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // preko Drive se dobavljaju panic i drive chatovi
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Drive drive = Mockup.getDrives().get(position);

        /*Message panic = drive.getPanicMessage();
        if (panic != null && getPanic) {
            view = activity.getLayoutInflater().inflate(R.layout.inbox_panic_list, null);
        }*/

        if(convertView==null)
            view = activity.getLayoutInflater().inflate(R.layout.inbox_drive_list, null);

        if (drive.getMessages().isEmpty() || drive.getMessages() == null) return null;

        TextView name = (TextView)view.findViewById(R.id.name);
        TextView time = (TextView)view.findViewById(R.id.message_date);
        TextView destination = (TextView)view.findViewById(R.id.destination);
        TextView date = (TextView)view.findViewById(R.id.route_date);
        TextView msg = (TextView)view.findViewById(R.id.message);

        name.setText(drive.getDriverName());
        time.setText(drive.getMessages().get(drive.getMessages().size()-1).getTime());
        destination.setText(drive.getDestination());
        date.setText(drive.getStartDate());
        String message = drive.getMessages().get(drive.getMessages().size()-1).getText();
        if(message.length() > 100) {
            message = message.substring(0, 100);
            message = message + "...";
        };
        msg.setText(message);

        return  view;
    }
}
