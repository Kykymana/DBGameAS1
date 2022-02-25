package ru.samsung.itschool.dbgame;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	/*
	 * TABLES: ------- RESULTS SCORE INTEGER USER VARCHAR
	 */
	private Context context;
	private String DB_NAME = "game.db";

	private SQLiteDatabase db;

	private static DBManager dbManager;

	public static DBManager getInstance(Context context) {
		if (dbManager == null) {
			dbManager = new DBManager(context);
		}
		return dbManager;
	}

	int gamesCount;
	DBManager(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		createTablesIfNeedBe();
	}

	void addResult(String username, int score) {
		//так не надо  db.execSQL("INSERT INTO RESULTS VALUES ('" + username + "', " + score + ");");
		gamesCount += 1;
		ContentValues contentValues = new ContentValues();
		contentValues.put("USERNAME", username);
		contentValues.put("SCORE", score);
		db.insert("RESULTS", null, contentValues);
	}

	// INSERT INTO RESULTS VALUES ('
	// username
	// ',
	// score
	// );

	// INSERT INTO RESULTS VALUES ('
	// Vasya', 1001);
	// ',
	// score
	// );
	int getGamesCount(){
		return gamesCount;
	}


	ArrayList<Result> getAllResults() {
		ArrayList<Result> data = new ArrayList<Result>();
		Cursor cursor = db.rawQuery("SELECT * FROM RESULTS ORDER BY SCORE;", null);
		boolean hasMoreData = cursor.moveToFirst();

		while (hasMoreData) {
			String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
			int score = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("SCORE")));
			data.add(new Result(name, score));
			hasMoreData = cursor.moveToNext();
		}

		return data;
	}
	Cursor getMax(){
		Cursor max = db.rawQuery("SELECT MAX(SCORE) FROM RESULTS ORDER BY SCORE;", null);
		return max;
	}
	ArrayList<Result> getAllUserResults(String username, int minScore) {
		ArrayList<Result> data = new ArrayList<Result>();
		Cursor cursor = db.rawQuery("SELECT * FROM RESULTS WHERE USERNAME = ? AND SCORE >= ?", new String[]{username, minScore + ""});
		boolean hasMoreData = cursor.moveToFirst();

		while (hasMoreData) {
			String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
			int score = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("SCORE")));
			data.add(new Result(name, score));
			hasMoreData = cursor.moveToNext();
		}

		return data;
	}
	// SELECT USERNAME, MAX(SCORE) AS MS FROM RESULTS GROUP BY USERNAME HAVING MS > 500 ORDER BY MS DESK

	ArrayList<Result> getMaxUserResults() {
		ArrayList<Result> data = new ArrayList<Result>();
		Cursor cursor = db.query("RESULTS", new String[]{"USERNAME", "MAX(SCORE) AS MS"}, null, null,  "USERNAME", "MS > 500", "MS DESC"
				);
		boolean hasMoreData = cursor.moveToFirst();

		while (hasMoreData) {
			String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
			int score = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("SCORE")));
			data.add(new Result(name, score));
			hasMoreData = cursor.moveToNext();
		}

		return data;
	}

	private void createTablesIfNeedBe() {
		db.execSQL("CREATE TABLE IF NOT EXISTS RESULTS (USERNAME TEXT, SCORE INTEGER);");
	}

}
