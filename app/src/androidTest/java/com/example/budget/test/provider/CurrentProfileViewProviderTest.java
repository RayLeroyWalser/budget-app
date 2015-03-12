package com.example.budget.test.provider;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.provider.CurrentProfileViewProvider;

public class CurrentProfileViewProviderTest extends AbstractDataProviderTest {

    CurrentProfileViewProvider currentProfileViewProvider;
    
    @Override
    public void setUp(){
        currentProfileViewProvider = new CurrentProfileViewProvider(getContext());
        currentProfileViewProvider.open();
        currentProfileViewProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(0);
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return currentProfileViewProvider;
    }
    
    public void testGetAllCategories(){
        assertEquals(4,currentProfileViewProvider.getCategories().size());
    }
}
