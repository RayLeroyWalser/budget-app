package com.example.budget.provider;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.Category;

public class EditCategoryViewProvider extends BudgetDbAdapter {

    public EditCategoryViewProvider(Context context) {
        super(context);
    }

    public ArrayList<Category> getAllCategories() {
        Cursor queryResult = db.query(CATEGORIES_TABLE, allCategoryColumns, null, null, null, null, null);
        return createCategoryArrayListFromCursor(queryResult);
    }

    public void insertCategoryIfNotExists(Category category) throws Exception {
        if( getCategoryForName(category.getName()) == null ){
            insertCategory(category);
        } else{
            throw new Exception();
            //TODO make a custom exception and handle this better
        }
    }
}
