package com.example.budget.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.Category;
import com.example.budget.layout.SuggestionItem;

public class SuggestedProfileViewProvider extends BudgetDbAdapter {

    private static final int NUMBER_OF_MONTHS = 3;
    public static final double SUGGESTION_INCREASE_FACTOR = 1.05;

    public SuggestedProfileViewProvider(Context context) {
        super(context);
    }

    public List<SuggestionItem> getSuggestionList() {
        ArrayList<SuggestionItem> suggestionList = new ArrayList<SuggestionItem>();
        ArrayList<Category> categories = getAllCategories();

        for (Category category : categories) {
            double suggestedAmount = SUGGESTION_INCREASE_FACTOR
                    * getPastMonthsAverageForCategory(category.getId(), NUMBER_OF_MONTHS);
            if (Math.abs(suggestedAmount) != Math.abs(category.getAmount())) {
                SuggestionItem suggestionItem = new SuggestionItem();
                suggestionItem.setCategory(category);
                suggestionItem.setSuggested(suggestedAmount);
                suggestionList.add(suggestionItem);
            }
        }
        return suggestionList;
    }

    private double getPastMonthsAverageForCategory(long categoryId, int numberOfMonths) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        double result;
        
        DateTime now = new DateTime().withDayOfMonth(1).withTimeAtStartOfDay();
        String currentMonthStart = format.format(now.toDate());
        now = now.minusMonths(numberOfMonths);
        String priorMonthStart = format.format(now.toDate());

        Cursor queryResult = db.query(ENTRIES_TABLE, new String[] { "sum(" + ENTRIES_AMOUNT_KEY + ")" },
                "date(" + ENTRIES_DATE_KEY + ") between ? and ? and " + ENTRIES_CATEGORY_KEY + " = ?",
                new String[] { priorMonthStart, currentMonthStart, String.valueOf(categoryId) }, null, null,
                null);
        if (queryResult.moveToFirst()) {
            result = queryResult.getDouble(0);
        } else {
            result = 0.0;
        }
        return result;
    }

    private ArrayList<Category> getAllCategories() {
        Cursor categories = db.query(true, CATEGORIES_TABLE, allCategoryColumns, null, null, null, null,
                null, null, null);
        return createCategoryArrayListFromCursor(categories);
    }

}
