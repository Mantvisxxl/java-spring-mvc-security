package com.mantvydas.demosecurity.service;

import com.mantvydas.demosecurity.entity.User;

public interface UserService {

    User getUserByUsername(String username);
    void updateUserPassword(String username, String newPassword);

    boolean isPasswordCorrect(String username, String currentPassword);
}
