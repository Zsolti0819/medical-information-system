package com.example.medis.AdminMode;

import com.example.medis.Entities.User;

public class UserInfo {

    private User loggedInUser;

    public void initData(User user) {
        loggedInUser = user;
    }
}
