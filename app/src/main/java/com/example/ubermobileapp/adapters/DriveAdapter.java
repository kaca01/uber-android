package com.example.ubermobileapp.adapters;

import android.app.Activity;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.enumeration.MessageType;
import com.example.ubermobileapp.models.pojo.communication.Message;
import com.example.ubermobileapp.models.pojo.communication.MessageList;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.DriverService;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class DriveAdapter extends BaseAdapter {

    private Activity activity;
    private HashMap<Long, List<Message>> messagesMap = new HashMap<>();
//    public String filter = "";
    public String search = "";

    public DriveAdapter(Activity activity, String filter, String search) {
        this.activity = activity;
        getMessagesByRides(filter, search);
    }

    @Override
    public int getCount() {
        return messagesMap.size();
    }

    @Override
    public List<Message> getItem(int position) {
        int i = 0;
        for (Map.Entry<Long, List<Message>> entry : messagesMap.entrySet()) {
            if(i == position){
                return entry.getValue();
            }
            i++;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        List<Message> messages = getItem(position);

        if(convertView==null) {
            view = activity.getLayoutInflater().inflate(R.layout.inbox_drive_list, null);
        }
        if (messages == null || messages.isEmpty()) return null;

        Ride ride = getRideById(messages.get(0).getRideId());

        TextView name = view.findViewById(R.id.name);
        TextView time = view.findViewById(R.id.message_date);
        TextView destination = view.findViewById(R.id.destination);
        TextView date = view.findViewById(R.id.route_date);
        TextView msg = view.findViewById(R.id.message);

        name.setText(getTitle(ride));
        time.setText(messages.get(messages.size()-1).getTimeOfSending().substring(11, 16));
        destination.setText(ride.getLocations().get(0).getDestination().getAddress());
        date.setText(ride.getStartTime().substring(0, 10));
        String message = messages.get(messages.size()-1).getMessage();
        if(message.length() > 100) {
            message = message.substring(0, 100);
            message = message + "...";
        };
        msg.setText(message);
        boolean isPanic = checkIfHasPanic(messages);
        if (isPanic)
            view.setBackgroundResource(R.drawable.panic_item_background);
        else view.setBackgroundResource(R.drawable.drive_item_background);

        return  view;
    }

    private boolean checkIfHasPanic(List<Message> messages) {
        boolean isPanic = false;
        for(Message m: messages){
            if(m.getType().equals(MessageType.PANIC.toString())){
                isPanic = true;
                break;
            }
        }
        return isPanic;
    }

    private Ride getRideById(Long id) {
        return RideService.getRideDetails(id);
    }

    private void getMessagesByRides(String filter, String search){
        List<Message> allMessages = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (AuthService.getCurrentUser() == null) return;
        Call<MessageList> messageResponseCall = ApiUtils.getMessageService().getUserMessages(AuthService.getCurrentUser().getId());
        try {
            Response<MessageList> response = messageResponseCall.execute();
            allMessages = response.body().getResults();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        for (Message m: allMessages){
            String type = "";
            System.out.println("FILTERRRR");
            System.out.println(filter);
            switch (filter) {
                case "panic":
                    type = MessageType.PANIC.toString();
                    break;
                case "ride":
                    type = MessageType.RIDE.toString();
                    break;
                case "none":
                    type = "";
                    break;
            }

            if (m.getType().equals(MessageType.SUPPORT.toString())) continue;

            if (!type.equals("")) {
                if (!m.getType().equals(type)) continue;
            }

            if (!search.equals("")) {
                // TODO : this is not finished
                if (!checkIfMessageContains(search, m)) continue;
            }


            if (messagesMap.containsKey(m.getRideId())) messagesMap.get(m.getRideId()).add(m);
            else {
                messagesMap.put(m.getRideId(), new ArrayList<>());
                messagesMap.get(m.getRideId()).add(m);
            }
        }
    }

    private boolean checkIfMessageContains(String search, Message m) {
        User reciever = null;
        User sender = null;
        User current = AuthService.getCurrentUser();
        if (m.getReceiverId() != current.getId()) {
            reciever = PassengerService.getPassenger(m.getReceiverId());
            if (reciever == null) {
                reciever = DriverService.getDriver(m.getReceiverId());
            }

            if (reciever.getName().contains(search)) return true;
            if (reciever.getSurname().contains(search)) return true;
        }
        else if (m.getSenderId() != current.getId()) {
            sender = DriverService.getDriver(m.getSenderId());
            if (sender == null) sender = PassengerService.getPassenger(m.getSenderId());

            if (sender.getName().contains(search)) return true;
            if (sender.getSurname().contains(search)) return true;
        }

        return false;
    }

    public String getTitle(Ride ride)
    {
        String title;
        if(AuthService.getCurrentUser().getRoles().get(0).getName().equals("ROLE_PASSENGER"))
            title = ride.getDriver().getName() + " " + ride.getDriver().getSurname();
        else {
            User user = ride.getPassengers().get(ride.getPassengers().size()-1);
            Passenger p = PassengerService.getPassenger(user.getId());
            title = p.getName() + " " + p.getSurname();
        }
        return title;
    }
}
