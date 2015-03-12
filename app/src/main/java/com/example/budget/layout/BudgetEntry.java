package com.example.budget.layout;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BudgetEntry{
	private String description;
	private Category category;
	private double amount;
	private Date date;
	private int id;
	
	public BudgetEntry(){
	    this.category = new Category();
	    this.date = new Date();
	}
	
	public BudgetEntry(String description, Category category, double amount, int id, Date date){
		this.description = description;
		this.category = category;
		this.amount = amount;
		this.date = date;
		this.id = id;
	}
	
	public BudgetEntry( BudgetEntry other ){
		description = other.getDescription();
		category = other.getCategory();
		amount = other.getAmount();
		date = other.getDate();
		id = other.getId();
	}
	
	public String getAmountAsString(){
		return NumberFormat.getCurrencyInstance().format(amount);		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDateFromString(String date) throws ParseException{
	    this.date = getSimpleDateFormat().parse(date);
	}
    
    public String getDateAsString(){
        return getSimpleDateFormat().format(this.date);
    }
    
    private SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		if( date == null ){
			return description + " (" + category + ") " + ", " + getAmountAsString() + "\nOnce every month";
		}
		else{
			return description + " (" + category + ") " + ", " + getAmountAsString() + "\n" + getDateAsString();
		}
	}
}