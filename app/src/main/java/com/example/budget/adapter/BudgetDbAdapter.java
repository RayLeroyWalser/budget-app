package com.example.budget.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.budget.layout.BudgetEntry;
import com.example.budget.layout.Category;

//TODO make test class for this
public class BudgetDbAdapter {

    private static final String DATABASE_NAME = "budgeter.db";
    private static final int DATABASE_VERSION = 6;

    protected SQLiteDatabase db;
    private final Context context;
    private budgetDBOpenHelper dbHelper;

    public static final String ENTRIES_TABLE = "ENTRIES";
    public static final String ENTRIES_ID_KEY = "id";
    public static final String ENTRIES_DATE_KEY = "date_of_entry";
    public static final String ENTRIES_DESCRIPTION_KEY = "description";
    public static final String ENTRIES_CATEGORY_KEY = "category";
    public static final String ENTRIES_AMOUNT_KEY = "amount";
    public static final String ENTRIES_LAST_UPDATED_KEY = "updated";
    public static final int ENTRIES_ID_COLUMN = 0;
    public static final int ENTRIES_DATE_COLUMN = 1;
    public static final int ENTRIES_DESCRIPTION_COLUMN = 2;
    public static final int ENTRIES_CATEGORY_COLUMN = 3;
    public static final int ENTRIES_AMOUNT_COLUMN = 4;
    public static final int ENTRIES_UPDATED_COLUMN = 5;
    public final String[] allEntryColumns = new String[]{ENTRIES_ID_KEY, ENTRIES_DATE_KEY,
            ENTRIES_DESCRIPTION_KEY, ENTRIES_CATEGORY_KEY, ENTRIES_AMOUNT_KEY, ENTRIES_LAST_UPDATED_KEY};

    public static final String CATEGORIES_TABLE = "CATEGORIES";
    public static final String CATEGORIES_ID_KEY = "id";
    public static final String CATEGORIES_NAME_KEY = "name";
    public static final String CATEGORIES_AMOUNT_KEY = "projection_amount";
    public static final String CATEGORIES_IS_ACTIVE_KEY = "is_active";
    public static final int TRUE = 0;
    public static final int FALSE = 1;
    public static final int CATEGORIES_ID_COLUMN = 0;
    public static final int CATEGORIES_NAME_COLUMN = 1;
    public static final int CATEGORIES_AMOUNT_COLUMN = 2;
    public static final int CATEGORIES_IS_ACTIVE_COLUMN = 3;
    public final String[] allCategoryColumns = new String[]{CATEGORIES_ID_KEY, CATEGORIES_NAME_KEY,
            CATEGORIES_AMOUNT_KEY, CATEGORIES_IS_ACTIVE_KEY};

    public BudgetDbAdapter(Context _context) {
        this.context = _context;
        dbHelper = new budgetDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public long insertEntry(BudgetEntry newEntry) {
        return db.insert(ENTRIES_TABLE, null, getContentValuesFromEntry(newEntry));
    }

    public long updateEntry(BudgetEntry entry) {
        return db.update(ENTRIES_TABLE, getContentValuesFromEntry(entry),
                ENTRIES_ID_KEY + " = " + String.valueOf(entry.getId()), null);
    }

    public boolean deleteEntry(long id) {
        boolean deleteResult = db.delete(ENTRIES_TABLE, ENTRIES_ID_KEY + "=" + String.valueOf(id), null) > 0;
        // TODO check if category should be deleted
        return deleteResult;
    }

    private ContentValues getContentValuesFromEntry(BudgetEntry newEntry) {
        SimpleDateFormat dateFormat = getSimpleDateFormat();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ENTRIES_DATE_KEY, dateFormat.format(newEntry.getDate()));
        contentValues.put(ENTRIES_DESCRIPTION_KEY, newEntry.getDescription());
        contentValues.put(ENTRIES_CATEGORY_KEY, newEntry.getCategory().getId());
        contentValues.put(ENTRIES_AMOUNT_KEY, newEntry.getAmount());
        contentValues.put(ENTRIES_LAST_UPDATED_KEY, dateFormat.format(new Date()));

        return contentValues;
    }

    public long insertCategory(Category category) {
        return db.insert(CATEGORIES_TABLE, null, getContentValuesFromCategory(category));
    }

    public long updateCategory(Category category) {
        return db.update(CATEGORIES_TABLE, getContentValuesFromCategory(category), ENTRIES_ID_KEY + " = "
                + String.valueOf(category.getId()), null);
    }

    public boolean deleteCategory(long id) {
        return db.delete(CATEGORIES_TABLE, CATEGORIES_ID_KEY + "=" + String.valueOf(id), null) > 0;
    }

