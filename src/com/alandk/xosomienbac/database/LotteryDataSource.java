package com.alandk.xosomienbac.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LotteryDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_DATE, MySQLiteHelper.COLUMN_RESULT };

	public LotteryDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public LotteryDBResult createLotteryResult(int date, String result) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_DATE, date);
		values.put(MySQLiteHelper.COLUMN_RESULT, result);
		database.insert(MySQLiteHelper.TABLE_LOTTERY, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOTTERY, allColumns, MySQLiteHelper.COLUMN_DATE + " = " + date, null, null, null, null);
		cursor.moveToFirst();
		LotteryDBResult newLotteryResult = cursorToLottery(cursor);
		cursor.close();
		return newLotteryResult;
	}

	public void createOrUpdateLotteryDBResult(int date, String result) {
		LotteryDBResult lotteryDBResult = getLotteryResultByDate(date);
		if (lotteryDBResult == null) {
			createLotteryResult(date, result);
		} else {
			updateLotteryResult(date, result);
		}
	}

	public LotteryDBResult updateLotteryResult(int date, String result) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_RESULT, result);
		database.update(MySQLiteHelper.TABLE_LOTTERY, values, MySQLiteHelper.COLUMN_DATE + " = ?", new String[] { String.valueOf(date) });
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOTTERY, allColumns, MySQLiteHelper.COLUMN_DATE + " = " + date, null, null, null, null);
		cursor.moveToFirst();
		LotteryDBResult newLotteryResult = cursorToLottery(cursor);
		cursor.close();
		return newLotteryResult;
	}

	public List<LotteryDBResult> getAllLotteryResults() {
		List<LotteryDBResult> lotteryResults = new ArrayList<LotteryDBResult>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOTTERY, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			LotteryDBResult lotteryResult = cursorToLottery(cursor);
			lotteryResults.add(lotteryResult);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return lotteryResults;
	}

	public LotteryDBResult getLotteryResultByDate(int date) {
		LotteryDBResult newLotteryResult = null;
		String strDate = Integer.valueOf(date).toString();
		Cursor cursor = database
				.query(MySQLiteHelper.TABLE_LOTTERY, allColumns, MySQLiteHelper.COLUMN_DATE + " =?", new String[] { strDate }, null, null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			newLotteryResult = cursorToLottery(cursor);
			cursor.close();
		}
		return newLotteryResult;
	}

	private LotteryDBResult cursorToLottery(Cursor cursor) {
		LotteryDBResult lotteryResult = new LotteryDBResult();
		lotteryResult.setDate((int) cursor.getLong(0));
		lotteryResult.setResult(cursor.getString(1));
		return lotteryResult;
	}
}