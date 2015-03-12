package com.example.budget.test.provider;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.provider.EditCategoryViewProvider;

public class EditCategoryViewProviderTest extends AbstractDataProviderTest {

    private EditCategoryViewProvider editCategoryViewProvider;
    
    @Override
    public void setUp(){
        editCategoryViewProvider = new EditCategoryViewProvider(getContext());
        editCategoryViewProvider.open();
        editCategoryViewProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(0);
    }
    
    @Override
    public BudgetDbAdapter getProvider() {
        return editCategoryViewProvider;
    }
    
    public void testGetCategories(){
        assertEquals(4,editCategoryViewProvider.getAllCategories().size());
    }

}
