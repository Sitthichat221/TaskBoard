package com._6.TaskBoard.Services;

import com._6.TaskBoard.Entity.User;

public interface AuthService {
    User findByUserName(String username);
    User register(User user);
    User login(User user);
}
