package util;

import model.Category;
import java.util.Map;

public class ChartGenerator {
    public static void generatePieChart(Map<Category, Double> categoryTotals) {
        System.out.println("\n=== Expense Distribution (Pie Chart) ===");
        double total = categoryTotals.values().stream().mapToDouble(Double::doubleValue).sum();
        
        for (Map.Entry<Category, Double> entry : categoryTotals.entrySet()) {
            double percentage = (entry.getValue() / total) * 100;
            System.out.printf("%-15s: ", entry.getKey().getName());
            printBar(percentage);
            System.out.printf(" %.1f%%\n", percentage);
        }
    }
    
    private static void printBar(double percentage) {
        int bars = (int) (percentage / 2);
        for (int i = 0; i < bars; i++) {
            System.out.print("█");
        }
    }
    
    public static void generateBarChart(Map<Category, Double> categoryTotals) {
        System.out.println("\n=== Expense Comparison (Bar Chart) ===");
        double max = categoryTotals.values().stream().max(Double::compare).orElse(1.0);
        
        for (Map.Entry<Category, Double> entry : categoryTotals.entrySet()) {
            System.out.printf("%-15s: ", entry.getKey().getName());
            printScaledBar(entry.getValue(), max);
            System.out.printf(" $%.2f\n", entry.getValue());
        }
    }
    
    private static void printScaledBar(double value, double max) {
        int maxBars = 50;
        int bars = (int) ((value / max) * maxBars);
        for (int i = 0; i < bars; i++) {
            System.out.print("|");
        }
    }
}
