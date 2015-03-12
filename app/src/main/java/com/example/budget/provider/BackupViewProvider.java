package com.example.budget.provider;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.BudgetEntry;

public class BackupViewProvider extends BudgetDbAdapter {

    public BackupViewProvider(Context context) {
        super(context);
    }

    public List<BudgetEntry> getNewEntries(){
        Cursor queryResult = db.query(ENTRIES_TABLE, allEntryColumns, null, null, null, null, null);
        return createEntryArrayListFromCursor(queryResult);
    }
    
    public void updateEntriesFromArray(ArrayList<BudgetEntry> entryList){
        //TODO implement this method
    }
}
