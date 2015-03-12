package com.example.budget.view;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.converter.BudgetEntryConverter;
import com.example.budget.layout.BudgetEntry;
import com.example.budget.layout.Category;
import com.example.budget.provider.AddSingleEntryProvider;

public class AddSingleEntryView extends AbstractBudgetView {

    private AddSingleEntryProvider addSingleEntryProvider;
    private EditText descriptionEntryField;
    private EditText newCategoryEntryField;
    private Spinner categorySpinner;
    private DatePicker datePicker;
    private EditText amountEntryField;
    private TextView spinnerPromptText;
    private Button returnSpinner;

    private BudgetEntry entry;
    private ArrayAdapter<Category> categorySpinnerAdapter;
    private ArrayList<Category> categories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsingle);

        getViewHandles();
        addSingleEntryProvider = new AddSingleEntryProvider(this);
        addSingleEntryProvider.open();
        initializeCategorySpinner();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCategoryList();
        entry = getEntryFromIntent();
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return addSingleEntryProvider;
    }

    private BudgetEntry getEntryFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(BudgetEntryConverter.AMOUNT_EXTRA_NAME)) {
            try {
                entry = BudgetEntryConverter.getEntryFromIntent(intent, addSingleEntryProvider);
                setEntryFieldsToEntry(entry);
            } catch (ParseException e) {
                Toast.makeText(this, "Unable to retrieve entry to edit.", Toast.LENGTH_LONG).show();
                entry = null;
            }
        } else {
            entry = null;
        }
        return entry;
    }

    private void setEntryFieldsToEntry(BudgetEntry entryToSet) {
        descriptionEntryField.setText(entryToSet.getDescription());
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().equals(entryToSet.getCategory().getName())) {
                categorySpinner.setSelection(i);
            }
        }
        String dateString = entryToSet.getDateAsString();
        datePicker
                .updateDate(Integer.valueOf(dateString.substring(0, 4)),
                        Integer.valueOf(dateString.substring(5, 7)) - 1,
                        Integer.valueOf(dateString.substring(8, 10)));
        amountEntryField.setText(String.valueOf(entryToSet.getAmount()));
    }

    private void refreshCategoryList() {
        categories.clear();
        categories.addAll(addSingleEntryProvider.getAllCategories());
        categories.add(Category.getNewCategory());
        categorySpinnerAdapter.notifyDataSetChanged();
    }

    private void initializeCategorySpinner() {
        categories = addSingleEntryProvider.getAllCategories();
        categorySpinnerAdapter = new ArrayAdapter<Category>(this, R.layout.spinneritemview, categories);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (categorySpinner.getSelectedItem().toString().equals(Category.NEW_CATEGORY)) {
                    categorySpinner.setVisibility(View.INVISIBLE);
                    spinnerPromptText.setVisibility(View.INVISIBLE);
                    newCategoryEntryField.setVisibility(View.VISIBLE);
                    returnSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void getViewHandles() {
        descriptionEntryField = (EditText) findViewById(R.id.enterExpense);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        datePicker = (DatePicker) findViewById(R.id.addSingleDatePicker);
        amountEntryField = (EditText) findViewById(R.id.enterSingleAmount);
        newCategoryEntryField = (EditText) findViewById(R.id.newCategory);
        spinnerPromptText = (TextView) findViewById(R.id.categorySelectPrompt);
        returnSpinner = (Button) findViewById(R.id.backToSpinnerButton);
    }

    public void onAddClick(View view) {
        try {
            addCategoryToDatabaseIfNew();
            entry = setEntryFieldsFromInput();
            if (entry.getId() == 0) {
                addSingleEntryProvider.insertEntry(entry);
            } else {
                addSingleEntryProvider.updateEntry(entry);
            }
            Toast addEntrySuccessfulToast = Toast.makeText(getBaseContext(), "Entry saved successfully!",
                    Toast.LENGTH_SHORT);
            addEntrySuccessfulToast.show();
            finish();
        } catch (Exception e) {
            Toast addEntryErrorToast = Toast.makeText(getBaseContext(),
                    "Unable to add entry.  Check inputs and try again.", Toast.LENGTH_SHORT);
            addEntryErrorToast.show();
        }
    }

    private void addCategoryToDatabaseIfNew() {
        if (newCategoryEntryField.getVisibility() == View.VISIBLE) {
            Category category = new Category();
            category.setName(newCategoryEntryField.getText().toString());
            addSingleEntryProvider.insertCategory(category);
        }
    }

    private BudgetEntry setEntryFieldsFromInput() {
        String inputDesc = descriptionEntryField.getText().toString();
        float inputAmount = Float.valueOf(amountEntryField.getText().toString());
        Calendar calendar = Calendar.getInstance();
        String categoryName;
        if (categorySpinner.getVisibility() == View.VISIBLE) {
            categoryName = categorySpinner.getSelectedItem().toString();
        } else {
            categoryName = newCategoryEntryField.getText().toString();
        }
        if( entry == null ){
            entry = new BudgetEntry();
        }
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.YEAR, datePicker.getYear());
        entry.setAmount(inputAmount);
        entry.setCategory(addSingleEntryProvider.getCategoryForName(categoryName));
        entry.setDescription(inputDesc);
        entry.setDate(calendar.getTime());
        return entry;
    }

    public void onCancelClick(View view) {
        finish();
    }

    public void onBackClick(View view) {
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        newCategoryEntryField = (EditText) findViewById(R.id.newCategory);
        spinnerPromptText = (TextView) findViewById(R.id.categorySelectPrompt);
        returnSpinner = (Button) findViewById(R.id.backToSpinnerButton);

        returnSpinner.setVisibility(View.INVISIBLE);
        newCategoryEntryField.setVisibility(View.INVISIBLE);
        categorySpinner.setVisibility(View.VISIBLE);
        spinnerPromptText.setVisibility(View.VISIBLE);
        categorySpinner.setSelection(0);
    }

    public void onSignClick(View view) {
        try {
            double amount = Double.parseDouble(amountEntryField.getText().toString());
            amountEntryField.setText(String.valueOf(amount * -1));
        } catch (Exception e) {
            Toast conversionError = Toast
                    .makeText(
                            getBaseContext(),
                            "Unable to toggle sign due to conversion error.  Check that the amount field is a number.",
                            Toast.LENGTH_LONG);
            conversionError.show();
        }
    }

    public BudgetEntry getEntry() {
        return entry;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
}