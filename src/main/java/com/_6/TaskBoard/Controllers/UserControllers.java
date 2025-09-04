package com._6.TaskBoard.Controllers;

import com._6.TaskBoard.Entity.User;
import com._6.TaskBoard.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserControllers {

    private UserServices userServices;

    @Autowired
    public UserControllers(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        user.setId(0);
        return userServices.save(user);

    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userServices.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id){
        User myUser = userServices.findById(id);
        if(myUser == null){
            throw new RuntimeException("ไม่พบข้อมูล"+id);
        }
        return myUser;

    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id){
        User myUser = userServices.findById(id);
        if(myUser==null){
            throw new RuntimeException("ไม่พบข้อผู้ใช้รหัส"+id);
        }
        userServices.deleteById(id);
        return "ลบข้อมูลผู้ใช้"+id+"เรียบร้อย";
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        return userServices.save(user);

    }

}
