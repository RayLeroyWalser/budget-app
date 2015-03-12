package com.example.budget.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.budget.R;
import com.example.budget.layout.OverviewItem;

public class OverviewListAdapter extends BaseAdapter {

    Activity context;
    ArrayList<OverviewItem> list;

    private LayoutInflater mInflater;

    public OverviewListAdapter(Activity context, ArrayList<OverviewItem> _list) {
        list = _list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public OverviewItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    static class ViewHolder {
        TextView textViewCategory;
        TextView textViewActual;
        TextView textViewEstimate;
        ProgressBar progress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.overviewlistviewrow, null);
            holder = new ViewHolder();
            holder.textViewEstimate = (TextView) convertView.findViewById(R.id.overviewListViewEstimate);
            holder.textViewActual = (TextView) convertView.findViewById(R.id.overviewListViewActual);
            holder.textViewCategory = (TextView) convertView.findViewById(R.id.overviewListViewCategory);
            holder.progress = (ProgressBar) convertView.findViewById(R.id.overviewListViewProgress);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            formatAsHeaderItem(holder.textViewActual, "Actual");
            formatAsHeaderItem(holder.textViewEstimate, "Estimate");
            formatAsHeaderItem(holder.textViewCategory, "Category");
            holder.progress.setVisibility(View.GONE);
        } else {
            OverviewItem item = list.get(position);
            formatAsListItem(holder.textViewActual, item.getActualAmountString());
            formatAsListItem(holder.textViewEstimate, item.getEstimateAmountString());
            formatAsListItem(holder.textViewCategory, item.categoryName);
            holder.progress.setProgress(item.getProgress());
            holder.progress.setVisibility(View.VISIBLE);
        }

        // TODO set overspent style

        return convertView;
    }

    private void formatAsHeaderItem(TextView view, String headerText) {
        view.setText(headerText);
        view.setTextSize(22);
        view.setTextColor(Color.WHITE);
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    private void formatAsListItem(TextView view, String cellText) {
        view.setText(cellText);
        view.setTextColor(Color.BLACK);
        view.setTextSize(12);
    }

}