package com.skillspring.SkillSpring.controller;

import com.skillspring.SkillSpring.dto.UserCourseRequest;
import com.skillspring.SkillSpring.dto.UserCourseResponse;
import com.skillspring.SkillSpring.sevice.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-courses")
@CrossOrigin(origins = "http://localhost:5173")
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<UserCourseResponse> createUserCourse(@RequestBody UserCourseRequest userCourseRequest) {
        UserCourseResponse response = userCourseService.createUserCourse(userCourseRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userCourseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<UserCourseResponse> getUserCourseById(@PathVariable Long userCourseId) {
        UserCourseResponse response = userCourseService.getUserCourseById(userCourseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<List<UserCourseResponse>> getUserCoursesByUserId(@PathVariable Long userId) {
        List<UserCourseResponse> responses = userCourseService.getUserCoursesByUserId(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<List<UserCourseResponse>> getUserCoursesByCourseId(@PathVariable Long courseId) {
        List<UserCourseResponse> responses = userCourseService.getUserCoursesByCourseId(courseId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{userCourseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<UserCourseResponse> updateUserCourse(
            @PathVariable Long userCourseId,
            @RequestBody UserCourseRequest userCourseRequest) {
        UserCourseResponse response = userCourseService.updateUserCourse(userCourseId, userCourseRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userCourseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public ResponseEntity<Void> deleteUserCourse(@PathVariable Long userCourseId) {
        userCourseService.deleteUserCourse(userCourseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}