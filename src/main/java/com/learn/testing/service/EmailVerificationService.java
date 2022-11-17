package com.learn.testing.service;

import com.learn.testing.model.User;

public interface EmailVerificationService {

    public void scheduleEmailConfirmation(User user);
}
