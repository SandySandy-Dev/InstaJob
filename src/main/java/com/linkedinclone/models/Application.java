package com.linkedinclone.models;

public class Application {
    private String jobSeekerId;
    private String jobSeekerName;
    private String mobileNumber;
    private String introduction;
    private String githubLink;
    private String linkedinLink;
    private String portfolioLink;
    private String whyJoinUs;
    private String status; // "applied", "viewed", etc.
    private String appliedDate;
    
    // Getters and setters
    public String getJobSeekerId() { return jobSeekerId; }
    public void setJobSeekerId(String jobSeekerId) { this.jobSeekerId = jobSeekerId; }
    public String getJobSeekerName() { return jobSeekerName; }
    public void setJobSeekerName(String jobSeekerName) { this.jobSeekerName = jobSeekerName; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
    public String getGithubLink() { return githubLink; }
    public void setGithubLink(String githubLink) { this.githubLink = githubLink; }
    public String getLinkedinLink() { return linkedinLink; }
    public void setLinkedinLink(String linkedinLink) { this.linkedinLink = linkedinLink; }
    public String getPortfolioLink() { return portfolioLink; }
    public void setPortfolioLink(String portfolioLink) { this.portfolioLink = portfolioLink; }
    public String getWhyJoinUs() { return whyJoinUs; }
    public void setWhyJoinUs(String whyJoinUs) { this.whyJoinUs = whyJoinUs; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAppliedDate() { return appliedDate; }
    public void setAppliedDate(String appliedDate) { this.appliedDate = appliedDate; }
}