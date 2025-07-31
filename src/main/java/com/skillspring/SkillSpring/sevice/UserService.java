package com.skillspring.SkillSpring.sevice;

import com.skillspring.SkillSpring.dto.StudentResponse;
import com.skillspring.SkillSpring.entity.User;
import com.skillspring.SkillSpring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User createUsers(User user){
        return userRepo.save(user);
    }

    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public List<StudentResponse> getAllStudents() {
        List<User> students = userRepo.findByRole(User.Role.STUDENT);
        return students.stream().map(this::mapToStudentResponse).collect(Collectors.toList());
    }

    private StudentResponse mapToStudentResponse(User user) {
        StudentResponse response = new StudentResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setLanguage(user.getLanguage());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
