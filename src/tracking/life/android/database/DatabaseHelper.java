package tracking.life.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "trackingthelife.db";
	private static final int DATABASE_VERSION = 8;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		MemberTable.onCreate(database);
		PersonalSettingsTable.onCreate(database);
		TaskListTable.onCreate(database);
		
		ContactCategoryTable.onCreate(database);
		ContactListTable.onCreate(database);
		
		LocationCategoryTable.onCreate(database);
		LocationHistoryTable.onCreate(database);
		LocationListTable.onCreate(database);
		LocationNoteTable.onCreate(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		MemberTable.onUpgrade(database, oldVersion, newVersion);
		PersonalSettingsTable.onUpgrade(database, oldVersion, newVersion);
		TaskListTable.onUpgrade(database, oldVersion, newVersion);
		
		ContactCategoryTable.onUpgrade(database, oldVersion, newVersion);
		ContactListTable.onUpgrade(database, oldVersion, newVersion);
		
		LocationCategoryTable.onUpgrade(database, oldVersion, newVersion);
		LocationHistoryTable.onUpgrade(database, oldVersion, newVersion);
		LocationListTable.onUpgrade(database, oldVersion, newVersion);
		LocationNoteTable.onUpgrade(database, oldVersion, newVersion);
	}
}

