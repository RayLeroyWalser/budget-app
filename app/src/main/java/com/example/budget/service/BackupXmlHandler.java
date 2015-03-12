package com.example.budget.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.example.budget.layout.BudgetEntry;

public class BackupXmlHandler extends DefaultHandler{
	public static final String TAG = "myBudget";
	
	private ArrayList<BudgetEntry> list;
	private int id;
	private String description;
	private String category;
	private float amount;
	private Date date;
	
	private boolean inID,
					inDescription,
					inCategory,
					inAmount,
					inSign,
					inType,
					inDate;
	
	public ArrayList<BudgetEntry> getData(){
		//return new budgetEntry(description, category, amount, sign, id, date, type);
		return list;
	}
	
	@Override
	public void startDocument() throws SAXException {
		list = new ArrayList<BudgetEntry>();
	}

	@Override
	public void endDocument() throws SAXException {
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		Log.w(TAG,"startElement: localname[" + localName +"]");
		
		if( localName.equals("id") ){
			inID = true;
		}
		else if( localName.equals("description") ){
			inDescription = true;
		}
		else if( localName.equals("category") ){
			inCategory = true;
		}
		else if( localName.equals("amount") ){
			inAmount = true;
		}
		else if( localName.equals("sign") ){
			inSign = true;
		}
		else if( localName.equals("type") ){
			inType = true;
		}
		else if( localName.equals("date") ){
			inDate = true;
		}
		else{

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName){
		
		Log.w(TAG,"endElement: localname[" + localName + "]");
		if( localName.equals("id") ){
			inID = false;
		}
		else if( localName.equals("description") ){
			inDescription = false;
		}
		else if( localName.equals("category") ){
			inCategory = false;
		}
		else if( localName.equals("amount") ){
			inAmount = false;
		}
		else if( localName.equals("sign") ){
			inSign = false;
		}
		else if( localName.equals("type") ){
			inType = false;
		}
		else if( localName.equals("date") ){
			inDate = false;
		}
		else if( localName.equals("entry") ){
			//TODO list.add(new BudgetEntry(description, category, amount, id, date));
		}
		else{
			
		}
		
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String chars = new String(ch, start, length);
		chars.trim();

		Log.w(TAG,"characters: - " + chars);
		if( inID ){
			id = Integer.parseInt(chars);
		}
		else if( inDescription ){
			description = chars;
		}
		else if( inCategory ){
			category = chars;
		}
		else if( inAmount ){
			amount = Float.parseFloat(chars);
		}
		else if( inDate ){
			try {
				date = dateFormat.parse(chars);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{

		}
	}

}