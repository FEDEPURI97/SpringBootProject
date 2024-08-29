package com.federicopuricella.jobportal.repository;

import com.federicopuricella.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {
}
