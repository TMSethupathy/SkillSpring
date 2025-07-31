package com.skillspring.SkillSpring.sevice;

import com.skillspring.SkillSpring.dto.CourseRequest;
import com.skillspring.SkillSpring.dto.CourseResponse;
import com.skillspring.SkillSpring.entity.Course;
import com.skillspring.SkillSpring.entity.User;
import com.skillspring.SkillSpring.repo.CourseRepo;
import com.skillspring.SkillSpring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CourseRepo courseRepo;

    public CourseResponse createCourse(CourseRequest courseRequest) {
        if (courseRepo.existsBySlug(courseRequest.getSlug())) {
            throw new IllegalArgumentException("Course with slug " + courseRequest.getSlug() + " already exists");
        }

        Course course = new Course();
        updateCourseFromRequest(course, courseRequest);
        Course savedCourse = courseRepo.save(course);
        return mapToCourseResponse(savedCourse);
    }

    private void updateCourseFromRequest(Course course, CourseRequest courseRequest) {
        course.setTitle(courseRequest.getTitle());
        course.setSlug(courseRequest.getSlug());
        course.setDescription(courseRequest.getDescription());
        course.setLanguage(courseRequest.getLanguage());
        course.setLevel(Course.Level.valueOf(courseRequest.getLevel()));
        course.setPrice(courseRequest.getPrice());
        course.setFree(courseRequest.isFree());
        course.setThumbnailUrl(courseRequest.getThumbnailUrl());
        course.setAverageRating(courseRequest.getAverageRating());
        course.setTotalContentHours(courseRequest.getTotalContentHours());
        course.setTotalLearners(courseRequest.getTotalLearners());

        if (courseRequest.getCreatedById() != null) {
            User createdBy = userRepo.findById(courseRequest.getCreatedById())
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + courseRequest.getCreatedById() + " not found"));
            if (!createdBy.getRole().equals(User.Role.MENTOR)) {
                throw new IllegalArgumentException("User with ID " + courseRequest.getCreatedById() + " is not a mentor");
            }
            course.setCreatedBy(createdBy);
        }
    }

    private CourseResponse mapToCourseResponse(Course course) {

        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setSlug(course.getSlug());
        response.setDescription(course.getDescription());
        response.setLanguage(course.getLanguage());
        response.setLevel(course.getLevel().name());
        response.setPrice(course.getPrice());
        response.setFree(course.isFree());
        response.setThumbnailUrl(course.getThumbnailUrl());
        response.setAverageRating(course.getAverageRating());
        response.setTotalContentHours(course.getTotalContentHours());
        response.setTotalLearners(course.getTotalLearners());
        response.setMentorDetails(course.getCreatedBy() != null ? mapToMentorDetails(course.getCreatedBy()) : null);
        return response;
    }

    private CourseResponse.MentorDetails mapToMentorDetails(User mentor) {
        CourseResponse.MentorDetails mentorDetails = new CourseResponse.MentorDetails();
        mentorDetails.setId(mentor.getId());
        mentorDetails.setName(mentor.getName());
        mentorDetails.setEmail(mentor.getEmail());
        mentorDetails.setPhone(mentor.getPhone());
        mentorDetails.setLanguage(mentor.getLanguage());
        mentorDetails.setRole(mentor.getRole().name());
        return mentorDetails;
    }

    public CourseResponse getCourseById(Long courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));
        return mapToCourseResponse(course);
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepo.findAll().stream()
                .map(this::mapToCourseResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse updateCourse(Long courseId, CourseRequest courseRequest) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

        if (!course.getSlug().equals(courseRequest.getSlug()) &&
                courseRepo.existsBySlug(courseRequest.getSlug())) {
            throw new IllegalArgumentException("Course with slug " + courseRequest.getSlug() + " already exists");
        }

        updateCourseFromRequest(course, courseRequest);
        Course updatedCourse = courseRepo.save(course);
        return mapToCourseResponse(updatedCourse);
    }
    public void deleteCourse(Long courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));
        courseRepo.delete(course);
    }
}