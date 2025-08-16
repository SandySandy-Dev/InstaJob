package com.linkedinclone.controllers;

import com.linkedinclone.models.Application;
import com.linkedinclone.models.Job;
import com.linkedinclone.models.User;
import com.linkedinclone.services.JobService;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {
    @Autowired
    private JobService jobService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserType().equals("recruiter")) {
            return "redirect:/login";
        }
        model.addAttribute("jobs", jobService.getJobsByRecruiter(user.getId()));
        return "recruiter/dashboard";
    }

    @GetMapping("/post-job")
    public String postJobPage() {
        return "recruiter/post-job";
    }

    @PostMapping("/post-job")
    public String postJob(Job job, HttpSession session) {
        User user = (User) session.getAttribute("user");
        job.setRecruiterId(user.getId());
        jobService.postJob(job);
        return "redirect:/recruiter/dashboard";
    }

    @GetMapping("/edit-job/{id}")
    public String editJobPage(@PathVariable String id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "recruiter/edit-job";
    }

    @PostMapping("/edit-job/{id}")
    public String editJob(@PathVariable String id, Job job, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // Get the existing job first
        Job existingJob = jobService.getJobById(id);
        if (existingJob == null) {
            // Handle case where job doesn't exist
            return "redirect:/recruiter/dashboard";
        }

        // Update only the editable fields
        existingJob.setTitle(job.getTitle());
        existingJob.setCompany(job.getCompany());
        existingJob.setLocation(job.getLocation());
        existingJob.setDescription(job.getDescription());

        // Ensure recruiterId remains the same
        existingJob.setRecruiterId(user.getId());

        jobService.updateJob(existingJob);
        return "redirect:/recruiter/dashboard";
    }

    @GetMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable String id) {
        jobService.deleteJob(id);
        return "redirect:/recruiter/dashboard";
    }

    @GetMapping("/view-applicants/{jobId}")
    public String viewApplicants(@PathVariable String jobId, Model model, HttpSession session) {
        // Check user authentication
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserType().equals("recruiter")) {
            return "redirect:/login";
        }

        try {
            // System.out.println("Fetching job with ID: " + jobId);
            Job job = jobService.getJobById(jobId);

            if (job == null) {
                // System.out.println("Job not found with ID: " + jobId);
                model.addAttribute("error", "Job not found");
                return "redirect:/recruiter/dashboard";
            }

            // System.out.println("Found job: " + job.getTitle() + " (Recruiter: " + job.getRecruiterId() + ")");

            // Verify the job belongs to this recruiter
            if (!job.getRecruiterId().equals(user.getId())) {
                // System.out.println("Permission denied - Job belongs to: " + job.getRecruiterId() +
                //         ", User is: " + user.getId());
                model.addAttribute("error", "You don't have permission to view this job");
                return "redirect:/recruiter/dashboard";
            }

            // Safely handle applications
            List<Application> applications = job.getApplications() != null ? job.getApplications()
                    : Collections.emptyList();

            // System.out.println("Found " + applications.size() + " applications");

            model.addAttribute("job", job);
            model.addAttribute("applications", applications);
            return "recruiter/view-applicants";

        } catch (Exception e) {
            // System.err.println("Error viewing applicants for job " + jobId);
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while loading applicants: " + e.getMessage());
            return "redirect:/recruiter/dashboard";
        }
    }
}