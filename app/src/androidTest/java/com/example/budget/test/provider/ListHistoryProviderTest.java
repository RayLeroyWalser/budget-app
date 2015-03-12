package com.example.budget.test.provider;

import java.util.ArrayList;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.BudgetEntry;
import com.example.budget.layout.Filter;
import com.example.budget.provider.ListHistoryProvider;

public class ListHistoryProviderTest extends AbstractDataProviderTest {

    ListHistoryProvider listHistoryProvider;

    @Override
    public void setUp() {
        listHistoryProvider = new ListHistoryProvider(getContext());
        listHistoryProvider.open();
        listHistoryProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(0);
    }

    public void testGetActualsWithFilter_noFilter() {
        Filter filter = new Filter();
        assertEquals(4, listHistoryProvider.getEntriesUsingFilter(filter).size());
    }

    public void testGetActualsWithFilter_oneCategory() {
        Filter filter = new Filter();
        filter.addCategory(listHistoryProvider.getCategoryForName("Paycheck"));
        assertEquals(1, listHistoryProvider.getEntriesUsingFilter(filter).size());
    }

    public void testGetActualsWithFilter_multipleCategories() {
        Filter filter = new Filter();
        filter.addCategory(listHistoryProvider.getCategoryForName("Paycheck"));
        filter.addCategory(listHistoryProvider.getCategoryForName("Dividend"));
        filter.addCategory(listHistoryProvider.getCategoryForName("Food"));
        ArrayList<BudgetEntry> entries = listHistoryProvider.getEntriesUsingFilter(filter);
        assertEquals(3, entries.size());
        assertInDescendingOrder(entries);
    }

    private void assertInDescendingOrder(ArrayList<BudgetEntry> entries) {
        BudgetEntry lastEntry = null;
        for (BudgetEntry entry : entries) {
            if (lastEntry != null) {
                assertTrue("list is not in descending order", entry.getDate().after(lastEntry.getDate()));
            }
        }
    }

    public BudgetDbAdapter getProvider() {
        return listHistoryProvider;
    }

}