    private ContentValues getContentValuesFromCategory(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORIES_NAME_KEY, category.getName());
        contentValues.put(CATEGORIES_AMOUNT_KEY, category.getAmount());
        contentValues.put(CATEGORIES_IS_ACTIVE_KEY, category.getIsActiveAsInt());
        return contentValues;
    }

    private SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void truncateTables() {
        db.delete(ENTRIES_TABLE, null, null);
        db.delete(CATEGORIES_TABLE, null, null);
    }

    public boolean hasEntries() {
        Cursor listCursor;
        listCursor = db.query(ENTRIES_TABLE, new String[]{"COUNT(" + ENTRIES_ID_KEY + ")"}, null, null,
                null, null, null);
        if (listCursor.moveToFirst()) {
            if (listCursor.getInt(0) == 0) {
                listCursor.close();
                return false;
            }
        }
        listCursor.close();
        return true;
    }

    public Category getCategoryForName(String string) {
        Category category = null;
        Cursor queryResult = db.query(CATEGORIES_TABLE, allCategoryColumns, CATEGORIES_NAME_KEY + " = ?",
                new String[]{string}, null, null, null);
        if (queryResult.moveToFirst()) {
            category = new Category();
            category.setId(queryResult.getLong(CATEGORIES_ID_COLUMN));
            category.setAmount(queryResult.getDouble(CATEGORIES_AMOUNT_COLUMN));
            category.setName(string);
        }
        return category;
    }

    public Category getCategoryForId(long id) {
        Category category = null;
        Cursor queryResult = db.query(CATEGORIES_TABLE, allCategoryColumns, CATEGORIES_ID_KEY + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (queryResult.moveToFirst()) {
            category = new Category();
            category.setId(queryResult.getLong(CATEGORIES_ID_COLUMN));
            category.setAmount(queryResult.getDouble(CATEGORIES_AMOUNT_COLUMN));
            category.setName(queryResult.getString(CATEGORIES_NAME_COLUMN));
        }
        return category;
    }

    protected ArrayList<Category> createCategoryArrayListFromCursor(Cursor queryResult) {
        ArrayList<Category> resultList = new ArrayList<Category>();
        if (queryResult.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(queryResult.getInt(CATEGORIES_ID_COLUMN));
                category.setAmount(queryResult.getDouble(CATEGORIES_AMOUNT_COLUMN));
                category.setName(queryResult.getString(CATEGORIES_NAME_COLUMN));
                category.setIsActiveFromInt(queryResult.getInt(CATEGORIES_IS_ACTIVE_COLUMN));
                resultList.add(category);
            } while (queryResult.moveToNext());
        }
        return resultList;
    }

    protected ArrayList<BudgetEntry> createEntryArrayListFromCursor(Cursor cursor) {
        ArrayList<BudgetEntry> resultList = new ArrayList<BudgetEntry>();
        if (cursor.moveToFirst()) {
            do {
                BudgetEntry budgetEntry = new BudgetEntry();
                budgetEntry.setId(cursor.getInt(ENTRIES_ID_COLUMN));
                budgetEntry.setCategory(getCategoryForId(cursor.getLong(ENTRIES_CATEGORY_COLUMN)));
                budgetEntry.setAmount(cursor.getDouble(ENTRIES_AMOUNT_COLUMN));
                budgetEntry
                        .setDate(new DateTime(cursor.getString(ENTRIES_DATE_COLUMN).replaceFirst(" ", "T"))
                                .toDate());
                budgetEntry.setDescription(cursor.getString(ENTRIES_DESCRIPTION_COLUMN));
                resultList.add(budgetEntry);
            } while (cursor.moveToNext());
        }
        return resultList;
    }

    /**
     * **********************************************************************************************
     */

    private static class budgetDBOpenHelper extends SQLiteOpenHelper {

        public budgetDBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // TODO make name column unique
        private static final String CREATE_CATEGORIES_TABLE = "create table " + CATEGORIES_TABLE + " ("
                + CATEGORIES_ID_KEY + " integer primary key autoincrement, " + CATEGORIES_NAME_KEY
                + " text, " + CATEGORIES_AMOUNT_KEY + " double, " + CATEGORIES_IS_ACTIVE_KEY + " int);";

        private static final String CREATE_ENTRIES_TABLE = "create table " + ENTRIES_TABLE + " ("
                + ENTRIES_ID_KEY + " integer primary key autoincrement, " + ENTRIES_DATE_KEY + " datetime, "
                + ENTRIES_DESCRIPTION_KEY + " text, " + ENTRIES_CATEGORY_KEY + " bigint, "
                + ENTRIES_AMOUNT_KEY + " double , " + ENTRIES_LAST_UPDATED_KEY + " datetime, " +
                " FOREIGN KEY(" + ENTRIES_CATEGORY_KEY + ") REFERENCES categories(" +
                CATEGORIES_ID_KEY + "));";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(CREATE_CATEGORIES_TABLE);
            _db.execSQL(CREATE_ENTRIES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            _db.execSQL("DROP TABLE " + ENTRIES_TABLE);
            _db.execSQL("DROP TABLE " + CATEGORIES_TABLE);
            _db.execSQL(CREATE_CATEGORIES_TABLE);
            _db.execSQL(CREATE_ENTRIES_TABLE);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
}
