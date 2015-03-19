package com.example.budget.provider;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.OverviewItem;

public class OverviewProvider extends BudgetDbAdapter {

    public OverviewProvider(Context context) {
        super(context);
    }

    public double getActualIncomeForMonth(int month) {
        double amount = 0.0;
        String queryString = "SELECT SUM(" + ENTRIES_AMOUNT_KEY + ") FROM " + ENTRIES_TABLE
                + " WHERE strftime('%m', " + ENTRIES_DATE_KEY + ") = ? and " + ENTRIES_AMOUNT_KEY + " > 0;";

        Cursor queryResult = db.rawQuery(queryString, new String[] { String.valueOf(month) });
        if (queryResult.moveToFirst()) {
            amount = queryResult.getDouble(0);
        }
        queryResult.close();

        return amount;
    }

    public double getActualExpenseForMonth(int month) {
        double amount = 0.0;
        String queryString = "SELECT SUM(" + ENTRIES_AMOUNT_KEY + ") FROM " + ENTRIES_TABLE
                + " WHERE strftime('%m', " + ENTRIES_DATE_KEY + ") = ? and " + ENTRIES_AMOUNT_KEY + " < 0;";

        Cursor queryResult = db.rawQuery(queryString, new String[] { String.valueOf(month) });
        if (queryResult.moveToFirst()) {
            amount = queryResult.getDouble(0);
        }
        queryResult.close();

        return amount;
    }

    public double getProjectedMonthlyIncome() {
        double amount = 0.0;
        String queryString = "SELECT SUM(" + CATEGORIES_AMOUNT_KEY + ") FROM " + CATEGORIES_TABLE + " WHERE "
                + CATEGORIES_AMOUNT_KEY + " > 0;";

        Cursor queryResult = db.rawQuery(queryString, null);
        if (queryResult.moveToFirst()) {
            amount = queryResult.getDouble(0);
        }
        queryResult.close();

        return amount;
    }

    public double getProjectedMonthlyExpense() {
        double amount = 0.0;
        String queryString = "SELECT SUM(" + CATEGORIES_AMOUNT_KEY + ") FROM " + CATEGORIES_TABLE + " WHERE "
                + CATEGORIES_AMOUNT_KEY + " < 0;";

        Cursor queryResult = db.rawQuery(queryString, null);
        if (queryResult.moveToFirst()) {
            amount = queryResult.getDouble(0);
        }
        queryResult.close();

        return amount;
    }

    public ArrayList<OverviewItem> getTotalsByCategoryForMonth(int month) {
        ArrayList<OverviewItem> entryList = new ArrayList<>();

        entryList.addAll(getCategoriesAndProjections());
        for (OverviewItem item : entryList) {
            item.actual = getActualAmountForMonthAndCategory(month, item.categoryId);
        }

        return entryList;
    }

    private ArrayList<OverviewItem> getCategoriesAndProjections() {
        Cursor queryResult = db.query(CATEGORIES_TABLE, new String[] { CATEGORIES_ID_KEY,
                CATEGORIES_NAME_KEY, CATEGORIES_AMOUNT_KEY, CATEGORIES_IS_ACTIVE_KEY }, null, null, null,
                null, null);

        return createOverviewItemArrayListFromCursor(queryResult);
    }

    public ArrayList<OverviewItem> createOverviewItemArrayListFromCursor(Cursor cursor) {
        ArrayList<OverviewItem> resultList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                OverviewItem overviewItem = new OverviewItem();
                overviewItem.categoryId = cursor.getInt(BudgetDbAdapter.CATEGORIES_ID_COLUMN);
                overviewItem.categoryName = cursor.getString(BudgetDbAdapter.CATEGORIES_NAME_COLUMN);
                overviewItem.projection = cursor.getDouble(BudgetDbAdapter.CATEGORIES_AMOUNT_COLUMN);
                resultList.add(overviewItem);
            } while (cursor.moveToNext());
        }
        return resultList;
    }

    private double getActualAmountForMonthAndCategory(int month, int categoryId) {
        double result;
        String queryString = "SELECT SUM(" + ENTRIES_AMOUNT_KEY + ") FROM " + ENTRIES_TABLE + " WHERE "
                + ENTRIES_CATEGORY_KEY + " = ? AND strftime('%m', " + ENTRIES_DATE_KEY + ") = '" + String.format("%02d", month)
                + "';";
        Cursor queryResult = db.rawQuery(queryString,
                new String[]{String.valueOf(categoryId)});
        if (queryResult.moveToFirst()) {
            result = queryResult.getDouble(0);
        } else {
            result = 0.0;
        }
        queryResult.close();

        return result;
    }
}
