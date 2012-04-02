package tracking.life.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PersonalSettingsTable {

	// Database table
	public static final String TABLE_PERSONAL_SETTINGS = "personal_settings";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MEMBER_ID = "memberId";	
	public static final String COLUMN_DEFAULT_MAP_VIEW = "defaultMapView";
	public static final String COLUMN_DEFAULT_NOTE_BAR_ORDER = "defaultNoteBarOrder";
	public static final String COLUMN_DEFAULT_TASK_BAR_ORDER = "defaultTaskBarOrder";
	public static final String COLUMN_LOCATION_BAR_ORDER = "defaultLocationBarOrder";
	public static final String COLUMN_CONTACT_BAR_ORDER = "defaultContactBarOrder";
	public static final String COLUMN_IS_AUTO_CHECK_IN_ON = "isAutoCheckInOn";
	public static final String COLUMN_LOCATION_CHECKING_FREQUENCY = "locationCheckingFrequency";
	public static final String COLUMN_IS_OVERRIDE_PHONE_SOUND = "isOverridePhoneSound";
	public static final String COLUMN_IS_SYNCED= "isSynced";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_PERSONAL_SETTINGS
			+ "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_MEMBER_ID + " INTEGER NOT NULL, " 
			+ COLUMN_DEFAULT_MAP_VIEW + " INTEGER DEFAULT (0), " 
			+ COLUMN_DEFAULT_NOTE_BAR_ORDER + " INTEGER DEFAULT (0)," 
			+ COLUMN_DEFAULT_TASK_BAR_ORDER + " INTEGER DEFAULT (0),"
			+ COLUMN_LOCATION_BAR_ORDER + " INTEGER DEFAULT (0),"
			+ COLUMN_CONTACT_BAR_ORDER + " INTEGER DEFAULT (0),"
			+ COLUMN_IS_AUTO_CHECK_IN_ON + " INTEGER DEFAULT (0),"
			+ COLUMN_LOCATION_CHECKING_FREQUENCY + " INTEGER DEFAULT (10),"
			+ COLUMN_IS_OVERRIDE_PHONE_SOUND + " INTEGER DEFAULT (1),"			
			+ COLUMN_IS_SYNCED + " INTEGER DEFAULT (0)"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(PersonalSettingsTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_SETTINGS);
		onCreate(database);
	}
}