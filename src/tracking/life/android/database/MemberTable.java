package tracking.life.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MemberTable {

	// Database table
	public static final String TABLE_MEMBER = "member";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_FIRST_NAME = "firstName";
	public static final String COLUMN_LAST_NAME = "lastName";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_BIRTHDATE = "birthDate";
	public static final String COLUMN_PHOTO = "photo";
	public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
	public static final String COLUMN_REGISTRATION_DATE = "registrationDate";
	public static final String COLUMN_IS_ACTIVE = "isActive";
	public static final String COLUMN_IS_SYNCED= "isSynced";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_MEMBER
			+ "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY , " 
			+ COLUMN_EMAIL + " TEXT NOT NULL," 
			+ COLUMN_PASSWORD + " TEXT NOT NULL, " 
			+ COLUMN_FIRST_NAME + " TEXT NOT NULL,"
			+ COLUMN_LAST_NAME + " TEXT,"
			+ COLUMN_GENDER + " INTEGER,"
			+ COLUMN_BIRTHDATE + " TEXT,"
			+ COLUMN_PHOTO + " BLOB,"
			+ COLUMN_PHONE_NUMBER + " TEXT,"
			+ COLUMN_REGISTRATION_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
			+ COLUMN_IS_ACTIVE + " INTEGER DEFAULT (1) " //todo default will be 0 and will turn to 1 after the user activates the account
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(MemberTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
		onCreate(database);
	}
}