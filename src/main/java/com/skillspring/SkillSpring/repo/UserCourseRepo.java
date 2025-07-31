package com.skillspring.SkillSpring.repo;

import com.skillspring.SkillSpring.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepo extends JpaRepository<UserCourse ,Long> {

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    List<UserCourse> findByUserId(Long userId);
    List<UserCourse> findByCourseId(Long courseId);
}
