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

        return amount;
    }

    public ArrayList<OverviewItem> getTotalsByCategoryForMonth(int month) {
        ArrayList<OverviewItem> entryList = new ArrayList<OverviewItem>();

        entryList = getCategoriesAndProjections(entryList);
        entryList = getEntryAmountsForMonth(entryList, month);

        return entryList;
    }

    private ArrayList<OverviewItem> getCategoriesAndProjections(ArrayList<OverviewItem> list) {
        Cursor queryResult = db.query(CATEGORIES_TABLE, new String[] { CATEGORIES_ID_KEY,
                CATEGORIES_NAME_KEY, CATEGORIES_AMOUNT_KEY, CATEGORIES_IS_ACTIVE_KEY }, null, null, null,
                null, null);

        return createOverviewItemArrayListFromCursor(queryResult);
    }

    public ArrayList<OverviewItem> createOverviewItemArrayListFromCursor(Cursor cursor) {
        ArrayList<OverviewItem> resultList = null;
        if (cursor.moveToFirst()) {
            resultList = new ArrayList<OverviewItem>();
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

    private ArrayList<OverviewItem> getEntryAmountsForMonth(ArrayList<OverviewItem> list, int month) {
        String queryString = "SELECT SUM(" + ENTRIES_AMOUNT_KEY + ") FROM " + ENTRIES_TABLE + " WHERE "
                + ENTRIES_CATEGORY_KEY + " = ? AND + strftime('%m', " + ENTRIES_DATE_KEY + ") = '" + month
                + "';";
        for (int i = 0; i < list.size(); i++) {
            Cursor queryResult = db.rawQuery(queryString,
                    new String[] { String.valueOf(list.get(i).categoryId) });
            if (queryResult.moveToFirst()) {
                double temp = queryResult.getDouble(0);
                list.get(i).actual = temp;
            } else {
                list.get(i).actual = 0.0;
            }
        }

        return list;
    }
}
