package com.learn.testing.service;

import com.learn.testing.model.User;
import com.learn.testing.repo.UserRepo;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    public UserServiceImpl(UserRepo  userDatabase) {
        this.userRepo = userDatabase;
    }

    @Override
    public String createUser(User user) {
        String userId = UUID.randomUUID().toString();
        boolean isUserCreated = false;

        System.out.println("Invoking UserServiceImpl createUser for userId : " + userId);
        isUserCreated = userRepo.saveUser(userId, user);

        if (!isUserCreated) {
            throw new UserServiceException("User already exists and hence not created");
        }

        return userId;
    }

    @Override
    public void updateUser(String userId, User user) {
        if (null == userId || userId.isEmpty()) {
            throw new IllegalArgumentException("userId cannot be null or empty!!!");
        }

        System.out.println("Invoking UserServiceImpl updateUser for userId : " + userId);
        if (null == userRepo.getUser(userId)) {
            throw new IllegalArgumentException("User does not exist for userId : " + userId);
        }
        userRepo.updateUser(userId, user);
    }

    @Override
    public User getUser(String userId) {
        return userRepo.getUser(userId);
    }

    @Override
    public int getAllUsersCount() {
        return userRepo.getAllUsersCount();
    }

    @Override
    public void removeUser(String userId) {
        System.out.println("Invoking UserServiceImpl deleteUser for userId : " + userId);
        userRepo.deleteUser(userId);
    }
}
