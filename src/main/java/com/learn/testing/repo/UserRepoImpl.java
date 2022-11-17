package com.learn.testing.repo;

import com.learn.testing.model.User;

import java.util.HashMap;

public class UserRepoImpl implements UserRepo {

    HashMap<String, User> userDataMap;

    @Override
    public void init() {
        userDataMap = new HashMap<String, User>();
    }

    @Override
    public void close() {
        userDataMap.clear();
        userDataMap = null;
    }

    @Override
    public boolean saveUser(String userId, User user) {
        boolean isUserExists = true;

        if (!userDataMap.containsKey(userId)) {
            System.out.println("Invoking UserRepo save for userId : " + userId);
            userDataMap.put(userId, user);
            isUserExists = false;
        }
        return isUserExists;
    }

    @Override
    public void updateUser(String userId, User user) {
        System.out.println("Invoking UserRepo update for userId : " + userId);
        userDataMap.put(userId, user);
    }

    @Override
    public User getUser(String userId) {
        return userDataMap.get(userId);
    }

    @Override
    public int getAllUsersCount() {
        return userDataMap.size();
    }

    @Override
    public void deleteUser(String userId) {
        System.out.println("Invoking UserRepo delete for userId : " + userId);
        userDataMap.remove(userId);
    }
}
