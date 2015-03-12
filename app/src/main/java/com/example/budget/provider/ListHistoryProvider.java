package com.example.budget.provider;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.BudgetEntry;
import com.example.budget.layout.Filter;

public class ListHistoryProvider extends BudgetDbAdapter {

    public ListHistoryProvider(Context context) {
        super(context);
    }

    public ArrayList<BudgetEntry> getEntriesUsingFilter(Filter filter) {
        Cursor queryResult = db.query(ENTRIES_TABLE, allEntryColumns, filter.buildWhereClause(), null, null,
                null, ENTRIES_DATE_KEY + " desc");
        ArrayList<BudgetEntry> entryList = createEntryArrayListFromCursor(queryResult);
        queryResult.close();
        return entryList;
    }

}
