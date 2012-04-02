package tracking.life.android.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import tracking.life.android.database.DatabaseHelper;

public class ContactListDbAdapter {
	// Database fields
	public static final String DATABASE_TABLE = "contact_list";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MEMBER_ID = "memberId";
	public static final String KEY_CONTACT_ID = "contactId";
	public static final String KEY_CONTACT_CATEGORY_ID = "contactCategoryId";
	public static final String KEY_FIRST_NAME = "firstName";
	public static final String KEY_LAST_NAME = "lastName";
	public static final String KEY_PHOTO = "photo";
	public static final String KEY_PHONE_NUMBER = "phoneNumber";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_TRACKING_PERMISSION = "trackingPermission";
	public static final String KEY_IS_SYNCED = "isSynced";

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public ContactListDbAdapter(Context context) {
		this.context = context;
	}
	
	public ContactListDbAdapter open() throws SQLException {
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

	// creating new contact with member id, contact id, first name, last name,
	// phone number and email values
	public long insertNewContact(long memberId, long contactId,
			String firstName, String lastName, String phoneNumber, String email) {
		ContentValues values = new ContentValues();
		values.put(KEY_MEMBER_ID, memberId);
		values.put(KEY_CONTACT_ID, contactId);
		values.put(KEY_FIRST_NAME, firstName);
		values.put(KEY_LAST_NAME, lastName);
		values.put(KEY_PHONE_NUMBER, phoneNumber);
		values.put(KEY_EMAIL, email);

		return database.insert(DATABASE_TABLE, null, values);
	}

	/**
	 * Return a Cursor over the list of all the items in the database
	 */
	public Cursor selectContactsByMemberId(long memberId) {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_CONTACT_ID, KEY_FIRST_NAME, KEY_LAST_NAME,
				KEY_PHONE_NUMBER, KEY_EMAIL }, KEY_MEMBER_ID + "=" + memberId,
				null, null, null, null);
	}

	public Cursor selectContactsByMemberIdCategoryId(long memberId,
			long contactCategoryId) {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_CONTACT_ID, KEY_FIRST_NAME, KEY_LAST_NAME,
				KEY_PHONE_NUMBER, KEY_EMAIL }, KEY_CONTACT_CATEGORY_ID + "="
				+ memberId + "AND " + KEY_CONTACT_ID + "=" + contactCategoryId,
				null, null, null, null);
	}
	
}
