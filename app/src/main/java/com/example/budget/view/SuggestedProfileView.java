package com.example.budget.view;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.budget.R;
import com.example.budget.adapter.SuggestionListViewAdapter;
import com.example.budget.layout.Category;
import com.example.budget.layout.SuggestionItem;
import com.example.budget.provider.SuggestedProfileViewProvider;

public class SuggestedProfileView extends Activity {

    private SuggestedProfileViewProvider suggestedProfileViewProvider;

    private SuggestionListViewAdapter suggestionListViewAdapter;
    private ArrayList<SuggestionItem> suggestionList;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.suggestedprofile);

        suggestedProfileViewProvider = new SuggestedProfileViewProvider(this);
        suggestedProfileViewProvider.open();
        initializeListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onDestroy() {
        suggestedProfileViewProvider.close();
        super.onDestroy();
    }

    private void initializeListView() {
        ListView list = (ListView) findViewById(R.id.suggestedProfileSuggestions);
        suggestionList = new ArrayList<SuggestionItem>();
        suggestionListViewAdapter = new SuggestionListViewAdapter(this, suggestionList);
        list.setAdapter(suggestionListViewAdapter);
    }

    private void refreshList() {
        TextView message;
        message = (TextView) findViewById(R.id.suggestedProfileMessage);
        suggestionList.clear();
        suggestionList.addAll(suggestedProfileViewProvider.getSuggestionList());
        suggestionListViewAdapter.notifyDataSetChanged();

        if (suggestionList.size() == 0) {
            message.setText("All of your estimates match my suggestions! Check back at the beginning of "
                    + "next month - I might have some then.");
            message.setVisibility(View.VISIBLE);
        } else{
            message.setVisibility(View.GONE);
        }
    }

    public void onSaveClick(View view) {
        int i = 0;
        for (i = 0; i < suggestionList.size(); i++) {
            if (suggestionList.get(i).isUseNew()) {
                Category category = suggestionList.get(i).getCategory();
                category.setAmount(suggestionList.get(i).getSuggested());
                suggestedProfileViewProvider.updateCategory(category);
            }
        }
        refreshList();
    }

}