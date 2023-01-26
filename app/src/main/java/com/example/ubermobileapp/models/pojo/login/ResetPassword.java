package com.example.ubermobileapp.models.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPassword {

    @SerializedName("newPassword")
    @Expose
    private String newPassword;

    @SerializedName("code")
    @Expose
    private String code;

    public ResetPassword() { }

    public ResetPassword(String newPassword, String code) {
        this.newPassword = newPassword;
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "newPassword='" + newPassword + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
