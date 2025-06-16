package model;

public class Budget {
    private Category category;
    private double limit;
    
    public Budget(Category category, double limit) {
        this.category = category;
        this.limit = limit;
    }
    
    public Category getCategory() { return category; }
    public double getLimit() { return limit; }
}
