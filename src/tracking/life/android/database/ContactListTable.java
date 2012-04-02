package tracking.life.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ContactListTable {

	// Database table
	public static final String TABLE_CONTACT_LIST = "contact_list";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MEMBER_ID = "memberId";
	public static final String COLUMN_CONTACT_ID = "contactId";
	public static final String COLUMN_CONTACT_CATEGORY_ID = "contactCategoryId";
	public static final String COLUMN_FIRST_NAME = "firstName";
	public static final String COLUMN_LAST_NAME = "lastName";
	public static final String COLUMN_PHOTO = "photo";
	public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_TRACKING_PERMISSION = "trackingPermission";
	public static final String COLUMN_IS_SYNCED= "isSynced";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_CONTACT_LIST
			+ "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_MEMBER_ID + " INTEGER NOT NULL, " 
			+ COLUMN_CONTACT_ID + " INTEGER NOT NULL, " 
			+ COLUMN_CONTACT_CATEGORY_ID + " INTEGER," 
			+ COLUMN_FIRST_NAME + " TEXT NOT NULL,"
			+ COLUMN_LAST_NAME + " TEXT,"
			+ COLUMN_PHOTO + " BLOB,"
			+ COLUMN_PHONE_NUMBER + " TEXT,"
			+ COLUMN_TRACKING_PERMISSION + " INTEGER,"
			+ COLUMN_IS_SYNCED + " INTEGER DEFAULT (0) "
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ContactListTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_LIST);
		onCreate(database);
	}
}