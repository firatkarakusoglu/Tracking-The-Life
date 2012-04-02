package tracking.life.android.providers;

import java.util.HashMap;

import de.vogella.android.todos.database.TodoDatabaseHelper;

import tracking.life.android.database.DatabaseHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class TrackingTheLifeContentProvider extends ContentProvider {

	private static final String TAG = "TrackingTheLifeContentProvider";
	private static final String DATABASE_NAME = "trackingthelife.db";
	private static final int DATABASE_VERSION = 1;

	private static final String NOTES_TABLE_NAME = "notes";

	// TABLE NAMES
	public static final String TABLE_MEMBER_NAME = "member";
	public static final String TABLE_TASK_LIST_NAME = "task_list";
	public static final String TABLE_CONTACT_CATEGORY_NAME = "contact_category";
	public static final String TABLE_CONTACT_LIST_NAME = "contact_list";
	public static final String TABLE_LOCATION_CATEGORY_NAME = "location_category";
	public static final String TABLE_LOCATION_HISTORY_NAME = "location_history";
	public static final String TABLE_LOCATION_LIST_NAME = "location_list";
	public static final String TABLE_LOCATION_NOTE_NAME = "location_note";
	// END TABLE NAMES

	public static final String AUTHORITY = "tracking.life.android.providers.TrackingTheLifeContentProvider";

	private static final int TABLE_MEMBER_URI_INDICATOR = 1;
	private static final int TABLE_TASK_LIST_URI_INDICATOR = 2;
	private static final int TABLE_CONTACT_CATEGORY_URI_INDICATOR = 3;
	private static final int TABLE_CONTACT_LIST_URI_INDICATOR = 4;
	private static final int TABLE_LOCATION_CATEGORY_URI_INDICATOR = 5;
	private static final int TABLE_LOCATION_HISTORY_URI_INDICATOR = 6;
	private static final int TABLE_LOCATION_LIST_URI_INDICATOR = 7;
	private static final int TABLE_LOCATION_NOTE_URI_INDICATOR = 8;

	private static final UriMatcher sUriMatcher;
	private static HashMap<String, String> notesProjectionMap;
	
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, TABLE_MEMBER_NAME, TABLE_MEMBER_URI_INDICATOR);
		sUriMatcher.addURI(AUTHORITY, TABLE_TASK_LIST_NAME, TABLE_TASK_LIST_URI_INDICATOR);
		sUriMatcher.addURI(AUTHORITY, TABLE_CONTACT_CATEGORY_NAME, TABLE_CONTACT_CATEGORY_URI_INDICATOR);
		sUriMatcher.addURI(AUTHORITY, TABLE_CONTACT_LIST_NAME, TABLE_CONTACT_LIST_URI_INDICATOR);
		sUriMatcher.addURI(AUTHORITY, TABLE_LOCATION_CATEGORY_NAME, TABLE_LOCATION_CATEGORY_URI_INDICATOR);
		sUriMatcher.addURI(AUTHORITY, TABLE_LOCATION_HISTORY_NAME, TABLE_LOCATION_HISTORY_URI_INDICATOR);
		sUriMatcher.addURI(AUTHORITY, TABLE_LOCATION_LIST_NAME, TABLE_LOCATION_LIST_URI_INDICATOR);
		sUriMatcher.addURI(AUTHORITY, TABLE_LOCATION_NOTE_NAME, TABLE_LOCATION_NOTE_URI_INDICATOR);

//		notesProjectionMap = new HashMap<String, String>();
//		notesProjectionMap.put(Notes.NOTE_ID, Notes.NOTE_ID);
//		notesProjectionMap.put(Notes.TITLE, Notes.TITLE);
//		notesProjectionMap.put(Notes.TEXT, Notes.TEXT);

	}
	
	//private static final int NOTES = 1;

	//

	// database
	private DatabaseHelper database;

	// private static class DatabaseHelper extends SQLiteOpenHelper {
	//
	// DatabaseHelper(Context context) {
	// super(context, DATABASE_NAME, null, DATABASE_VERSION);
	// }
	//
	// @Override
	// public void onCreate(SQLiteDatabase db) {
	// db.execSQL("CREATE TABLE " + NOTES_TABLE_NAME + " (" + Notes.NOTE_ID
	// + " INTEGER PRIMARY KEY AUTOINCREMENT," + Notes.TITLE + " VARCHAR(255),"
	// + Notes.TEXT
	// + " LONGTEXT" + ");");
	// }
	//
	// @Override
	// public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	// {
	// Log.w(TAG, "Upgrading database from version " + oldVersion + " to " +
	// newVersion
	// + ", which will destroy all old data");
	// db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME);
	// onCreate(db);
	// }
	// }

	// private DatabaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		database = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = database.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case TABLE_MEMBER_URI_INDICATOR:
			count = db.delete(TABLE_MEMBER_NAME, where, whereArgs);
			break;
		case TABLE_TASK_LIST_URI_INDICATOR:
			count = db.delete(TABLE_TASK_LIST_NAME, where, whereArgs);
			break;
		case TABLE_CONTACT_CATEGORY_URI_INDICATOR:
			count = db.delete(TABLE_CONTACT_CATEGORY_NAME, where, whereArgs);
			break;
		case TABLE_CONTACT_LIST_URI_INDICATOR:
			count = db.delete(TABLE_CONTACT_LIST_NAME, where, whereArgs);
			break;
		case TABLE_LOCATION_CATEGORY_URI_INDICATOR:
			count = db.delete(TABLE_LOCATION_CATEGORY_NAME, where, whereArgs);
			break;
		case TABLE_LOCATION_HISTORY_URI_INDICATOR:
			count = db.delete(TABLE_LOCATION_HISTORY_NAME, where, whereArgs);
			break;
		case TABLE_LOCATION_LIST_URI_INDICATOR:
			count = db.delete(TABLE_LOCATION_NOTE_NAME, where, whereArgs);
			break;
		case TABLE_LOCATION_NOTE_URI_INDICATOR:
			count = db.delete(NOTES_TABLE_NAME, where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case NOTES:
			return Notes.CONTENT_TYPE;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (sUriMatcher.match(uri) != NOTES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		SQLiteDatabase db = database.getWritableDatabase();
		long rowId = db.insert(NOTES_TABLE_NAME, Notes.TEXT, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(Notes.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		switch (sUriMatcher.match(uri)) {
		case NOTES:
			qb.setTables(NOTES_TABLE_NAME);
			qb.setProjectionMap(notesProjectionMap);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase db = database.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase db = database.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case NOTES:
			count = db.update(NOTES_TABLE_NAME, values, where, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	// public static final Uri CONTENT_URI1 = Uri.parse("content://"+
	// PROVIDER_NAME + "/sampleuri1");
	// public static final Uri CONTENT_URI2 = Uri.parse("content://"+
	// PROVIDER_NAME + "/sampleuri2");


}
