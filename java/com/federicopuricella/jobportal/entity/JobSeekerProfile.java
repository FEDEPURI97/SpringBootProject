package com.federicopuricella.jobportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {
    @Id
    private Integer userAccountId;
    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    private String firstName;

    private String lastName;

    private String city;

    private String country;

    private String state;

    private String resume;

    private String workAuthorization;

    private String employmentType;
    @Column(nullable = true,length = 64)
    private String profilePhoto;
    @OneToMany(targetEntity = Skills.class,cascade = CascadeType.ALL,
            mappedBy = "jobSeekerProfile")
    private List<Skills> skills;

    public JobSeekerProfile() {
    }

    public JobSeekerProfile(Users userId) {
        this.userId = userId;
    }

    public JobSeekerProfile(Integer userAccountId, List<Skills> skills, String profilePhoto, String employmentType, String workAuthorization, String resume, String state, String country, String city, String lastName, String firstName, Users userId) {
        this.userAccountId = userAccountId;
        this.skills = skills;
        this.profilePhoto = profilePhoto;
        this.employmentType = employmentType;
        this.workAuthorization = workAuthorization;
        this.resume = resume;
        this.state = state;
        this.country = country;
        this.city = city;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userId = userId;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }
    @Transient
    public String getPhotosImagePath(){
        if(profilePhoto==null||userAccountId==null)return null;
        return"/photos/candidate/"+userAccountId+"/"+profilePhoto;

    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", resume='" + resume + '\'' +
                ", workAuthorization='" + workAuthorization + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
