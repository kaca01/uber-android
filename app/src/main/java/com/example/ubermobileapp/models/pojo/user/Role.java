<<<<<<<< HEAD:app/src/main/java/com/example/ubermobileapp/models/login/Role.java
package com.example.ubermobileapp.models.login;
========
package com.example.ubermobileapp.model.pojo.user;
>>>>>>>> feature-inbox-binding:app/src/main/java/com/example/ubermobileapp/model/pojo/user/Role.java


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("authority")
    @Expose
    private String authority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
