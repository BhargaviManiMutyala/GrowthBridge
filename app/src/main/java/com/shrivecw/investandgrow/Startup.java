package com.shrivecw.investandgrow;

public class Startup {
    private String title;
    private String category;
    private String description;
    private int companyPeriod;
    private double annualIncome;

    // Empty constructor for Firestore
    public Startup() {}

    public Startup(String title, String category, String description, int companyPeriod, double annualIncome) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.companyPeriod = companyPeriod;
        this.annualIncome = annualIncome;
    }

    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public int getCompanyPeriod() { return companyPeriod; }
    public double getAnnualIncome() { return annualIncome; }
}
