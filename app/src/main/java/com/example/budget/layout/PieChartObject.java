package com.example.budget.layout;

import java.text.NumberFormat;

import android.graphics.Paint;


public class PieChartObject{
	private Paint paint;
	private String category;
	private float total;
	private float angle;
	
	public Paint getPaint() {
		return paint;
	}
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public void setAngle(float value, float total) {
		this.angle = value/total*360;
	}
	
	public String toString(){
		return category + " [" + NumberFormat.getCurrencyInstance().format(Math.abs(total)) + "]";
	}
}