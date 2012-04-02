package tracking.life.android.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import tracking.life.android.database.DatabaseHelper;

public class LocationHistoryDbAdapter {
	// Database fields
	public static final String DATABASE_TABLE = "location_history";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MEMBER_ID = "memberId";
	public static final String KEY_LOCATION_LIST_ID = "locationListId";
	public static final String KEY_TIME_STAMP = "time_stamp";
	public static final String KEY_TIME_LENGTH = "timeLength";
	public static final String KEY_IS_SYNCED = "isSynced";

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public LocationHistoryDbAdapter(Context context) {
		this.context = context;
	}
	
	public LocationHistoryDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/***
	 * Create a new item If the item is successfully created return the new*
	 * rowId for that item, otherwise return a -1 to indicate failure.
	 */
	public long insertLocation(long memberId, long locationListId,
			int timeLength) {

		ContentValues values = new ContentValues();
		values.put(KEY_MEMBER_ID, memberId);
		values.put(KEY_LOCATION_LIST_ID, locationListId);
		values.put(KEY_TIME_LENGTH, timeLength);

		return database.insert(DATABASE_TABLE, null, values);
	}

	/**
	 * Update the item
	 */
	public boolean updateTimeLength(long rowId, int timeLength) {
		ContentValues values = new ContentValues();
		values.put(KEY_TIME_LENGTH, timeLength);

		return database.update(DATABASE_TABLE, values, KEY_ROWID + "=" + rowId,
				null) > 0;
	}

	public boolean updateIsSynced(long rowId, String isSynced) {
		ContentValues values = new ContentValues();
		values.put(KEY_IS_SYNCED, isSynced);

		return database.update(DATABASE_TABLE, values, KEY_ROWID + "=" + rowId,
				null) > 0;
	}

	/**
	 * Deletes the item with given id
	 */
	public boolean deleteByRowId(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all the items in the database
	 * 
	 * @return Cursor over all location history data
	 */
	public Cursor selectLocationHistoryByMemberId(long memberId) {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_LOCATION_LIST_ID, KEY_TIME_STAMP, KEY_TIME_LENGTH },
				KEY_MEMBER_ID + "=" + memberId, null, null, null, null);
	}

}
