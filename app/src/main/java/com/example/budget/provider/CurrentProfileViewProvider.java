package com.example.budget.provider;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.Category;

public class CurrentProfileViewProvider extends BudgetDbAdapter {

    public CurrentProfileViewProvider(Context context) {
        super(context);
    }

    public ArrayList<Category> getCategories() {
        Cursor queryResult = db.query(CATEGORIES_TABLE, allCategoryColumns, null, null, null, null, null);
        return createCategoryArrayListFromCursor(queryResult);
    }

}
