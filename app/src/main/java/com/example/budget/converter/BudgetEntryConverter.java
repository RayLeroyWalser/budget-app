package com.example.budget.converter;

import java.text.ParseException;

import android.content.Intent;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.BudgetEntry;

public class BudgetEntryConverter {

    public static String ID_EXTRA_NAME = "entryId";
    public static String AMOUNT_EXTRA_NAME = "entryAmount";
    public static String CATEGORY_EXTRA_NAME = "entryCategory";
    public static String DESCRIPTION_EXTRA_NAME = "entryDescription";
    public static String DATE_EXTRA_NAME = "entryDate";

    public static Intent addEntryToIntent(Intent intent, BudgetEntry entry) {
        intent.putExtra(ID_EXTRA_NAME, entry.getId());
        intent.putExtra(AMOUNT_EXTRA_NAME, entry.getAmount());
        intent.putExtra(CATEGORY_EXTRA_NAME, entry.getCategory().getId());
        intent.putExtra(DESCRIPTION_EXTRA_NAME, entry.getDescription());
        intent.putExtra(DATE_EXTRA_NAME, entry.getDateAsString());
        return intent;
    }

    public static BudgetEntry getEntryFromIntent(Intent intent, BudgetDbAdapter provider) throws ParseException {
        BudgetEntry entry = new BudgetEntry();
        entry.setId(intent.getIntExtra(ID_EXTRA_NAME, -1));
        entry.setAmount(intent.getDoubleExtra(AMOUNT_EXTRA_NAME, 0.0));
        entry.setCategory(provider.getCategoryForId(intent.getLongExtra(CATEGORY_EXTRA_NAME, -1)));
        entry.setDescription(intent.getStringExtra(DESCRIPTION_EXTRA_NAME));
        entry.setDateFromString(intent.getStringExtra(DATE_EXTRA_NAME));
        return entry;
    }
}
