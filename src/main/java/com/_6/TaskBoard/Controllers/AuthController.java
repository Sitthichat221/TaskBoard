package com._6.TaskBoard.Controllers;

import com._6.TaskBoard.DTO.RegisterRequest;
import com._6.TaskBoard.Entity.User;
import com._6.TaskBoard.Services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (authService.findByUserName(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username ถูกใช้ไปแล้ว");
        }
//        String role = request.getRole().toUpperCase();
//
//        if (!role.equals("USER")) {
//            return ResponseEntity.badRequest().body("Role ไม่ถูกต้อง ต้องเป็น USER");
//        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
//        newUser.setRole(request.getRole());

        authService.register(newUser);

        return ResponseEntity.ok("Register สำเร็จ");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody User loginRequest) {
        try {
            User loggedInUser = authService.login(loginRequest);

            return ResponseEntity.ok("login สำเร็จ");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
