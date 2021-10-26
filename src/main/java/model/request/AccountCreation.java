package model.request;

import java.util.Map;

public class AccountCreation {
    private String email;
    private String id;
    private String password;
    private Map<Object,Object> profile;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.util.Map<Object, Object> getProfile() {
        return profile;
    }

    public void setProfile(java.util.Map<Object, Object> profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
}
