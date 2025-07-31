package com.skillspring.SkillSpring.repo;

import com.skillspring.SkillSpring.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course,Long> {
    boolean existsBySlug(String slug);
}
