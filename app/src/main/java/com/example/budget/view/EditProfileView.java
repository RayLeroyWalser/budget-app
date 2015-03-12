package com.example.budget.view;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.budget.R;
import com.example.budget.layout.Category;

public class EditProfileView extends TabActivity{
	
	private TabHost tabHost;
	private Category current;
	private boolean editExistingCategory;
	
	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.editprofile);
		
		Resources res = getResources();
		tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Reusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, CurrentProfileView.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("current").setIndicator("Current", res.getDrawable(R.drawable.binoculars)).setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, SuggestedProfileView.class);
	    spec = tabHost.newTabSpec("suggested").setIndicator("Suggested", res.getDrawable(R.drawable.lightbulb)).setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, EditCategoryView.class);
	    spec = tabHost.newTabSpec("add").setIndicator("Add New", res.getDrawable(R.drawable.plussign)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    setTab(1);
	    editExistingCategory = false;
	}
	
	public void setTab(int tabNum){
		tabHost.setCurrentTab(tabNum);
	}
	
	public void setCurrentCategory(Category category){
		current = category;
		editExistingCategory = true;
	}
	
	public Category getCurrentCategory(){
		editExistingCategory = false;
		return current;
	}
	
	public boolean isEditExistingCategory(){
		return editExistingCategory;
	}
	
	public void setEditExistingCategory(boolean edit){
	    editExistingCategory = edit;
	}
}