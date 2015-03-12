package com.example.budget.view;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.component.PreviousMonthPieChart;
import com.example.budget.layout.PieChartObject;

public class PreviousMonthView extends Activity{
	
	public static final String TAG = "previousMonthView";
	public static String[] monthConversion = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	BudgetDbAdapter db;
	Cursor all;
	
	ListView pieList;
	ArrayAdapter<PieChartObject> aa;
	ArrayList<PieChartObject> totalsForPie;
	
	ArrayList<String> fromYears;
	ArrayList<String> toYears;
	ArrayList<String> fromMonths;
	ArrayList<String> toMonths;
	ArrayAdapter<String> aaFromYear;
	ArrayAdapter<String> aaToYear;
	ArrayAdapter<String> aaFromMonth;
	ArrayAdapter<String> aaToMonth;
	
	Cursor catCursor;
	Cursor actualCursor;
	PreviousMonthPieChart pie;
	
	LayoutParams l;
	RelativeLayout pieContainer;
	
	Spinner fromMonthSpinner;
	Spinner fromYearSpinner;
	Spinner toMonthSpinner;
	Spinner toYearSpinner;
	TextView netIncomeTextView;
	
	Context context;
	
	TextView debug;
	
	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.previousmonth);
		
		//final Date date = new Date();
		
		db = new BudgetDbAdapter(this);
		db.open();
		
		totalsForPie = new ArrayList<PieChartObject>();
		
		fromYears = new ArrayList<String>();
		toYears = new ArrayList<String>();
		fromMonths = new ArrayList<String>();
		toMonths = new ArrayList<String>();
		
		aaFromYear = new ArrayAdapter<String>(this, R.layout.spinneritemview, fromYears);
		aaToYear = new ArrayAdapter<String>(this, R.layout.spinneritemview, toYears);
		aaFromMonth = new ArrayAdapter<String>(this, R.layout.spinneritemview, fromMonths);
		aaToMonth = new ArrayAdapter<String>(this, R.layout.spinneritemview, toMonths);
		
        
        //aa = new ArrayAdapter<PieChartObject>(this, android.R.layout.simple_list_item_1, android.R.id.text1, totalsForPie);
        //pieList = (ListView) findViewById(R.id.previousMonthByCategoryList);
        //pieList.setAdapter(aa);
        
        pie = (PreviousMonthPieChart) findViewById(R.id.previousMonthPieChart);
        pieContainer = (RelativeLayout) findViewById(R.id.previousMonthListviewContainer);
        netIncomeTextView = (TextView) findViewById(R.id.previousMonthNetIncomeTextView);
        debug = (TextView) findViewById(R.id.previousMonthContainerTitle);
        
        
        fromYearSpinner = (Spinner) findViewById(R.id.previousMonthFromYearSpinner);
        fromYearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setUpMonthSpinner("from", fromYearSpinner.getSelectedItem().toString());
                populateList();
                pie.invalidate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        
        toYearSpinner = (Spinner) findViewById(R.id.previousMonthToYearSpinner);
        toYearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setUpMonthSpinner("to", toYearSpinner.getSelectedItem().toString());
                populateList();
        		toMonthSpinner.setSelection(toMonths.size()-1);
                pie.invalidate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        
        fromMonthSpinner = (Spinner) findViewById(R.id.previousMonthFromMonthSpinner);
        fromMonthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                populateList();
                pie.invalidate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        
        toMonthSpinner = (Spinner) findViewById(R.id.previousMonthToMonthSpinner);
        toMonthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                populateList();
                pie.invalidate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        
		/*all = db.getWithFilters(null,new String[]{"111"},null,-1,-1);
		
		if( all.moveToFirst() ){
			do{
				String description = all.getString(budgetDBAdapter.DESCRIPTION_COLUMN);
    			String category = all.getString(budgetDBAdapter.CATEGORY_COLUMN);
    			float amount = all.getFloat(budgetDBAdapter.AMOUNT_COLUMN);
    			long date = all.getLong(budgetDBAdapter.DATE_COLUMN);
    			int sign = all.getInt(budgetDBAdapter.SIGN_COLUMN);
    			int type = all.getInt(budgetDBAdapter.TYPE_COLUMN);
    			int id = all.getInt(budgetDBAdapter.ID_COLUMN);
    			int month = all.getInt(budgetDBAdapter.MONTH_COLUMN);
    			int year = all.getInt(budgetDBAdapter.YEAR_COLUMN);
    			
    			_date.setTime(all.getLong(budgetDBAdapter.DATE_COLUMN));
    			
    			
    			
    			budgetEntry next = new budgetEntry(description, category, amount, sign, id, date, type,month,year);
    			items.add(next);
			}
			while( all.moveToNext() );
		}
		_date.setTime(items.get(1).getDate());
		
		debug.setText(String.valueOf(items.size()));*/
        setupYearSpinners();
		toYearSpinner.setSelection(toYears.size()-1);
		toMonthSpinner.setSelection(toMonths.size()-1);
        //populateList();
        
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		db.close();
	}
	
	private void setupYearSpinners(){
		//TODO rewrite
	}
	
	public void onPieClick(View view){
		//totalsForPie.remove(0);
		pie.invalidate();
	}
	
	private void setUpMonthSpinner(String which, String _year){
		//TODO rewrite
	}
	
	private void populateList(){
		//TODO rewrite
    }
    
    private long getBeginningTime(){
    	//Log.w(TAG,"making beginning time");
    	Date beginning = new Date();
    	
    	int year = Integer.parseInt(fromYearSpinner.getSelectedItem().toString());
    	int month = getNumberFromMonth(fromMonthSpinner.getSelectedItem().toString());
    	
    	beginning.setDate(1);
    	beginning.setHours(0);
    	beginning.setMinutes(0);
    	beginning.setSeconds(0);
    	beginning.setMonth(month);
    	beginning.setYear(year-1900);
  
    	//netIncomeTextView.setText(beginning.toLocaleString());
    	//Log.w(TAG,"Beginning time: " +beginning.toLocaleString());
    	return beginning.getTime();
    }
    
    private long getEndTime(){
    	//Log.w(TAG,"making end time");
    	Date end = new Date();
    	
    	int year = Integer.parseInt(toYearSpinner.getSelectedItem().toString());
    	int month = getNumberFromMonth(toMonthSpinner.getSelectedItem().toString());
    	
    	end.setDate(1);
    	end.setHours(0);
    	end.setMinutes(0);
    	end.setSeconds(0);
    	end.setMonth(month+1);
    	end.setYear(year-1900);
  
    	//netIncomeTextView.setText(end.toLocaleString() + ": month-" + String.valueOf(month));
    	//Log.w(TAG,"End time: " +end.toLocaleString());
    	//Log.w(TAG,"made beginning time");
    	return end.getTime();
    }
    
    public int getNumberFromMonth(String month){
    	if( month.equals("Jan") ){
    		return 0;
    	}
    	else if( month.equals("Feb") ){
    		return 1;
    	}
    	else if( month.equals("Mar") ){
    		return 2;
    	}
    	else if( month.equals("Apr") ){
    		return 3;
    	}
    	else if( month.equals("May") ){
    		return 4;
    	}
    	else if( month.equals("Jun") ){
    		return 5;
    	}
    	else if( month.equals("Jul") ){
    		return 6;
    	}
    	else if( month.equals("Aug") ){
    		return 7;
    	}
    	else if( month.equals("Sep") ){
    		return 8;
    	}
    	else if( month.equals("Oct") ){
    		return 9;
    	}
    	else if( month.equals("Nov") ){
    		return 10;
    	}
    	else if( month.equals("Dec") ){
    		return 11;
    	}
    	return -1;
    }
    
    
}