package com.example.ubermobileapp.services;
import com.example.ubermobileapp.model.pojo.communication.Message;
import com.example.ubermobileapp.model.pojo.communication.MessageList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMessageService {

    //get all messages from that{id} user
    @Headers("Content-Type: application/json")
    @GET("user/{id}/message")
    Call<MessageList> getUserMessages(@Path("id") Long id);

    //send a message to the user, sender is taken from token
    @Headers({"Content-Type:application/json"})
    @POST("user/{id}/message")
    Call<Message> sendMessage(@Path("id") Long id, @Body Message message);

}
