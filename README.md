A comprehensive expense tracking system built with Java that helps users manage their finances through a console interface. Features include income/expense tracking, budgeting, recurring transactions, and financial reporting.

Features:-
1. Income/Expense Tracking: Record financial transactions with categories.
2. Budget Management: Set monthly budgets for spending categories.
3. Recurring Transactions: Automate regular payments/salary deposits.
4. Advanced Reporting:
        Monthly salary statements.
        Spending breakdowns by category.
        Investment recommendations (20% of surplus).
5. Data Visualization: ASCII pie/bar charts.
6. Search Functionality: Filter transactions by date, amount, or category.

Technology Used:-
1. Core Java (Collections, Stream API, Date/Time)
2. OOP Principles (Encapsulation, Inheritance, Polymorphism)
3. Data Structures (HashMaps, ArrayLists)
4. Algorithms (Binary Search, Sorting)


Sample Usage:

     ADDING INCOME:
    === EXPENSE TRACKER ===
    1. Add Income
    2. Add Expense
    Enter choice: 1
    === ADD INCOME ===
    Amount: 5000
    Date (yyyy-MM-dd): 2023-11-01
    Source: Salary

    Nov 01, 2023 12:00 PM [Income]    +$5000.00               Salary        (Balance: $5000.00)

    ADDING EXPENSE:

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

    MONTHLY REPORT:-
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
