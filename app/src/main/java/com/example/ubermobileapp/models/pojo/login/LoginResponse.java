<<<<<<<< HEAD:app/src/main/java/com/example/ubermobileapp/models/login/LoginResponse.java
package com.example.ubermobileapp.models.login;
========
package com.example.ubermobileapp.model.pojo.login;
>>>>>>>> feature-inbox-binding:app/src/main/java/com/example/ubermobileapp/model/pojo/login/LoginResponse.java

public class LoginResponse {
    private String accessToken;
    private double expiresIn;

    public LoginResponse(String accessToken, double expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public double getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(double expiresIn) {
        this.expiresIn = expiresIn;
    }
}
