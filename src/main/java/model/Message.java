package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

public class Message implements Serializable {
    private String username;
    private String password;
    private String email;
    private String type;
    private String text;

    private Vector<String> users;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Vector<String> getUsers() {
        return users;
    }

    public void setUsers(Vector<String> users) {
        this.users = users;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", users=" + users +
                '}';
    }
}
