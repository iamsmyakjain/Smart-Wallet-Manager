package service;

import model.*;
import util.ConsoleFormatter;
import java.util.*;
import java.text.SimpleDateFormat;

public class ReportService {
    private ExpenseService expenseService;
    private IncomeService incomeService;
    private BudgetService budgetService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public ReportService(ExpenseService expenseService, IncomeService incomeService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.budgetService = budgetService;
    }
    
    public double getCurrentBalance() {
        return incomeService.getTotalIncome() - expenseService.getTotalExpenses();
    }
    
    public void generateSpendingReport() {
        ConsoleFormatter.printTitle("Spending Report");
        System.out.printf("Total Income: $%.2f\n", incomeService.getTotalIncome());
        System.out.printf("Total Expenses: $%.2f\n", expenseService.getTotalExpenses());
        System.out.printf("Current Balance: $%.2f\n\n", getCurrentBalance());
        
        System.out.println("Category-wise Spending:");
        for (Map.Entry<Category, Double> entry : expenseService.getCategoryTotals().entrySet()) {
            Category category = entry.getKey();
            double spent = entry.getValue();
            double budget = budgetService.getBudgetForCategory(category);
            
            System.out.printf("%-15s: $%.2f", category.getName(), spent);
            if (budget > 0) {
                System.out.printf(" (Budget: $%.2f, Remaining: $%.2f)", budget, budget - spent);
            }
            System.out.println();
        }
    }
    
    public void generateInvestmentRecommendation() {
        double balance = getCurrentBalance();
        double recommendedInvestment = balance * 0.2;
        
        ConsoleFormatter.printTitle("Investment Recommendation");
        System.out.printf("Current available balance: $%.2f\n", balance);
        System.out.printf("Recommended amount to invest in stocks: $%.2f\n", recommendedInvestment);
    }
    
    public void generateMonthlySalaryStatement(int year, int month) {
        ConsoleFormatter.printTitle("Monthly Salary Statement");
        System.out.printf("For: %d-%02d\n\n", year, month);
        
        List<Income> salaries = incomeService.getIncomes().stream()
            .filter(income -> isInMonth(income.getDate(), year, month))
            .filter(income -> income.getSource().equalsIgnoreCase("Salary"))
            .collect(Collectors.toList());
        
        if (salaries.isEmpty()) {
            System.out.println("No salary records found for this month.");
            return;
        }
        
        double totalSalary = salaries.stream().mapToDouble(Income::getAmount).sum();
        System.out.printf("Total Salary Credited: $%.2f\n\n", totalSalary);
        
        Date firstSalaryDate = salaries.get(0).getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstSalaryDate);
        cal.add(Calendar.MONTH, 1);
        Date nextSalaryDate = cal.getTime();
        
        List<Expense> monthlyExpenses = expenseService.getExpenses().stream()
            .filter(expense -> !expense.getDate().before(firstSalaryDate))
            .filter(expense -> expense.getDate().before(nextSalaryDate))
            .collect(Collectors.toList());
        
        ConsoleFormatter.printTitle("Salary Utilization Breakdown");
        System.out.println("Date       | Category    | Description      | Amount     | Remaining");
        System.out.println("-----------|-------------|------------------|------------|----------");
        
        double runningBalance = totalSalary;
        for (Expense expense : monthlyExpenses) {
            runningBalance -= expense.getAmount();
            System.out.printf("%-10s | %-11s | %-16s | $-9.2f | $%-8.2f\n",
                dateFormat.format(expense.getDate()),
                expense.getCategory().getName(),
                expense.getDescription(),
                expense.getAmount(),
                runningBalance);
        }
        
        ConsoleFormatter.printTitle("Final Summary");
        System.out.printf("Starting Salary: $%.2f\n", totalSalary);
        System.out.printf("Total Expenses: $%.2f\n", monthlyExpenses.stream().mapToDouble(Expense::getAmount).sum());
        System.out.printf("Remaining Balance: $%.2f\n", runningBalance);
        System.out.printf("Utilization Percentage: %.1f%%\n", 
            ((totalSalary - runningBalance)/totalSalary)*100);
    }
    
    private boolean isInMonth(Date date, int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR) == year && 
               cal.get(Calendar.MONTH) == (month-1);
    }
}
