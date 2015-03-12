package com.example.budget.provider;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.Category;

public class AddSingleEntryProvider extends BudgetDbAdapter {

    public AddSingleEntryProvider(Context context) {
        super(context);
    }

    public ArrayList<Category> getAllCategories() {
        Cursor queryResult = db.query(CATEGORIES_TABLE, allCategoryColumns, null, null, null, null, null);
        ArrayList<Category> resultList = createCategoryArrayListFromCursor(queryResult);
        queryResult.close();
        return resultList;
    }
}
