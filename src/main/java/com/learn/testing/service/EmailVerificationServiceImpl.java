package com.learn.testing.service;

import com.learn.testing.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Override
    public void scheduleEmailConfirmation(User user) {
        // Put user details into email queue
        System.out.println("scheduleEmailConfirmation is called");
    }
}
