package tracking.life.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocationListTable {

	// Database table
	public static final String TABLE_LOCATION_LIST = "location_list";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MEMBER_ID = "memberId";
	public static final String COLUMN_CATEGORY_ID = "categoryId";
	public static final String COLUMN_TITLE= "title";
	public static final String COLUMN_LONGITUDE= "longitude";
	public static final String COLUMN_LATITUDE= "latitude";
	public static final String COLUMN_IS_FAVORITE= "isFavorite";
	public static final String COLUMN_IS_SYNCED= "isSynced";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_LOCATION_LIST 
			+ "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_MEMBER_ID + " INTEGER NOT NULL, " 
			+ COLUMN_CATEGORY_ID + " INTEGER," 
			+ COLUMN_TITLE + " TEXT, "
			+ COLUMN_LONGITUDE + " TEXT NOT NULL , "
			+ COLUMN_LATITUDE + " TEXT NOT NULL, "
			+ COLUMN_IS_FAVORITE + " INTEGER DEFAULT (0), "
			+ COLUMN_IS_SYNCED + " INTEGER DEFAULT (0) "
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(LocationListTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_LIST);
		onCreate(database);
	}
}