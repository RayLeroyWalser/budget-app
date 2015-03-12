package com.example.budget.test;

import android.test.AndroidTestCase;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.BudgetEntry;
import com.example.budget.layout.Category;

public class BudgetDbAdapterTest extends AndroidTestCase {

	BudgetDbAdapter db;

	@Override
	public void setUp(){
		db = new BudgetDbAdapter(getContext());
		db.open();
		assertNotNull(db);
		db.truncateTables();
	}

	public void testInsertAndRemoveEntry(){
		BudgetEntry budgetEntry = new BudgetEntry();
		long rowId = db.insertEntry(budgetEntry);
		assertTrue("Should not have returned -1",-1L < rowId);
		assertTrue(db.deleteEntry(rowId));
	}
	
	public void testInsertAndRemoveCategory(){
		Category category = new Category();
		long rowId = db.insertCategory(category);
		assertTrue("failed to insert category",rowId != -1L);
		assertTrue(db.deleteCategory(rowId));
	}

	@Override
	public void tearDown(){
	    BudgetEntry budgetEntry = new BudgetEntry();
	    Category category = new Category();
	    db.insertEntry(budgetEntry);
	    db.insertCategory(category);
		db.close();
	}
}
