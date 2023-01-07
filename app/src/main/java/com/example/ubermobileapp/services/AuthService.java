package com.example.ubermobileapp.services;

import android.os.StrictMode;

import com.example.ubermobileapp.model.User.User;

import retrofit2.Call;
import retrofit2.Response;

public class AuthService {

    private static User currentUser;
    private static String accessToken;
    private static String role;

    public static String getRole() {
        return role;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean tokenIsPresent() {
        return accessToken != null;
    }

    public static String getToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        AuthService.accessToken = accessToken;
    }

    public void getMyInfo(String email){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<String> currUserResponseCall = ApiUtils.getUserService().getCurrentUser(email);
        try {
            Response<String> response = currUserResponseCall.execute();
            role = response.body();
            }
        catch(Exception ex){
            ex.printStackTrace();
            }
        }

    public static void logout() {
        accessToken = null;
        currentUser = null;
    }
}
