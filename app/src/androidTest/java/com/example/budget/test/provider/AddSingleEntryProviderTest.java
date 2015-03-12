package com.example.budget.test.provider;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.provider.AddSingleEntryProvider;

public class AddSingleEntryProviderTest extends AbstractDataProviderTest {

    AddSingleEntryProvider addSingleEntryProvider;
    
    @Override
    public void setUp(){
        addSingleEntryProvider = new AddSingleEntryProvider(getContext());
        addSingleEntryProvider.open();
        addSingleEntryProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(0);
    }
    
    public void testGetAllCategories(){
       assertEquals("should be 4 categories", 4, addSingleEntryProvider.getAllCategories().size());
    }
    
    public BudgetDbAdapter getProvider(){
        return addSingleEntryProvider;
    }
    
}
