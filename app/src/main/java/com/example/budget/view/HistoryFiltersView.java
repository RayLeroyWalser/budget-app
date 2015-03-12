package com.example.budget.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.adapter.FilterListAdapter;
import com.example.budget.layout.Filter;
import com.example.budget.layout.FilterListAdapterItem;
import com.example.budget.provider.HistoryFiltersViewProvider;

public class HistoryFiltersView extends AbstractBudgetView {

    private ArrayList<FilterListAdapterItem> categories;
    private FilterListAdapter filterListAdapter;
    private HistoryFiltersViewProvider historyFiltersViewProvider;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.setfilters);
        historyFiltersViewProvider = new HistoryFiltersViewProvider(this);
        historyFiltersViewProvider.open();
        initializeListAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return historyFiltersViewProvider;
    }

    private void initializeListAdapter() {
        categories = new ArrayList<FilterListAdapterItem>();
        filterListAdapter = new FilterListAdapter(this, categories);
        list = (ListView) findViewById(R.id.setFiltersListView);
        list.setAdapter(filterListAdapter);
    }

    private void refreshList() {
        categories.clear();
        categories.addAll(historyFiltersViewProvider.getFilterListItemForAllCategories());
    }

    public void onSetClick(View view) {
        Intent startingIntent = new Intent();
        if (categories.size() > 0) {
            Filter filter = new Filter();
            for (FilterListAdapterItem item : categories) {
                if (item.isUseFilter()) {
                    filter.addCategory(item.getCategory());
                }
            }
            filter.addFilterToIntent(startingIntent);
        }
        setResult(Activity.RESULT_OK, startingIntent);
        finish();
    }

}