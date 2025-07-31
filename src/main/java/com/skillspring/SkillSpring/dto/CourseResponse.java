package com.skillspring.SkillSpring.dto;

import lombok.Data;

@Data
public class CourseResponse {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String language;
    private String level;
    private double price;
    private boolean isFree;
    private String thumbnailUrl;
    private double averageRating;
    private double totalContentHours;
    private long totalLearners;
    private MentorDetails mentorDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getTotalContentHours() {
        return totalContentHours;
    }

    public void setTotalContentHours(double totalContentHours) {
        this.totalContentHours = totalContentHours;
    }

    public long getTotalLearners() {
        return totalLearners;
    }

    public void setTotalLearners(long totalLearners) {
        this.totalLearners = totalLearners;
    }

    public MentorDetails getMentorDetails() {
        return mentorDetails;
    }

    public void setMentorDetails(MentorDetails mentorDetails) {
        this.mentorDetails = mentorDetails;
    }

    @Data
    public static class MentorDetails {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String language;
        private String role;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}