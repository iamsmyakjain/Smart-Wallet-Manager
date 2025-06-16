package model;

import java.util.Date;

public class RecurringTransaction {
    public enum Frequency { DAILY, WEEKLY, MONTHLY, YEARLY }
    
    private String description;
    private double amount;
    private Category category;
    private Frequency frequency;
    private Date startDate;
    private Date endDate;
    private boolean isIncome;
    
    public RecurringTransaction(String description, double amount, Category category, 
                              Frequency frequency, Date startDate, Date endDate, boolean isIncome) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isIncome = isIncome;
    }
    
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Category getCategory() { return category; }
    public Frequency getFrequency() { return frequency; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public boolean isIncome() { return isIncome; }
}
