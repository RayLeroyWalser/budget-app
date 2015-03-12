package com.example.budget.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.budget.R;

public class InformationView extends Activity{
	
	TextView info;
	Intent incoming;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		Log.w("information", "myBudget - made it to the activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		
		info = (TextView) findViewById(R.id.informationText);
		
		incoming = getIntent();
		if( incoming.hasExtra("caller")){
			if( incoming.getStringExtra("caller").equals("overview")){
				info.setText("The home screen shows a summary of your expenses for the current month. It shows" +
						" totals of your estimated and actual expenses/incomes.\n\nThe progress bar shows what percentage" +
						" of your budget you have spent. This includes expenses only - it does not count your income.\n\n" +
						"It also shows how your expenses break down by category. Try long-pressing one of the items" +
						" in this list to see each individual item of that category.\n\nYou can press menu to access " +
						"\"My Profile\" and to add a single item, or view all past items.");
			}
			else if(incoming.getStringExtra("caller").equals("currentProfile")){
				info.setText("This is your profile, where you can enter estimates for what you will spend and how much you will " +
						"make in a month. You can enter amounts for different categories (food, rent, gas, etc) and these estimates " +
						"will be compared to actual expenses that you record during the month. \n\nDon't worry too much about accurate " +
						"estimates. I will average your estimates for past months and suggest values that my be more accurate (See the" +
						"\"Suggested\" tab at the top of the screen).\n\nTo get started, click on the \"Add New\"" +
						" tab at the top of the screen to add your first estimate.");
			}
			else if(incoming.getStringExtra("caller").equals("addNewProjection")){
				info.setText("This screen lets you enter the information for a new entry in your budget. Enter the appropriate information " +
						"and select save.\n\nDescription - a short description to help you remember what the entry is for.\n\nAmount - how much" +
						"the expense or income is worth\n\nCategory - how the entry should be classified (examples: Food, Entertainment, Rent, " +
						"ACME Paycheck)\n\nYou can use the \"Suggested\" tab to help you refine these entries and make them more accurate.");
			}
			else if(incoming.getStringExtra("caller").equals("listHistory")){
				info.setText("This screen shows the expenses or incomes that you have entered.\n\nYou can filter the entries by category" +
						" by selecting 'Menu'>'Set Filters', then checking the categories you would like to display.\n\nActive filters will " +
						"be displayed at the top of the screen. Clicking on a filter will remove its entries.\n\nAll filters can be cleared at once " +
						"by selecting 'Menu'>'Clear Filters'.\n\nAll filters will be reset when you navigate away from the list.");
			}
			else{
				info.setText("not sure where you came from");
			}
		}
	}
	
	public void onBackClick(View view){
		finish();
	}
}