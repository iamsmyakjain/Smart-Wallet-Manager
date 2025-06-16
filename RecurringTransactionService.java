package service;

import model.RecurringTransaction;
import model.Expense;
import model.Income;
import model.Category;
import java.util.*;

public class RecurringTransactionService {
    private List<RecurringTransaction> recurringTransactions;
    private ExpenseService expenseService;
    private IncomeService incomeService;
    
    public RecurringTransactionService(ExpenseService expenseService, IncomeService incomeService) {
        this.recurringTransactions = new ArrayList<>();
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }
    
    public void addRecurringTransaction(RecurringTransaction transaction) {
        recurringTransactions.add(transaction);
    }
    
    public void processDueTransactions() {
        Date now = new Date();
        Iterator<RecurringTransaction> iterator = recurringTransactions.iterator();
        
        while (iterator.hasNext()) {
            RecurringTransaction rt = iterator.next();
            if (isDue(rt, now)) {
                if (rt.isIncome()) {
                    incomeService.addIncome(new Income(rt.getAmount(), now, rt.getDescription()));
                } else {
                    expenseService.addExpense(new Expense(rt.getAmount(), rt.getCategory(), now, rt.getDescription()));
                }
                
                if (rt.getEndDate() != null && rt.getEndDate().before(now)) {
                    iterator.remove();
                }
            }
        }
    }
    
    private boolean isDue(RecurringTransaction rt, Date currentDate) {
        if (currentDate.before(rt.getStartDate())) return false;
        if (rt.getEndDate() != null && currentDate.after(rt.getEndDate())) return false;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        
        cal.setTime(rt.getStartDate());
        int startDay = cal.get(Calendar.DAY_OF_MONTH);
        int startWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int startMonth = cal.get(Calendar.MONTH);
        int startYear = cal.get(Calendar.YEAR);
        
        switch (rt.getFrequency()) {
            case DAILY: return true;
            case WEEKLY: return (currentWeek - startWeek) % 1 == 0;
            case MONTHLY: return currentDay == startDay;
            case YEARLY: return currentMonth == startMonth && currentDay == startDay;
            default: return false;
        }
    }
    
    public List<RecurringTransaction> getRecurringTransactions() {
        return new ArrayList<>(recurringTransactions);
    }
}
