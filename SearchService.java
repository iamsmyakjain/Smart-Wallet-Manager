package service;

import model.Expense;
import java.util.*;
import java.util.stream.Collectors;

public class SearchService {
    private ExpenseService expenseService;
    private IncomeService incomeService;
    
    public SearchService(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }
    
    public List<Expense> searchExpenses(Map<String, Object> criteria) {
        return expenseService.getExpenses().stream()
            .filter(expense -> matchesCriteria(expense, criteria))
            .sorted(Comparator.comparing(Expense::getDate).reversed())
            .collect(Collectors.toList());
    }
    
    private boolean matchesCriteria(Expense expense, Map<String, Object> criteria) {
        return criteria.entrySet().stream().allMatch(entry -> {
            switch (entry.getKey()) {
                case "category": return expense.getCategory().equals(entry.getValue());
                case "minAmount": return expense.getAmount() >= (Double)entry.getValue();
                case "maxAmount": return expense.getAmount() <= (Double)entry.getValue();
                case "startDate": return !expense.getDate().before((Date)entry.getValue());
                case "endDate": return !expense.getDate().after((Date)entry.getValue());
                case "description": 
                    return expense.getDescription().toLowerCase()
                        .contains(((String)entry.getValue()).toLowerCase());
                default: return true;
            }
        });
    }
}
