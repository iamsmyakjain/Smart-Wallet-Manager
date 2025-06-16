package service;

import model.Budget;
import model.Category;
import java.util.*;

public class BudgetService {
    private List<Budget> budgets;
    
    public BudgetService() {
        budgets = new ArrayList<>();
    }
    
    public void setBudget(Budget budget) {
        budgets.removeIf(b -> b.getCategory().equals(budget.getCategory()));
        budgets.add(budget);
    }
    
    public List<Budget> getBudgets() {
        return new ArrayList<>(budgets);
    }
    
    public double getBudgetForCategory(Category category) {
        return budgets.stream()
                .filter(b -> b.getCategory().equals(category))
                .mapToDouble(Budget::getLimit)
                .findFirst()
                .orElse(0.0);
    }
}
