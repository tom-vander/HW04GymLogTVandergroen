package com.example.hw04gymlogtvandergroen.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.hw04gymlogtvandergroen.Database.GymLogDatabase;

import java.util.Objects;

@Entity(tableName = GymLogDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private String password;
    private boolean isAdmin;

    public User(String userName, String password) {
        this.password = password;
        this.userName = userName;
        isAdmin = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, isAdmin);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
