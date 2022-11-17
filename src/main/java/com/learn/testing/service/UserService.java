package com.learn.testing.service;

import com.learn.testing.model.User;

public interface UserService {
    public String createUser(User user);

    public void updateUser(String userId, User user);

    public User getUser(String userId);

    public int getAllUsersCount();

    public void removeUser(String userId);
}
