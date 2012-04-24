package tracking.life.android.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import tracking.life.android.database.DatabaseHelper;

public class LocationNoteDbAdapter {
	// Database fields
	public static final String DATABASE_TABLE = "location_note";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MEMBER_ID = "memberId";
	public static final String KEY_LOCATION_LIST_ID = "locationListId";
	public static final String KEY_NOTE = "note";
	public static final String KEY_TIME_STAMP = "timeStamp";
	public static final String KEY_IS_SYNCED = "isSynced";

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public LocationNoteDbAdapter(Context context) {
		this.context = context;
	}

	public LocationNoteDbAdapter open() throws SQLException {
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
	public long insertNewNote(long memberId, long locationListId, String note) {

		ContentValues values = new ContentValues();
		values.put(KEY_MEMBER_ID, memberId);
		values.put(KEY_LOCATION_LIST_ID, locationListId);
		values.put(KEY_NOTE, note);

		return database.insert(DATABASE_TABLE, null, values);
	}

	/**
	 * Update the item
	 */
	public boolean updateLocationNote(long rowId, String note) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_NOTE, note);

		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	public boolean updateIsSynced(long rowId, int isSynced) {
		ContentValues values = new ContentValues();
		values.put(KEY_IS_SYNCED, isSynced);
		return database.update(DATABASE_TABLE, values, KEY_ROWID + "=" + rowId,
				null) > 0;
	}

	public boolean markAsSynced(long rowId) {
		return updateIsSynced(rowId, 1);
	}

	/**
	 * Deletes the item with given id
	 */
	public boolean deleteByRowId(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all the items in the database
	 */
	public Cursor selectAllLocationNoteByMemberId(long memberId) {

		String[] selectColumns = new String[] { KEY_ROWID, KEY_MEMBER_ID,
				KEY_LOCATION_LIST_ID, KEY_NOTE, KEY_TIME_STAMP, KEY_IS_SYNCED };
		String selectWhere = KEY_MEMBER_ID + "=" + memberId;
		return database.query(DATABASE_TABLE, selectColumns, selectWhere, null,
				null, null, null);
	}

	public Cursor selectAllNotesByMemberIdLocationId(long memberId,
			long locationListId) {

		String[] selectColumns = new String[] { KEY_ROWID, KEY_MEMBER_ID,
				KEY_LOCATION_LIST_ID, KEY_NOTE, KEY_TIME_STAMP, KEY_IS_SYNCED };
		String selectWhere = KEY_MEMBER_ID + "=" + memberId
				+ KEY_LOCATION_LIST_ID + "=" + locationListId;
		return database.query(DATABASE_TABLE, selectColumns, selectWhere, null,
				null, null, null);
	}

	public Cursor selectAll() {
		Cursor mCursor = null;
		mCursor = database.rawQuery("select * from " + DATABASE_TABLE, null);
		return mCursor;

	}
}
