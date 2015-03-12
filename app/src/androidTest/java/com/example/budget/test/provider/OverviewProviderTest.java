package com.example.budget.test.provider;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.OverviewItem;
import com.example.budget.provider.OverviewProvider;

public class OverviewProviderTest extends AbstractDataProviderTest {

    OverviewProvider overviewProvider;

    @Override
    public void setUp() {
        overviewProvider = new OverviewProvider(getContext());
        overviewProvider.open();
        overviewProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(0);
    }

    public void testGetActualIncomeForMonth() {
        assertEquals("Should add up to 5.5", 5.5,
                overviewProvider.getActualIncomeForMonth(new DateTime().monthOfYear().get()));
    }

    public void testGetActualExpenseForMonth() {
        assertEquals("Should add up to -4.4", -4.4,
                overviewProvider.getActualExpenseForMonth(new DateTime().monthOfYear().get()));
    }

    public void testGetProjectedMonthlyIncome() {
        assertEquals("Should add up to 4.4", 4.4, overviewProvider.getProjectedMonthlyIncome());
    }

    public void testGetProjectedMonthlyExpense() {
        assertEquals("Should add up to -5.5", -5.5, overviewProvider.getProjectedMonthlyExpense());
    }

    public void testGetBudgetEntriesForMonth() {
        ArrayList<OverviewItem> entryList = overviewProvider.getTotalsByCategoryForMonth(new DateTime()
                .monthOfYear().get());
        assertNotNull(entryList);
        assertEquals("entryList should have 4 items", 4, entryList.size());
        assertTrue(Math.abs(entryList.get(0).projection) > 0);
        assertTrue(Math.abs(entryList.get(0).actual) > 0); // TODO these should
                                                           // be better
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return overviewProvider;
    }

}
