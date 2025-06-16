package model;

import java.util.Date;

public class Income {
    private double amount;
    private Date date;
    private String source;
    
    public Income(double amount, Date date, String source) {
        this.amount = amount;
        this.date = date;
        this.source = source;
    }
    
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public String getSource() { return source; }
}
