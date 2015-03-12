package com.example.budget.layout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;

import com.example.budget.adapter.BudgetDbAdapter;

public class Filter {

    private static final String END_DATE_EXTRA = "endDate";
    private static final String START_DATE_EXTRA = "startDate";
    private static final String CATEGORIES_INTENT_EXTRA = "filterCategoryIds";
    private ArrayList<Category> categories;
    private Date startRange;
    private Date endRange;

    public Filter() {
        categories = null;
    }

    public Filter addCategory(Category category) {
        if (categories == null) {
            categories = new ArrayList<Category>();
        }
        categories.add(category);
        return this;
    }

    public String buildWhereClause() {
        String whereClause = "";

        if (categories != null) {
            for (int i = 0; i < categories.size(); i++) {
                if (whereClause.length() > 0) {
                    whereClause += " OR ";
                }
                whereClause += BudgetDbAdapter.ENTRIES_CATEGORY_KEY + " = '" + categories.get(i).getId()
                        + "'";
            }
        }
        if (startRange != null) {
            if (whereClause.length() > 0) {
                whereClause += " AND ";
            }
            whereClause += BudgetDbAdapter.ENTRIES_DATE_KEY + " > '" + getDateFormat().format(startRange) + "'";
        }
        if (endRange != null) {
            if (whereClause.length() > 0) {
                whereClause += " AND ";
            }
            whereClause += BudgetDbAdapter.ENTRIES_DATE_KEY + " < '" + getDateFormat().format(endRange) + "'";
        }

        if (whereClause.length() == 0) {
            whereClause = null;
        }
        return whereClause;
    }

    public void setFilterItemFromIntent(Intent intent, BudgetDbAdapter provider) throws ParseException {
        if (intent.hasExtra(CATEGORIES_INTENT_EXTRA)) {
            long[] categoryIds = intent.getLongArrayExtra(CATEGORIES_INTENT_EXTRA);
            for (int i = 0; i < categoryIds.length; i++) {
                addCategory(provider.getCategoryForId(categoryIds[i]));
            }
        }
        if (intent.hasExtra(START_DATE_EXTRA)) {
            startRange = getDateFormat().parse(intent.getStringExtra(START_DATE_EXTRA));
        }
        if (intent.hasExtra(END_DATE_EXTRA)) {
            endRange = getDateFormat().parse(intent.getStringExtra(END_DATE_EXTRA));
        }
    }

    public Intent addFilterToIntent(Intent intent) {
        if (categories != null && !categories.isEmpty()) {
            intent.putExtra(CATEGORIES_INTENT_EXTRA, getArrayOfCategoryIds());
        }
        if (startRange != null) {
            intent.putExtra(START_DATE_EXTRA, getDateFormat().format(startRange));
        }
        if (endRange != null) {
            intent.putExtra(END_DATE_EXTRA, getDateFormat().format(endRange));
        }
        return intent;
    }

    public void clearAll() {
        if (categories != null) {
            categories.clear();
        }
    }

    public long[] getArrayOfCategoryIds() {
        long[] returnList = new long[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            returnList[i] = categories.get(i).getId();
        }
        return returnList;
    }

    public Date getStartRange() {
        return startRange;
    }

    public void setStartRange(Date startRange) {
        this.startRange = startRange;
    }

    public Date getEndRange() {
        return endRange;
    }

    public void setEndRange(Date endRange) {
        this.endRange = endRange;
    }

    private SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    }
}
