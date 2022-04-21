package com.example.medis.AdminMode;

import com.example.medis.Entities.User;

public class UserInfo {

    private User loggedInUser;
    private User selectedUser;


    public void initData(User user, User user2) {
        loggedInUser = user;
        selectedUser = user2;
    }
}
