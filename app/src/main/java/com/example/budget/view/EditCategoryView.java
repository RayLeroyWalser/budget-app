package com.example.budget.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.Category;
import com.example.budget.provider.EditCategoryViewProvider;

public class EditCategoryView extends AbstractBudgetView {

    private EditCategoryViewProvider editCategoryViewProvider;
    private EditProfileView parentView;
    private EditText categoryEntry;
    private EditText amountEntry;
    private Category category;
    private CheckBox isActiveCheckBox;

    boolean isEdit = false;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.addprojection);
        editCategoryViewProvider = new EditCategoryViewProvider(this);
        editCategoryViewProvider.open();
        getViewHandles();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateFieldsIfEditing();
    }

    private void populateFieldsIfEditing() {
        if (parentView.isEditExistingCategory()) {
            category = parentView.getCurrentCategory();
            categoryEntry.setText(category.getName());
            amountEntry.setText(String.valueOf(category.getAmount()));
            isActiveCheckBox.setChecked(category.isActive());
            isEdit = true;
        } else {
            category = new Category();
        }
    }

    public void onSaveClick(View view) {
        try {
            category.setName(categoryEntry.getText().toString());
            category.setAmount(Double.valueOf(amountEntry.getText().toString()));
            category.setActive(isActiveCheckBox.isChecked());

            if (isEdit) {
                editCategoryViewProvider.updateCategory(category);
            } else {
                editCategoryViewProvider.insertCategoryIfNotExists(category);
            }
            parentView.setTab(0);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to update database.", Toast.LENGTH_LONG).show();
        }
    }

    public void onToggleSignClick(View view) {
        try {
            double amount = Double.valueOf(amountEntry.getText().toString());
            amountEntry.setText(String.valueOf(amount * -1));
        } catch (Exception e) {
            Toast.makeText(this, "Unable to toggle sign: non-numeric value entered into the amount field.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClearClick(View view) {
        amountEntry.setText("");
        categoryEntry.setText("");
        isActiveCheckBox.setChecked(false);
        isEdit = false;
    }

    public void onHelpClick(View view) {
        Intent myIntent = new Intent(this, InformationView.class);
        myIntent.putExtra("caller", "addNewProjection");
        startActivity(myIntent);
    }

    public void getViewHandles() {
        amountEntry = (EditText) findViewById(R.id.editProjectionAmountEntry);
        categoryEntry = (EditText) findViewById(R.id.editProjectionCategoryText);
        isActiveCheckBox = (CheckBox) findViewById(R.id.editProjectionActiveCheckbox);
        parentView = (EditProfileView) this.getParent();
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return editCategoryViewProvider;
    }
}