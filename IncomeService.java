package service;

import model.Income;
import java.util.*;

public class IncomeService {
    private List<Income> incomes;
    
    public IncomeService() {
        incomes = new ArrayList<>();
    }
    
    public void addIncome(Income income) {
        incomes.add(income);
    }
    
    public List<Income> getIncomes() {
        return new ArrayList<>(incomes);
    }
    
    public double getTotalIncome() {
        return incomes.stream().mapToDouble(Income::getAmount).sum();
    }
}
