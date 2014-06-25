package com.alandk.xosomienbac.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "comments";	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";
	
	public static final String TABLE_LOTTERY = "lottery";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_RESULT = "result";

	private static final String DATABASE_NAME = "xosomienbac.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement: create table comment
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_COMMENTS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_COMMENT
			+ " text not null);";
	
	// Database creation sql statement: create table lottery
	private static final String CREATE_TABLE_LOTTERY = "create table "
			+ TABLE_LOTTERY + "(" + COLUMN_DATE
			+ " integer primary key, " + COLUMN_RESULT
			+ " text not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		database.execSQL(CREATE_TABLE_LOTTERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		onCreate(db);
	}

}
