package com.example.ubermobileapp.services.implementation;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.ubermobileapp.model.pojo.Message;
import com.example.ubermobileapp.services.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageService {

    public static Message addMessageToDatabase(Context context, Message message, String toastText){
        final Message[] returnMessage = new Message[1];
        Call<Message> rideResponseCall = ApiUtils.getMessageService().sendMessage(message);
        rideResponseCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnMessage[0] = response.body();
                        }
                    }, 700);

                } else
                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(context, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return returnMessage[0];
    }
}
