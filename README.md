A comprehensive expense tracking system built with Java that helps users manage their finances through a console interface. Features include income/expense tracking, budgeting, recurring transactions, and financial reporting.

Features:
Income/Expense Tracking: Record financial transactions with categories
Budget Management: Set monthly budgets for spending categories
Recurring Transactions: Automate regular payments/salary deposits
Advanced Reporting:
       Monthly salary statements
       Spending breakdowns by category
       Investment recommendations (20% of surplus)
Data Visualization: ASCII pie/bar charts
Search Functionality: Filter transactions by date, amount, or category

Technologies Used:
Core Java (Collections, Stream API, Date/Time)
OOP Principles (Encapsulation, Inheritance, Polymorphism)
Data Structures (HashMaps, ArrayLists)
Algorithms (Binary Search, Sorting)

Project Structure:

expense-tracker/
├── src/
│   ├── model/
│   │   ├── Category.java

Sample Usage:

Adding Income:-
=== EXPENSE TRACKER ===
1. Add Income
2. Add Expense
Enter choice: 1
=== ADD INCOME ===
Amount: 5000
Date (yyyy-MM-dd): 2023-11-01
Source: Salary

Nov 01, 2023 12:00 PM [Income]    +$5000.00               Salary        (Balance: $5000.00)


Adding Expense:-
Enter choice: 2
=== ADD EXPENSE ===
Categories:
1. Food
2. Transport...
Select category: 1
Amount: 150
Date (yyyy-MM-dd): 2023-11-02
Description: Groceries

Nov 02, 2023 12:00 PM [Expense]   -$150.00    Food         Groceries      (Balance: $4850.00)


Monthly Report:-
Enter choice: 12

=== Monthly Salary Statement ===
For: 2023-11

Total Salary Credited: $5000.00

=== Salary Utilization Breakdown ===
Date       | Category | Description | Amount  | Remaining
-----------|----------|-------------|---------|----------
2023-11-02 | Food     | Groceries   | $150.00 | $4850.00

=== Final Summary ===
Starting Salary: $5000.00
Total Expenses: $150.00
Remaining Balance: $4850.00
Utilization Percentage: 3.0%
