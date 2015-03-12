package com.example.budget.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.budget.R;
import com.example.budget.layout.FilterListAdapterItem;

public class FilterListAdapter extends BaseAdapter{

	
	Activity context;
	final ArrayList<FilterListAdapterItem> categories;
	
	private LayoutInflater mInflater;
	
	public FilterListAdapter(Activity context, ArrayList<FilterListAdapterItem> cats){
		categories = cats;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return categories.size();
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
		CheckBox box;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		final int pos = position;
		
		if( convertView == null ){
			convertView = mInflater.inflate(R.layout.filterlistrow, null);
			holder = new ViewHolder();
			holder.box = (CheckBox) convertView.findViewById(R.id.filterRow);
			
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
			
		}
		
		holder.box.setChecked(categories.get(position).isUseFilter());
		holder.box.setText(categories.get(position).toString());
		
		holder.box.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				categories.get(pos).toggle();
				
			}
			
		});
		
		return convertView;
	}
	
}