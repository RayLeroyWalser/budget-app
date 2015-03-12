package com.example.budget.provider;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.Category;
import com.example.budget.layout.Filter;
import com.example.budget.layout.FilterListAdapterItem;

public class HistoryFiltersViewProvider extends BudgetDbAdapter {

    public HistoryFiltersViewProvider(Context context) {
        super(context);
    }

    public ArrayList<FilterListAdapterItem> getFilterListItemForAllCategories() {
        Cursor queryResult = db.query(CATEGORIES_TABLE, allCategoryColumns, null, null, null, null, null);
        ArrayList<FilterListAdapterItem> resultList = createFilterListItemArrayListFromCursor(queryResult);
        queryResult.close();
        return resultList;
    }

    private ArrayList<FilterListAdapterItem> createFilterListItemArrayListFromCursor(Cursor cursor) {
        ArrayList<FilterListAdapterItem> resultList = null;
        if (cursor.moveToFirst()) {
            resultList = new ArrayList<FilterListAdapterItem>();
            do {
                Category category = new Category();
                category.setId(cursor.getInt(CATEGORIES_ID_COLUMN));
                category.setAmount(cursor.getDouble(CATEGORIES_AMOUNT_COLUMN));
                category.setName(cursor.getString(CATEGORIES_NAME_COLUMN));
                category.setIsActiveFromInt(cursor.getInt(CATEGORIES_IS_ACTIVE_COLUMN));
                
                FilterListAdapterItem filterListItem = new FilterListAdapterItem();
                filterListItem.setCategory(category);
                filterListItem.setUseFilter(false);
                
                resultList.add(filterListItem);
            } while (cursor.moveToNext());
        }
        return resultList;
    }

    public Filter getFilterFromArrayList(ArrayList<FilterListAdapterItem> list){
        Filter filterItem = new Filter();
        for( FilterListAdapterItem item : list ){
            filterItem.addCategory(item.getCategory());
        }
        return filterItem;
    }
}
