package com.linkedinclone.repositories;

import com.linkedinclone.models.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByRecruiterId(String recruiterId);
    List<Job> findByTitleContainingIgnoreCase(String keyword);
}