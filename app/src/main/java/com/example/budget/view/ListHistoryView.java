package com.example.budget.view;

import java.text.ParseException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.adapter.EntryListAdapter;
import com.example.budget.converter.BudgetEntryConverter;
import com.example.budget.layout.BudgetEntry;
import com.example.budget.layout.Filter;
import com.example.budget.provider.ListHistoryProvider;

public class ListHistoryView extends AbstractBudgetView {

    static final private int SET_FILTERS = Menu.FIRST;
    static final private int CLEAR_FILTERS = Menu.FIRST + 1;

    private ListHistoryProvider listHistoryProvider;
    private Filter filter = new Filter();
    private ArrayList<BudgetEntry> listOfEntries;
    private EntryListAdapter aa;
    private int selectedPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listhistory);
        listHistoryProvider = new ListHistoryProvider(this);
        listHistoryProvider.open();
        initializeListAdapter();
    }

    @Override
    public void onResume() {
        super.onRestart();
        setFilterFromIntent();
        refreshList();
    }

    private void setFilterFromIntent() {
        try {
            filter.setFilterItemFromIntent(getIntent(), listHistoryProvider);
        } catch (ParseException e) {
            Toast.makeText(this, "Unable to set filter.", Toast.LENGTH_LONG).show();
        }
    }

    private void initializeListAdapter() {
        ListView list;
        list = (ListView) findViewById(R.id.itemsListView);
        listOfEntries = listHistoryProvider.getEntriesUsingFilter(filter);
        aa = new EntryListAdapter(this, listOfEntries);
        aa.notifyDataSetChanged();

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setAdapter(aa);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem setFilters = menu.add(0, SET_FILTERS, Menu.NONE, R.string.set_filters_name);
        MenuItem clearFilters = menu.add(0, CLEAR_FILTERS, Menu.NONE, R.string.clear_filters_name);
        setFilters.setShortcut('1', 's');
        clearFilters.setShortcut('0', 'c');

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        LinearLayout container = (LinearLayout) findViewById(R.id.listHistoryDynamicFiltersContainer);

        switch (item.getItemId()) {
        case (SET_FILTERS): {
            container.removeAllViews();
            Intent myIntent = new Intent(this, HistoryFiltersView.class);
            startActivityForResult(myIntent, 1);
            return true;
        }
        case (CLEAR_FILTERS): {
            filter.clearAll();
            container.removeAllViews();
            refreshList();
            return true;
        }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("budgetDBAdapter", "budgetDBAdapter: returning from activity");

        if (requestCode == 1 && resultCode == RESULT_OK) {
            setFilterFromIntent();
        }
        super.onActivityResult(requestCode, resultCode, data);
        refreshList();
    }

    private void refreshList() {
        listOfEntries.clear();
        listOfEntries.addAll(listHistoryProvider.getEntriesUsingFilter(filter));
        aa.notifyDataSetChanged();
    }

    public void onRemoveClick(View view) {
        try {
            listHistoryProvider.deleteEntry(listOfEntries.get(selectedPosition).getId());
            refreshList();
        } catch (Exception e) {

        }
    }

    public void onViewClick(View view) {
        try {
            Intent intent = new Intent(ListHistoryView.this, AddSingleEntryView.class);
            BudgetEntryConverter.addEntryToIntent(intent, listOfEntries.get(selectedPosition));
            startActivity(intent);
        } catch (Exception e) {

        }
    }

    public void onHelpClick(View view) {
        Intent myIntent = new Intent(this, InformationView.class);
        myIntent.putExtra("caller", "listHistory");
        startActivity(myIntent);
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return listHistoryProvider;
    }

}