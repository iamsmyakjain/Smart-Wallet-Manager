import model.*;
import service.*;
import util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private static ExpenseService expenseService = new ExpenseService();
    private static IncomeService incomeService = new IncomeService();
    private static BudgetService budgetService = new BudgetService();
    private static RecurringTransactionService recurringTransactionService = 
        new RecurringTransactionService(expenseService, incomeService);
    private static ReportService reportService = 
        new ReportService(expenseService, incomeService, budgetService);
    private static SearchService searchService = 
        new SearchService(expenseService, incomeService);
    
    private static List<Category> categories = new ArrayList<>();
    
    public static void main(String[] args) {
        initializeCategories();
        recurringTransactionService.processDueTransactions();
        
        while (true) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addIncome(); break;
                case 2: addExpense(); break;
                case 3: setBudget(); break;
                case 4: addCustomCategory(); break;
                case 5: reportService.generateSpendingReport(); break;
                case 6: ChartGenerator.generatePieChart(expenseService.getCategoryTotals()); break;
                case 7: ChartGenerator.generateBarChart(expenseService.getCategoryTotals()); break;
                case 8: reportService.generateInvestmentRecommendation(); break;
                case 9: viewTransactionHistory(); break;
                case 10: manageRecurringTransactions(); break;
                case 11: advancedSearch(); break;
                case 12: generateMonthlyStatement(); break;
                case 13: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void initializeCategories() {
        categories.add(new Category("Food"));
        categories.add(new Category("Transport"));
        categories.add(new Category("Rent"));
        categories.add(new Category("Utilities"));
        categories.add(new Category("Entertainment"));
        categories.add(new Category("Shopping"));
        categories.add(new Category("Stock"));
    }
    
    private static void printMainMenu() {
        ConsoleFormatter.printTitle("EXPENSE TRACKER");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. Set Budget");
        System.out.println("4. Add Custom Category");
        System.out.println("5. Spending Report");
        System.out.println("6. View Pie Chart");
        System.out.println("7. View Bar Chart");
        System.out.println("8. Investment Recommendation");
        System.out.println("9. Transaction History");
        System.out.println("10. Recurring Transactions");
        System.out.println("11. Advanced Search");
        System.out.println("12. Monthly Statement");
        System.out.println("13. Exit");
        System.out.print("Enter choice: ");
    }
    
    private static void addIncome() {
        try {
            ConsoleFormatter.printTitle("ADD INCOME");
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("Date (yyyy-MM-dd): ");
            Date date = dateFormat.parse(scanner.nextLine());
            
            System.out.print("Source: ");
            String source = scanner.nextLine();
            
            incomeService.addIncome(new Income(amount, date, source));
            System.out.println("Income added!");
            
            double balance = incomeService.getTotalIncome() - expenseService.getTotalExpenses();
            ConsoleFormatter.printTransaction(TransactionType.INCOME, amount, date, "", source, balance);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void addExpense() {
        try {
            ConsoleFormatter.printTitle("ADD EXPENSE");
            System.out.println("Categories:");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i+1) + ". " + categories.get(i));
            }
            System.out.print("Select category: ");
            int catIndex = scanner.nextInt()-1;
            scanner.nextLine();
            
            if (catIndex < 0 || catIndex >= categories.size()) {
                System.out.println("Invalid category");
                return;
            }
            
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("Date (yyyy-MM-dd): ");
            Date date = dateFormat.parse(scanner.nextLine());
            
            System.out.print("Description: ");
            String desc = scanner.nextLine();
            
            Expense expense = new Expense(amount, categories.get(catIndex), date, desc);
            expenseService.addExpense(expense);
            System.out.println("Expense added!");
            
            double balance = incomeService.getTotalIncome() - expenseService.getTotalExpenses();
            ConsoleFormatter.printTransaction(TransactionType.EXPENSE, amount, date, 
                categories.get(catIndex).getName(), desc, balance);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void setBudget() {
        ConsoleFormatter.printTitle("SET BUDGET");
        System.out.println("Categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i+1) + ". " + categories.get(i));
        }
        System.out.print("Select category: ");
        int catIndex = scanner.nextInt()-1;
        scanner.nextLine();
        
        if (catIndex < 0 || catIndex >= categories.size()) {
            System.out.println("Invalid category");
            return;
        }
        
        System.out.print("Monthly budget amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        budgetService.setBudget(new Budget(categories.get(catIndex), amount));
        System.out.printf("Budget set to $%.2f for %s\n", amount, categories.get(catIndex).getName());
    }
    
    private static void addCustomCategory() {
        ConsoleFormatter.printTitle("ADD CATEGORY");
        System.out.print("Enter new category name: ");
        String name = scanner.nextLine();
        categories.add(new Category(name));
        System.out.println("Category added!");
    }
    
    private static void viewTransactionHistory() {
        ConsoleFormatter.printTitle("TRANSACTION HISTORY");
        double balance = incomeService.getTotalIncome();
        
        // Print incomes first
        for (Income income : incomeService.getIncomes()) {
            ConsoleFormatter.printTransaction(TransactionType.INCOME, income.getAmount(), 
                income.getDate(), "", income.getSource(), balance);
            balance -= income.getAmount();
        }
        
        // Print expenses
        for (Expense expense : expenseService.getExpenses()) {
            balance -= expense.getAmount();
            ConsoleFormatter.printTransaction(TransactionType.EXPENSE, expense.getAmount(), 
                expense.getDate(), expense.getCategory().getName(), 
                expense.getDescription(), balance);
        }
    }
    
    private static void manageRecurringTransactions() {
        while (true) {
            ConsoleFormatter.printTitle("RECURRING TRANSACTIONS");
            System.out.println("1. Add New");
            System.out.println("2. View All");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addRecurringTransaction(); break;
                case 2: viewRecurringTransactions(); break;
                case 3: return;
                default: System.out.println("Invalid choice");
            }
        }
    }
    
    private static void addRecurringTransaction() {
        try {
            ConsoleFormatter.printTitle("ADD RECURRING TRANSACTION");
            System.out.print("Is this an income? (y/n): ");
            boolean isIncome = scanner.nextLine().equalsIgnoreCase("y");
            
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("Description: ");
            String desc = scanner.nextLine();
            
            Category category = null;
            if (!isIncome) {
                System.out.println("Categories:");
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println((i+1) + ". " + categories.get(i));
                }
                System.out.print("Select category: ");
                int catIndex = scanner.nextInt()-1;
                scanner.nextLine();
                if (catIndex >= 0 && catIndex < categories.size()) {
                    category = categories.get(catIndex);
                }
            }
            
            System.out.println("Frequency:");
            System.out.println("1. Daily");
            System.out.println("2. Weekly");
            System.out.println("3. Monthly");
            System.out.println("4. Yearly");
            System.out.print("Select: ");
            int freq = scanner.nextInt();
            scanner.nextLine();
            
            RecurringTransaction.Frequency frequency;
            switch (freq) {
                case 1: frequency = RecurringTransaction.Frequency.DAILY; break;
                case 2: frequency = RecurringTransaction.Frequency.WEEKLY; break;
                case 3: frequency = RecurringTransaction.Frequency.MONTHLY; break;
                case 4: frequency = RecurringTransaction.Frequency.YEARLY; break;
                default: frequency = RecurringTransaction.Frequency.MONTHLY;
            }
            
            System.out.print("Start date (yyyy-MM-dd): ");
            Date start = dateFormat.parse(scanner.nextLine());
            
            Date end = null;
            System.out.print("End date (optional, yyyy-MM-dd): ");
            String endStr = scanner.nextLine();
            if (!endStr.isEmpty()) {
                end = dateFormat.parse(endStr);
            }
            
            RecurringTransaction rt = new RecurringTransaction(
                desc, amount, category, frequency, start, end, isIncome);
            recurringTransactionService.addRecurringTransaction(rt);
            System.out.println("Recurring transaction added!");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void viewRecurringTransactions() {
        ConsoleFormatter.printTitle("RECURRING TRANSACTIONS");
        if (recurringTransactionService.getRecurringTransactions().isEmpty()) {
            System.out.println("No recurring transactions");
            return;
        }
        
        System.out.println("Date Range         | Type    | Amount    | Category    | Frequency | Description");
        System.out.println("-------------------|---------|-----------|-------------|-----------|------------");
        
        for (RecurringTransaction rt : recurringTransactionService.getRecurringTransactions()) {
            ConsoleFormatter.printRecurringTransaction(
                rt.getDescription(), rt.getAmount(), rt.getStartDate(), rt.getEndDate(),
                rt.getFrequency().toString(), 
                rt.getCategory() != null ? rt.getCategory().getName() : "",
                rt.isIncome());
        }
    }
    
    private static void advancedSearch() {
        try {
            ConsoleFormatter.printTitle("ADVANCED SEARCH");
            Map<String, Object> criteria = new HashMap<>();
            
            System.out.println("Leave blank to skip any field");
            System.out.print("Category (number): ");
            String catInput = scanner.nextLine();
            if (!catInput.isEmpty()) {
                int catIndex = Integer.parseInt(catInput)-1;
                if (catIndex >= 0 && catIndex < categories.size()) {
                    criteria.put("category", categories.get(catIndex));
                }
            }
            
            System.out.print("Min amount: ");
            String min = scanner.nextLine();
            if (!min.isEmpty()) {
                criteria.put("minAmount", Double.parseDouble(min));
            }
            
            System.out.print("Max amount: ");
            String max = scanner.nextLine();
            if (!max.isEmpty()) {
                criteria.put("maxAmount", Double.parseDouble(max));
            }
            
            System.out.print("Start date (yyyy-MM-dd): ");
            String start = scanner.nextLine();
            if (!start.isEmpty()) {
                criteria.put("startDate", dateFormat.parse(start));
            }
            
            System.out.print("End date (yyyy-MM-dd): ");
            String end = scanner.nextLine();
            if (!end.isEmpty()) {
                criteria.put("endDate", dateFormat.parse(end));
            }
            
            System.out.print("Description contains: ");
            String desc = scanner.nextLine();
            if (!desc.isEmpty()) {
                criteria.put("description", desc);
            }
            
            List<Expense> results = searchService.searchExpenses(criteria);
            ConsoleFormatter.printTitle("SEARCH RESULTS");
            
            if (results.isEmpty()) {
                System.out.println("No matching transactions");
            } else {
                double balance = incomeService.getTotalIncome();
                for (Expense exp : results) {
                    balance -= exp.getAmount();
                    ConsoleFormatter.printTransaction(TransactionType.EXPENSE, 
                        exp.getAmount(), exp.getDate(), 
                        exp.getCategory().getName(), 
                        exp.getDescription(), balance);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void generateMonthlyStatement() {
        try {
            ConsoleFormatter.printTitle("MONTHLY STATEMENT");
            System.out.print("Year (e.g. 2023): ");
            int year = scanner.nextInt();
            
            System.out.print("Month (1-12): ");
            int month = scanner.nextInt();
            scanner.nextLine();
            
            reportService.generateMonthlySalaryStatement(year, month);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
