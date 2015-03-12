package com.example.budget.component;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

import com.example.budget.layout.PieChartObject;

public class PreviousMonthPieChart extends View{

	public static final String TAG = "previousMonthView";
	private ArrayList<PieChartObject> entries;
	
	Paint p;
	private RectF rect;
	private float width;
	private float height;
	LayoutParams l;
	/*
	public PreviousMonthPieChart(Context context) {
		super(context);
		
		
		
		setEntries(new ArrayList<PieChartObject>());
		p = new Paint();
		p.setAntiAlias(false);
		p.setColor(Color.BLUE);
		
		
	}*/
	
	public PreviousMonthPieChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEntries(new ArrayList<PieChartObject>());
		p = new Paint();
		p.setColor(Color.BLUE);
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		Log.w(TAG,"DRAWING!");
		int[] colors = {Color.RED, Color.WHITE, Color.BLUE, Color.CYAN, Color.GREEN, Color.DKGRAY, Color.MAGENTA, Color.YELLOW, Color.LTGRAY, Color.GRAY};
		
		float startAngle = 0.0f;
		float endAngle = 0.0f;
		int i;
		
		width = ((float) canvas.getWidth());
		height = (float) canvas.getHeight();
		
		
		//canvas.drawColor(Color.WHITE);
		
		rect = new RectF();
		rect.set(width*0.125f, 0, width*0.875f, width*0.75f);

		updateAngles();
		for( i=0; i < entries.size(); i++){
			//Log.w(TAG,entries.get(i).getCategory() + " has angle: " + entries.get(i).getAngle());
			p.setColor(colors[i%colors.length]);
			endAngle = (float)(entries.get(i).getAngle());
			//Log.w(TAG, "For category '" + entries.get(i).getCategory() + "' start angle is " + String.valueOf(startAngle% 360f) + " and end angle is " + String.valueOf(endAngle% 360f));
			canvas.drawArc(rect, startAngle % 360f, endAngle % 360f, true, p);
			startAngle += endAngle;
		}
		
		int textSize = 20;
		p.setTextSize(textSize);
		for( i=0; i < entries.size(); i++ ){
			p.setColor(colors[i%colors.length]);
			canvas.drawText(entries.get(i).toString(), 5, (0.75f*width+textSize)+(textSize+5)*i, p);
		}
		
	}
	
	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
	   int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
	   int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
	   Log.w(TAG,"parentWidth:[" + String.valueOf(parentWidth) + "], parentHeight: [" + String.valueOf(parentHeight) + "]");
	   this.setMeasuredDimension(parentWidth, parentWidth);
	   //this.setLayoutParams(new RelativeLayout.LayoutParams(parentWidth,parentHeight));
	   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void updateAngles(){
		int i;
		float total = 0.0f;
		
		for( i = 0; i < entries.size(); i++){
			total += Math.abs(entries.get(i).getTotal());
		}
		
		for( i=0; i < entries.size(); i++){
			entries.get(i).setAngle(Math.abs((entries.get(i).getTotal()/total)*360f));
		}
	}
	
	public ArrayList<PieChartObject> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<PieChartObject> entries) {
		this.entries = entries;
	}
}