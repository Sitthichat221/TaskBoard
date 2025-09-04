package com._6.TaskBoard.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,}$", message = "Username ต้องเป็นตัวอักษรหรือตัวเลขอย่างน้อย 6 ตัว และห้ามมีช่องว่าง")
    @NotNull
    private String username;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,}$", message = "Password ต้องเป็นตัวอักษรหรือตัวเลขอย่างน้อย 6 ตัว และห้ามมีช่องว่าง")
    @NotNull
    private String password;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
