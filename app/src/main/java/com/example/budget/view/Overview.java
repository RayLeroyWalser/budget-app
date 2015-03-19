package com.example.budget.view;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.adapter.OverviewListAdapter;
import com.example.budget.layout.Filter;
import com.example.budget.layout.OverviewItem;
import com.example.budget.provider.OverviewProvider;

public class Overview extends AbstractBudgetView {

    public static final String TAG = "myBudget";
    public static final String BACKUP_PREFS = "backupsPreferences";

    private static final int LIST_HISTORY = Menu.FIRST;
    private static final int EDIT_PROFILE = Menu.FIRST + 1;
    private static final int ADD_SINGLE_ENTRY = Menu.FIRST + 2;
    private static final int BACKUP = Menu.FIRST + 3;

    private OverviewProvider overviewProvider;
    private ArrayList<OverviewItem> overviewItems;
    private OverviewListAdapter aa;

    TextView incomeActual;
    TextView incomeExpected;
    TextView expenseActual;
    TextView expenseExpected;
    TextView barLabel;
    ProgressBar bar;
    ImageButton helpButton;

    SharedPreferences backupPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overviewProvider = new OverviewProvider(this);
        overviewProvider.open();
        setContentView();
        getViewHandles();
        initializeListView();
    }

    @Override
    public void onResume() {
        if (overviewProvider.hasEntries()) {
            refreshSummaryData();
        }
        super.onResume();
    }

    private void initializeListView() {
        ListView list = (ListView) findViewById(R.id.byCategoryList);
        overviewItems = new ArrayList<OverviewItem>();

        aa = new OverviewListAdapter(this, overviewItems);
        list.setAdapter(aa);
        list.setOnItemLongClickListener(new OverviewItemOnClickListener());
    }

    public void getViewHandles() {
        helpButton = (ImageButton) findViewById(R.id.overviewInfoButton);
        incomeActual = (TextView) findViewById(R.id.dataMonthActual);
        incomeExpected = (TextView) findViewById(R.id.dataMonthProjected);
        expenseActual = (TextView) findViewById(R.id.dataActualTodate);
        expenseExpected = (TextView) findViewById(R.id.dataTodateProjected);
        barLabel = (TextView) findViewById(R.id.overviewProgressBarLabel);
        bar = (ProgressBar) findViewById(R.id.overviewProgressBar);
    }

    private void setContentView() {
//        if (overviewProvider.hasEntries()) {
            setContentView(R.layout.main);
//        } else {
//            setContentView(R.layout.noentriesoverview);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem editProfile = menu.add(0, EDIT_PROFILE, Menu.NONE, R.string.edit_profile);
        MenuItem previousMonth = menu.add(0, ADD_SINGLE_ENTRY, Menu.NONE, R.string.enter_single_expense);
        MenuItem listHistory = menu.add(0, LIST_HISTORY, Menu.NONE, R.string.history_name);
        MenuItem backup = menu.add(0, BACKUP, Menu.NONE, R.string.backup_name);
        listHistory.setShortcut('2', 'h');
        editProfile.setShortcut('1', 'c');
        previousMonth.setShortcut('0', 's');
        backup.setShortcut('3', 'b');

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
        case (EDIT_PROFILE): {
            onEditProfileClick(null);
            return true;
        }
        case (LIST_HISTORY): {
            onShowHistoryClick(null);
            return true;
        }
        case (ADD_SINGLE_ENTRY): {
            onAddClick(null);
            return true;
        }
        case (BACKUP): {
            onBackupClick(null);
            return true;
        }
        }
        return false;
    }

    private void refreshSummaryData() {
        int currentMonth = new DateTime().monthOfYear().get();

        double actualIncome = overviewProvider.getActualIncomeForMonth(currentMonth);
        incomeActual.setText(NumberFormat.getCurrencyInstance().format(actualIncome));

        double actualExpense = overviewProvider.getActualExpenseForMonth(currentMonth);
        expenseActual.setText(NumberFormat.getCurrencyInstance().format(actualExpense));

        double estimatedIncome = overviewProvider.getProjectedMonthlyIncome();
        incomeExpected.setText(NumberFormat.getCurrencyInstance().format(estimatedIncome));

        double estimatedExpense = overviewProvider.getProjectedMonthlyExpense();
        expenseExpected.setText(NumberFormat.getCurrencyInstance().format(estimatedExpense));

        updateProgressBar(actualExpense, estimatedExpense);

        refreshListData();
    }

    private void updateProgressBar(double actualExpense, double estimatedExpense) {
        if (estimatedExpense != 0) {
            bar.setVisibility(View.VISIBLE);
            bar.setProgress((int) (100 * actualExpense / estimatedExpense));
            barLabel.setText("You have spent "
                    + String.valueOf((int) (100 * actualExpense / estimatedExpense))
                    + "% of your estimated budget.");
        } else {
            bar.setVisibility(View.GONE);
            barLabel.setText("You haven't entered any estimates! Go to \"My Profile\" to enter how much you expect to spend each month.");
        }
    }

    private void refreshListData() {
        overviewItems.clear();
        overviewItems
                .addAll(overviewProvider.getTotalsByCategoryForMonth(new DateTime().monthOfYear().get()));
        Collections.sort(overviewItems);
        overviewItems.add(0,OverviewItem.defaultItem);
        aa.notifyDataSetChanged();
    }

    public void onAddClick(View view) {
        Intent myIntent = new Intent(this, AddSingleEntryView.class);
        startActivity(myIntent);
    }

    public void onEditProfileClick(View view) {
        Intent myIntent = new Intent(this, EditProfileView.class);
        if (!overviewProvider.hasEntries()) {
            myIntent.putExtra("tabToShow", 2);
        }
        startActivity(myIntent);
    }

    public void onHelpClick(View view) {
        Intent myIntent = new Intent(this, InformationView.class);
        myIntent.putExtra("caller", "overview");
        startActivity(myIntent);
    }

    public void showPreviousMonth(View view) {
        Intent myIntent = new Intent(this, PreviousMonthView.class);
        startActivity(myIntent);
    }

    public void onShowHistoryClick(View view) {
        Intent listHistoryIntent = new Intent(this, ListHistoryView.class);
        startActivity(listHistoryIntent);
    }

    private void onBackupClick(View view) {
        Intent myIntent = new Intent(this, BackupView.class);
        startActivity(myIntent);
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return overviewProvider;
    }

    private class OverviewItemOnClickListener implements OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
            Intent intent = new Intent(getBaseContext(), ListHistoryView.class);
            Filter filter = new Filter();
            filter.addCategory(overviewProvider.getCategoryForName(overviewItems.get(pos).categoryName));
            filter.setStartRange(new DateTime().withTimeAtStartOfDay().withDayOfMonth(1).toDate());
            filter.setEndRange(new DateTime().withTimeAtStartOfDay().withDayOfMonth(1).plusMonths(1).toDate());
            filter.addFilterToIntent(intent);
            startActivity(intent);

            return true;
        }
    }
}