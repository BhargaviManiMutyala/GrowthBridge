package com.shrivecw.investandgrow;
public class Investor {
    private String investedIn;
    private String description;
    private String interest;
    private String bankDocumentUri;
    private String taxDocumentUri;

    // Empty constructor for Firestore
    public Investor() {}

    public Investor(String investedIn, String description, String interest, String bankDocumentUri, String taxDocumentUri) {
        this.investedIn = investedIn;
        this.description = description;
        this.interest = interest;
    }

    public String getInvestedIn() { return investedIn; }
    public String getDescription() { return description; }
    public String getInterest() { return interest; }
}
