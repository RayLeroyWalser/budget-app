package com.example.budget.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.budget.R;
import com.example.budget.layout.BudgetEntry;

public class EntryListAdapter extends BaseAdapter{

	int COLOR_DARK_GREEN = 0xff007700;
	Activity context;
	ArrayList<BudgetEntry> list;
	
	private LayoutInflater mInflater;
	
	public EntryListAdapter(Activity context, ArrayList<BudgetEntry> entryList){
		list = entryList;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder{
		TextView textViewDescription;
		TextView textViewAmount;
		TextView textViewDate;
		TextView textViewCategory;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if( convertView == null ){
			convertView = mInflater.inflate(R.layout.customlistviewrow, null);
			holder = new ViewHolder();
			holder.textViewAmount = (TextView) convertView.findViewById(R.id.customListAmount);
			holder.textViewDate = (TextView) convertView.findViewById(R.id.customListDate);
			holder.textViewDescription = (TextView) convertView.findViewById(R.id.customListDescription);
			holder.textViewCategory = (TextView) convertView.findViewById(R.id.customListCategory);
			
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
			
		}
		
		holder.textViewAmount.setText(list.get(position).getAmountAsString());
		if( list.get(position).getAmount() <= 0 ){
			holder.textViewAmount.setTextColor(Color.RED);
		}
		else{
			holder.textViewAmount.setTextColor(COLOR_DARK_GREEN);
		}
		
		if( list.get(position).getDate() != null){
			holder.textViewDate.setText(list.get(position).getDateAsString());
		}
		else{
			holder.textViewDate.setText("");
		}
		holder.textViewDescription.setText(list.get(position).getDescription());
		holder.textViewCategory.setText(list.get(position).getCategory().toString());
		
		return convertView;
	}
	
}