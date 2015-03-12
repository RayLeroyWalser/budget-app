package com.example.budget.test.provider;

import java.util.List;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.BudgetEntry;
import com.example.budget.provider.BackupViewProvider;

public class BackupViewProviderTest extends AbstractDataProviderTest {

    private BackupViewProvider backupViewProvider;
    
    protected void setUp() throws Exception {
        super.setUp();
        backupViewProvider = new BackupViewProvider(getContext());
        backupViewProvider.open();
        backupViewProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(0);
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return backupViewProvider;
    }
    
    public void testGetNewEntries(){
        List<BudgetEntry> newEntries = backupViewProvider.getNewEntries();
        assertNotNull("list is null", newEntries);
        assertEquals(4, newEntries.size());
    }

}
