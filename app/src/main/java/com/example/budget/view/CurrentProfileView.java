package com.example.budget.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.adapter.CategoryListAdapter;
import com.example.budget.layout.Category;
import com.example.budget.provider.CurrentProfileViewProvider;

public class CurrentProfileView extends AbstractBudgetView {

    private CurrentProfileViewProvider currentProfileViewProvider;
    private ArrayList<Category> myForecasts;
    private CategoryListAdapter aa;
    private int selected;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.currentprofile);
        currentProfileViewProvider = new CurrentProfileViewProvider(this);
        currentProfileViewProvider.open();
        initializeList();
    }

    private void initializeList() {
        ListView list;
        list = (ListView) findViewById(R.id.currentProfileList);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myForecasts = new ArrayList<Category>();
        aa = new CategoryListAdapter(this, myForecasts);
        list.setAdapter(aa);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        myForecasts.clear();
        myForecasts.addAll(currentProfileViewProvider.getCategories());
        aa.notifyDataSetChanged();
    }

    public void onEditClick(View view) {
        EditProfileView parent;
        parent = (EditProfileView) this.getParent();
        try {
            parent.setCurrentCategory(myForecasts.get(selected));
            parent.setEditExistingCategory(true);
            parent.setTab(2);
        } catch (Exception e) {
            
        }
    }

    public void onRemoveClick(View view) {
        try {
            currentProfileViewProvider.deleteCategory(myForecasts.get(selected).getId());
        } catch (Exception e) {

        }
        refreshList();
    }

    public void onHelpClick(View view) {
        Intent myIntent = new Intent(this, InformationView.class);
        myIntent.putExtra("caller", "currentProfile");
        startActivity(myIntent);
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return currentProfileViewProvider;
    }
}