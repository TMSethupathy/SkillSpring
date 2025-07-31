package com.skillspring.SkillSpring.sevice;


import com.skillspring.SkillSpring.dto.LoginRequest;
import com.skillspring.SkillSpring.dto.RegisterRequest;
import com.skillspring.SkillSpring.entity.User;
import com.skillspring.SkillSpring.repo.UserRepo;
import com.skillspring.SkillSpring.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        System.out.println("Register request for email: " + request.getEmail());
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("{\"message\": \"Email already exists\"}");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setLanguage(request.getLanguage());
        user.setRole(request.getRole() != null ? request.getRole() : User.Role.STUDENT);

        userRepo.save(user);
        String token = jwtUtil.generateToken(user);
        System.out.println("Registered user: " + request.getEmail() + ", Token: " + token);
        return token;
    }

    public String login(LoginRequest request) {
        System.out.println("Login request for email: " + request.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("{\"message\": \"User not found\"}"));
        String token = jwtUtil.generateToken(user);
        System.out.println("Logged in user: " + request.getEmail() + ", Token: " + token);
        return token;
    }
}