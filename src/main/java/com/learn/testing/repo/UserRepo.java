package com.learn.testing.repo;


import com.learn.testing.model.User;

public interface UserRepo {

    public void init();

    public void close();

    boolean saveUser(String userId, User user);

    void updateUser(String userId, User user);

    User getUser(String userId);

    int getAllUsersCount();

    void deleteUser(String userId);
}