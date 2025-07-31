package com.skillspring.SkillSpring.controller;

import com.skillspring.SkillSpring.dto.StudentResponse;
import com.skillspring.SkillSpring.entity.User;
import com.skillspring.SkillSpring.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public ResponseEntity<List<User>>  getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public  ResponseEntity<Optional<User>> getUserById(@PathVariable Long id){
        Optional<User> userById = userService.getUserById(id);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }

    @GetMapping("/students")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = userService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
