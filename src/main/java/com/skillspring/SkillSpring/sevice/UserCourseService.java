package com.skillspring.SkillSpring.sevice;

import com.skillspring.SkillSpring.dto.UserCourseRequest;
import com.skillspring.SkillSpring.dto.UserCourseResponse;
import com.skillspring.SkillSpring.entity.Course;
import com.skillspring.SkillSpring.entity.User;
import com.skillspring.SkillSpring.entity.UserCourse;
import com.skillspring.SkillSpring.repo.CourseRepo;
import com.skillspring.SkillSpring.repo.UserCourseRepo;
import com.skillspring.SkillSpring.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCourseService {

    @Autowired
    private UserCourseRepo userCourseRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CourseRepo courseRepo;

    public UserCourseResponse createUserCourse(UserCourseRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + request.getUserId() + " not found"));
        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + request.getCourseId() + " not found"));

        if (userCourseRepo.existsByUserIdAndCourseId(request.getUserId(), request.getCourseId())) {
            throw new IllegalArgumentException("User with ID " + request.getUserId() + " is already enrolled in course with ID " + request.getCourseId());
        }

        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        userCourse.setStatus(UserCourse.Status.valueOf(request.getStatus()));
        userCourse.setProgress(request.getProgress());
        userCourse.setEnrolledAt(LocalDateTime.now());
        if (request.getStatus().equals(UserCourse.Status.COMPLETED.name())) {
            userCourse.setCompletedAt(LocalDateTime.now());
        }

        UserCourse savedUserCourse = userCourseRepo.save(userCourse);
        return mapToUserCourseResponse(savedUserCourse);
    }

    @Transactional
    public UserCourseResponse getUserCourseById(Long userCourseId) {
        UserCourse userCourse = userCourseRepo.findById(userCourseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCourse with ID " + userCourseId + " not found"));
        return mapToUserCourseResponse(userCourse);
    }

    @Transactional
    public List<UserCourseResponse> getUserCoursesByUserId(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        return userCourseRepo.findByUserId(userId).stream()
                .map(this::mapToUserCourseResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<UserCourseResponse> getUserCoursesByCourseId(Long courseId) {
        if (!courseRepo.existsById(courseId)) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }
        return userCourseRepo.findByCourseId(courseId).stream()
                .map(this::mapToUserCourseResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserCourseResponse updateUserCourse(Long userCourseId, UserCourseRequest request) {
        UserCourse userCourse = userCourseRepo.findById(userCourseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCourse with ID " + userCourseId + " not found"));

        // Validate user and course if they are being updated
        if (!userCourse.getUser().getId().equals(request.getUserId())) {
            User user = userRepo.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + request.getUserId() + " not found"));
            userCourse.setUser(user);
        }
        if (!userCourse.getCourse().getId().equals(request.getCourseId())) {
            Course course = courseRepo.findById(request.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Course with ID " + request.getCourseId() + " not found"));
            userCourse.setCourse(course);
        }

        // Update fields
        userCourse.setStatus(UserCourse.Status.valueOf(request.getStatus()));
        userCourse.setProgress(request.getProgress());
        if (request.getStatus().equals(UserCourse.Status.COMPLETED.name()) && userCourse.getCompletedAt() == null) {
            userCourse.setCompletedAt(LocalDateTime.now());
        } else if (!request.getStatus().equals(UserCourse.Status.COMPLETED.name())) {
            userCourse.setCompletedAt(null);
        }

        UserCourse updatedUserCourse = userCourseRepo.save(userCourse);
        return mapToUserCourseResponse(updatedUserCourse);
    }

    @Transactional
    public void deleteUserCourse(Long userCourseId) {
        if (!userCourseRepo.existsById(userCourseId)) {
            throw new IllegalArgumentException("UserCourse with ID " + userCourseId + " not found");
        }
        userCourseRepo.deleteById(userCourseId);
    }

    private UserCourseResponse mapToUserCourseResponse(UserCourse userCourse) {
        UserCourseResponse response = new UserCourseResponse();
        response.setId(userCourse.getId());
        response.setUserId(userCourse.getUser().getId());
        response.setCourseId(userCourse.getCourse().getId());
        response.setStatus(userCourse.getStatus().name());
        response.setProgress(userCourse.getProgress());
        response.setEnrolledAt(userCourse.getEnrolledAt());
        response.setCompletedAt(userCourse.getCompletedAt());
        return response;
    }
}