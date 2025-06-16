package util;

import model.TransactionType;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleFormatter {
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    public static void printTitle(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    public static void printTransaction(TransactionType type, double amount, 
                                      Date date, String category, String description, 
                                      double balance) {
        String typeStr = type == TransactionType.INCOME ? "[Income] " : "[Expense]";
        String amountStr = type == TransactionType.INCOME ? 
                          String.format("+$%.2f", amount) : 
                          String.format("-$%.2f", amount);
        
        System.out.printf("%-12s %-9s %10s %-15s %-20s (Balance: $%.2f)\n",
            dateTimeFormat.format(date),
            typeStr,
            amountStr,
            category != null ? category : "",
            description,
            balance);
    }

    public static void printRecurringTransaction(String description, double amount, 
                                               Date startDate, Date endDate, 
                                               String frequency, String category, 
                                               boolean isIncome) {
        String type = isIncome ? "Income" : "Expense";
        String amountDisplay = isIncome ? 
                             String.format("+$%.2f", amount) : 
                             String.format("-$%.2f", amount);
        
        String dateRange = dateFormat.format(startDate) + 
                          (endDate != null ? " to " + dateFormat.format(endDate) : "");
        
        System.out.printf("%-20s %-8s %10s %-15s %-12s %-20s\n",
            dateRange,
            type,
            amountDisplay,
            category != null ? category : "",
            frequency,
            description);
    }

    public static void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }
}
