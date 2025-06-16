package model;

import java.util.Date;

public class Expense {
    private double amount;
    private Category category;
    private Date date;
    private String description;
    
    public Expense(double amount, Category category, Date date, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }
    
    public double getAmount() { return amount; }
    public Category getCategory() { return category; }
    public Date getDate() { return date; }
    public String getDescription() { return description; }
}
