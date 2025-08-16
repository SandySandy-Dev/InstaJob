package com.linkedinclone.controllers;

import com.linkedinclone.models.Application;
import com.linkedinclone.models.Job;
import com.linkedinclone.models.User;
import com.linkedinclone.services.JobService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// import java.util.UUID;

@Controller
@RequestMapping("/jobseeker")
public class JobSeekerController {
    @Autowired
    private JobService jobService;

    // private static final String UPLOAD_DIR = "src/main/resources/static/uploads/resumes/";

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserType().equals("jobseeker")) {
            return "redirect:/login";
        }
        model.addAttribute("jobs", jobService.searchJobs(""));
        return "jobseeker/dashboard";
    }

    @GetMapping("/search-jobs")
    public String searchJobs(@RequestParam String keyword, Model model) {
        model.addAttribute("jobs", jobService.searchJobs(keyword));
        return "jobseeker/dashboard";
    }

    @GetMapping("/apply/{jobId}")
    public String applyPage(@PathVariable String jobId, Model model) {
        model.addAttribute("job", jobService.getJobById(jobId));
        return "jobseeker/apply";
    }

    @PostMapping("/apply/{jobId}")
    public String apply(@PathVariable String jobId,
            @RequestParam String name,
            @RequestParam String mobileNumber,
            @RequestParam String introduction,
            @RequestParam String githubLink,
            @RequestParam String linkedinLink,
            @RequestParam String portfolioLink,
            @RequestParam String whyJoinUs,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        Application application = new Application();
        application.setJobSeekerId(user.getId());
        application.setJobSeekerName(name);
        application.setMobileNumber(mobileNumber);
        application.setIntroduction(introduction);
        application.setGithubLink(githubLink);
        application.setLinkedinLink(linkedinLink);
        application.setPortfolioLink(portfolioLink);
        application.setWhyJoinUs(whyJoinUs);
        application.setStatus("applied");
        application.setAppliedDate(LocalDate.now().toString());

        // Update job applications
        Job job = jobService.getJobById(jobId);
        List<Application> applications = job.getApplications() != null ? job.getApplications() : new ArrayList<>();
        applications.add(application);
        job.setApplications(applications);
        jobService.updateJob(job);

        return "redirect:/jobseeker/dashboard";
    }

    @GetMapping("/my-applications")
    public String myApplications(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Job> allJobs = jobService.searchJobs("");
        List<Job> appliedJobs = new ArrayList<>();

        for (Job job : allJobs) {
            if (job.getApplications() != null) {
                for (Application app : job.getApplications()) {
                    if (app.getJobSeekerId().equals(user.getId())) {
                        appliedJobs.add(job);
                        break;
                    }
                }
            }
        }

        model.addAttribute("appliedJobs", appliedJobs);
        return "jobseeker/my-applications";
    }
}