package com.example.budget.test.converter;

import java.util.Date;

import android.content.Intent;
import android.test.AndroidTestCase;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.converter.BudgetEntryConverter;
import com.example.budget.layout.BudgetEntry;

public class BudgetEntryConverterTest extends AndroidTestCase {

    private BudgetDbAdapter provider;
    
    @Override
    public void setUp(){
        provider = new BudgetDbAdapter(getContext());
        provider.open();
    }
    
    public void testAddEntryToIntent(){
        Intent intent = new Intent();
        BudgetEntry entry = new BudgetEntry();
        entry.setCategory(provider.getCategoryForName("Paycheck"));
        entry.setDescription("Test entry");
        entry.setAmount(2.3);
        entry.setDate(new Date());
        intent = BudgetEntryConverter.addEntryToIntent(intent, entry);
        assertEquals(entry.getAmount(), intent.getDoubleExtra(BudgetEntryConverter.AMOUNT_EXTRA_NAME, 0));
        assertEquals(entry.getCategory().getId(), intent.getLongExtra(BudgetEntryConverter.CATEGORY_EXTRA_NAME,0));
        assertEquals(entry.getDescription(), intent.getStringExtra(BudgetEntryConverter.DESCRIPTION_EXTRA_NAME));
    }
}
