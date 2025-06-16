package service;

import model.Expense;
import model.Category;
import java.util.*;

public class ExpenseService {
    private List<Expense> expenses;
    private Map<Category, Double> categoryTotals;
    
    public ExpenseService() {
        expenses = new ArrayList<>();
        categoryTotals = new HashMap<>();
    }
    
    public void addExpense(Expense expense) {
        expenses.add(expense);
        Category category = expense.getCategory();
        double currentTotal = categoryTotals.getOrDefault(category, 0.0);
        categoryTotals.put(category, currentTotal + expense.getAmount());
    }
    
    public List<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }
    
    public Map<Category, Double> getCategoryTotals() {
        return new HashMap<>(categoryTotals);
    }
    
    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
}
