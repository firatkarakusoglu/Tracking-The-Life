package tracking.life.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocationNoteTable {

	// Database table
	public static final String TABLE_LOCATION_NOTE = "location_note";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MEMBER_ID = "memberId";
	public static final String COLUMN_LOCATION_LIST_ID = "locationListId";
	public static final String COLUMN_NOTE = "note";
	public static final String COLUMN_TIME_STAMP = "time_stamp";
	public static final String COLUMN_IS_SYNCED= "isSynced";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_LOCATION_NOTE
			+ "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_MEMBER_ID + " INTEGER NOT NULL, " 
			+ COLUMN_LOCATION_LIST_ID + " INTEGER NOT NULL, " 
			+ COLUMN_NOTE + " TEXT," 
			+ COLUMN_TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
			+ COLUMN_IS_SYNCED + " INTEGER DEFAULT (0) "
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(LocationNoteTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_NOTE);
		onCreate(database);
	}
}