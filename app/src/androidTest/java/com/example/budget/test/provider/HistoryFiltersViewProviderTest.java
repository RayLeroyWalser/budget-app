package com.example.budget.test.provider;

import java.util.ArrayList;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.Filter;
import com.example.budget.layout.FilterListAdapterItem;
import com.example.budget.provider.HistoryFiltersViewProvider;

public class HistoryFiltersViewProviderTest extends AbstractDataProviderTest {

    HistoryFiltersViewProvider historyFiltersViewProvider;

    @Override
    public void setUp() {
        historyFiltersViewProvider = new HistoryFiltersViewProvider(getContext());
        historyFiltersViewProvider.open();
        historyFiltersViewProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(0);
    }

    public void testGetCategories() {
        assertEquals("should be 4 categories", 4, historyFiltersViewProvider.getFilterListItemForAllCategories().size());
    }
    
    public void testGetFilterFromArrayList(){
        ArrayList<FilterListAdapterItem> list = historyFiltersViewProvider.getFilterListItemForAllCategories();
        for( FilterListAdapterItem item : list){
            item.setUseFilter(true);
        }
        Filter filter = historyFiltersViewProvider.getFilterFromArrayList(list);
        assertNotNull(filter);
    }

    public BudgetDbAdapter getProvider() {
        return historyFiltersViewProvider;
    }

}
