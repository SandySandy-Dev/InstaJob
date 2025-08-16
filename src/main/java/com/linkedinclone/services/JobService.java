package com.linkedinclone.services;

import com.linkedinclone.models.Job;
import com.linkedinclone.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    
    public Job postJob(Job job) {
        return jobRepository.save(job);
    }
    
    public List<Job> getJobsByRecruiter(String recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId);
    }
    
    public Job getJobById(String id) {
        return jobRepository.findById(id).orElse(null);
    }
    
    public void deleteJob(String id) {
        jobRepository.deleteById(id);
    }
    
    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }
}