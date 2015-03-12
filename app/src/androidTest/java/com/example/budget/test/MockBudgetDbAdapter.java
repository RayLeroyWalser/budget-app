package com.example.budget.test;

import org.joda.time.DateTime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.budget.adapter.BudgetDbAdapter;

public class MockBudgetDbAdapter extends BudgetDbAdapter{

	private static final String DATABASE_TABLE = "mockBudgetDb";

	public MockBudgetDbAdapter(Context _context) {
		super(_context);
	}

	public void insertTestData(SQLiteDatabase db) {
		db.execSQL("insert into " + DATABASE_TABLE + " "
				+ "values (" + new DateTime("2014-10-05T12:34:56").toDate().getTime() + ", "
				+ "1, "
				+ "wendys, "
				+ "Food, "
				+ "6.18, "
				+ "1, "
				+ "0, "
				+ "10, "
				+ "2014, "
				+ new DateTime("2014-10-05T12:34:56").toDate().getTime() + ");");

	}

}