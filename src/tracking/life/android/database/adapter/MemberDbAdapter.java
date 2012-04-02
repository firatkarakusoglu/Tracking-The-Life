package tracking.life.android.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import tracking.life.android.database.DatabaseHelper;

public class MemberDbAdapter {
	// Database fields
	public static final String DATABASE_TABLE = "member";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_FIRST_NAME = "firstName";
	public static final String KEY_LAST_NAME = "lastName";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_BIRTHDATE = "birthDate";
	public static final String KEY_PHOTO = "photo";
	public static final String KEY_PHONE_NUMBER = "phoneNumber";
	public static final String KEY_REGISTRATION_DATE = "registrationDate";
	public static final String KEY_IS_ACTIVE = "isActive";
	public static final String KEY_IS_SYNCED = "isSynced";

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public MemberDbAdapter(Context context) {
		this.context = context;
	}

	public MemberDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	// first row of every table is used as context temp
	public boolean saveContexToTemp(String email, String firstName,
			String lastName, String password) {
		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email);
		values.put(KEY_FIRST_NAME, firstName);
		values.put(KEY_LAST_NAME, lastName);

		int rowId = 1; // the first row is used to save temp data
		String selectWhere = KEY_ROWID + "=" + rowId;
		if (database.update(DATABASE_TABLE, values, selectWhere, null) <= 0) {
			insertNewMember(email, firstName, lastName, password);
		}

		return true;
	}

	/***
	 * Create a new item If the item is successfully created return the new*
	 * rowId for that item, otherwise return a -1 to indicate failure.
	 */
	public long insertNewMember(String email, String firstName,
			String lastName, String password) {

		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email);
		values.put(KEY_FIRST_NAME, firstName);
		values.put(KEY_LAST_NAME, lastName);
		values.put(KEY_PASSWORD, password);

		long result = database.insert(DATABASE_TABLE, null, values);

		return result;
	}

	/**
	 * Update the item
	 */
	public boolean updateFirstName(long rowId, String firstName) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_FIRST_NAME, firstName);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	public boolean updateLastName(long rowId, String lastName) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_LAST_NAME, lastName);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	public boolean updateKeyPhoneNumber(long rowId, String phoneNumber) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_PHONE_NUMBER, phoneNumber);
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
	public Cursor selectMemberByMemberId(long memberId) throws SQLException {

		String[] selectColumns = new String[] { KEY_ROWID, KEY_EMAIL,
				KEY_PASSWORD, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER,
				KEY_BIRTHDATE, KEY_PHOTO, KEY_PHONE_NUMBER,
				KEY_REGISTRATION_DATE, KEY_IS_ACTIVE };
		String selectWhere = KEY_ROWID + "=" + memberId;
		Cursor mCursor = database.query(true, DATABASE_TABLE, selectColumns,
				selectWhere, null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

//	public Cursor selectMemberByEmailPassword(String email, String password)
//			throws SQLException {
//
//		String[] selectColumns = new String[] { KEY_ROWID };
//		String selectWhere = KEY_EMAIL + "=" + email + " AND " + KEY_PASSWORD + "=" + password;
//
//		Cursor mCursor = database.query(true, DATABASE_TABLE, selectColumns,
//				selectWhere, null, null, null, null, null);
//
//		if (mCursor != null) {
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//	}

	public Cursor selectMemberByEmailPassword(String email, String password) 
	{

		Cursor mCursor = null;
		long memberId = 0;
		String selectWhere = KEY_EMAIL + "='" + email + "' AND " + KEY_PASSWORD + "='" + password+"'";
		mCursor = database.rawQuery("select * from " + DATABASE_TABLE + " where " + selectWhere  , null);
		return mCursor;

	}

}
