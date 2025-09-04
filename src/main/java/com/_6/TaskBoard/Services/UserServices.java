package com._6.TaskBoard.Services;

import com._6.TaskBoard.Entity.User;

import java.util.List;

public interface UserServices {
    User save(User user);
    List<User> findAll();
    User findById(Integer id);
    void deleteById(Integer id);

}
