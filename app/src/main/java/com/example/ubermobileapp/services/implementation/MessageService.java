package com.example.ubermobileapp.services.implementation;

import android.os.StrictMode;

import com.example.ubermobileapp.model.pojo.communication.Message;
import com.example.ubermobileapp.services.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Response;

public class MessageService {

    public static Message addMessageToDatabase(Long id, Message message){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<Message> rideResponseCall = ApiUtils.getMessageService().sendMessage(id, message);
        try {
            Response<Message> response = rideResponseCall.execute();
            message = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return message;
    }
}
