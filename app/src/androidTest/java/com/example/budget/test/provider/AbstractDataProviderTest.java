package com.example.budget.test.provider;

import org.joda.time.DateTime;

import android.test.AndroidTestCase;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.BudgetEntry;
import com.example.budget.layout.Category;

public abstract class AbstractDataProviderTest extends AndroidTestCase {
    
    public abstract BudgetDbAdapter getProvider();
    
    public void insertTestEntries(int dayOffset) {
        BudgetEntry budgetEntry = new BudgetEntry();
        
        budgetEntry.setCategory(getProvider().getCategoryForName("Paycheck"));
        budgetEntry.setAmount(2.3);
        budgetEntry.setDate(new DateTime().plusDays(3+dayOffset).toDate());
        getProvider().insertEntry(budgetEntry);
        
        budgetEntry.setCategory(getProvider().getCategoryForName("Dividend"));
        budgetEntry.setAmount(3.2);
        budgetEntry.setDate(new DateTime().plusDays(1+dayOffset).toDate());
        getProvider().insertEntry(budgetEntry);
        
        budgetEntry.setCategory(getProvider().getCategoryForName("Food"));
        budgetEntry.setAmount(-2.2);
        budgetEntry.setDate(new DateTime().plusDays(2+dayOffset).toDate());
        getProvider().insertEntry(budgetEntry);
        
        budgetEntry.setCategory(getProvider().getCategoryForName("Gas"));
        budgetEntry.setAmount(-2.2);
        budgetEntry.setDate(new DateTime().plusDays(dayOffset).toDate());
        getProvider().insertEntry(budgetEntry);
    }

    public void insertTestCategories() {
        Category category = new Category();
        category.setName("Paycheck");
        category.setActive(true);
        category.setAmount(2.2);
        getProvider().insertCategory(category);
        category.setName("Dividend");
        category.setActive(true);
        category.setAmount(2.2);
        getProvider().insertCategory(category);
        category.setName("Food");
        category.setActive(true);
        category.setAmount(-2.3);
        getProvider().insertCategory(category);
        category.setName("Gas");
        category.setActive(true);
        category.setAmount(-3.2);
        getProvider().insertCategory(category);
    }
}
