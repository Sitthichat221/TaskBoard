package com._6.TaskBoard.Services;

import com._6.TaskBoard.Entity.User;
import com._6.TaskBoard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthManager(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    @Override
    public User register(User user) {
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


    @Override
    public User login(User loginRequest) {
        User user = findByUserName(loginRequest.getUsername());

        if (user == null) {
            throw new RuntimeException("ไม่พบผู้ใช้งานนี้");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("รหัสผ่านไม่ถูกต้อง");
        }

        return user;
    }


}
