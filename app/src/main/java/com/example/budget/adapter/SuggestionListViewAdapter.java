package com.example.budget.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.budget.R;
import com.example.budget.layout.SuggestionItem;

public class SuggestionListViewAdapter extends BaseAdapter {

    int COLOR_DARK_GREEN = 0xff007700;
    Activity context;
    ArrayList<SuggestionItem> list;

    private LayoutInflater mInflater;

    public SuggestionListViewAdapter(Activity context, ArrayList<SuggestionItem> entryList) {
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

    static class ViewHolder {
        TextView textViewDescription;
        TextView textViewCurrentAmount;
        TextView textViewSuggestedAmount;
        TextView textViewCategory;
        TextView currentPointer;
        TextView suggestionPointer;
        Button toggleButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        final int pos = position;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.suggestionlistrow, null);
            holder = new ViewHolder();
            holder.textViewSuggestedAmount = (TextView) convertView
                    .findViewById(R.id.suggestionListSuggestedAmount);
            holder.textViewCurrentAmount = (TextView) convertView
                    .findViewById(R.id.suggestionListCurrentAmount);
            holder.textViewDescription = (TextView) convertView.findViewById(R.id.suggestionListDescription);
            holder.textViewCategory = (TextView) convertView.findViewById(R.id.suggestionListCategory);
            holder.toggleButton = (Button) convertView.findViewById(R.id.suggestionListButton);
            holder.currentPointer = (TextView) convertView.findViewById(R.id.suggestionListCurrentPointer);
            holder.suggestionPointer = (TextView) convertView
                    .findViewById(R.id.suggestionListSuggestedPointer);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.textViewCurrentAmount.setText(list.get(position).getCategory().getAmountAsString());
        if (list.get(position).getCategory().getAmount() < 0) {
            holder.textViewCurrentAmount.setTextColor(Color.RED);
        } else {
            holder.textViewCurrentAmount.setTextColor(COLOR_DARK_GREEN);
        }

        holder.textViewSuggestedAmount.setText(list.get(position).getSuggestedAmountString());
        if (list.get(position).getSuggested() < 0) {
            holder.textViewSuggestedAmount.setTextColor(Color.RED);
        } else {
            holder.textViewSuggestedAmount.setTextColor(COLOR_DARK_GREEN);
        }

        // holder.textViewDescription.setText(list.get(position).getDescription());

        holder.textViewCategory.setText(list.get(position).getCategory().toString());

        holder.currentPointer.setVisibility(View.VISIBLE);
        holder.suggestionPointer.setVisibility(View.INVISIBLE);

        holder.toggleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                list.get(pos).toggle();
                if (list.get(pos).isUseNew()) {
                    holder.currentPointer.setVisibility(View.INVISIBLE);
                    holder.suggestionPointer.setVisibility(View.VISIBLE);
                } else {
                    holder.currentPointer.setVisibility(View.VISIBLE);
                    holder.suggestionPointer.setVisibility(View.INVISIBLE);
                }
            }

        });

        return convertView;
    }

}