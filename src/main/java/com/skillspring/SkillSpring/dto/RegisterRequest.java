package com.skillspring.SkillSpring.dto;

import com.skillspring.SkillSpring.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;

    private String email;

    private String password;

    private String phone;

    private String language;

    @Enumerated(EnumType.STRING)
    private User.Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}