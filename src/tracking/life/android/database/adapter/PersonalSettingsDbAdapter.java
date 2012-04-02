package tracking.life.android.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import tracking.life.android.database.DatabaseHelper;

public class PersonalSettingsDbAdapter {
	// Database fields
	public static final String DATABASE_TABLE = "personal_settings";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MEMBER_ID = "memberId";	
	public static final String KEY_DEFAULT_MAP_VIEW = "defaultMapView";
	public static final String KEY_DEFAULT_NOTE_BAR_ORDER = "defaultNoteBarOrder";
	public static final String KEY_DEFAULT_TASK_BAR_ORDER = "defaultTaskBarOrder";
	public static final String KEY_LOCATION_BAR_ORDER = "defaultLocationBarOrder";
	public static final String KEY_CONTACT_BAR_ORDER = "defaultContactBarOrder";
	public static final String KEY_IS_AUTO_CHECK_IN_ON = "isAutoCheckInOn";
	public static final String KEY_LOCATION_CHECKING_FREQUENCY = "locationCheckingFrequency";
	public static final String KEY_IS_OVERRIDE_PHONE_SOUND = "isOverridePhoneSound";
	public static final String KEY_IS_SYNCED= "isSynced";
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public PersonalSettingsDbAdapter(Context context) {
		this.context = context;
	}
	
	public PersonalSettingsDbAdapter open() throws SQLException {
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
	public long insertNewPersonalSettingsByMemberId(long memberId) {
		
		ContentValues values = new ContentValues();
		values.put(KEY_MEMBER_ID, memberId);
		return database.insert(DATABASE_TABLE, null, values);
	}

	 /** 
     * Update the item
     */
    public boolean updateIsSynced(long rowId, int isSynced) {  
        ContentValues values = new ContentValues();
        values.put(KEY_IS_SYNCED, isSynced);
        return database.update(DATABASE_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;  
    }
    
    public boolean markAsSynced(long rowId) {  
        return  updateIsSynced(rowId, 1);
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
    public Cursor selectMemberByMemberId(long memberId) {  
    	
    	String[] selectColumns = new String[]
		{
    			KEY_ROWID,	
    			KEY_DEFAULT_MAP_VIEW ,
    			KEY_DEFAULT_NOTE_BAR_ORDER  ,
    			KEY_DEFAULT_TASK_BAR_ORDER  ,
    			KEY_LOCATION_BAR_ORDER  ,
    			KEY_CONTACT_BAR_ORDER ,
    			KEY_IS_AUTO_CHECK_IN_ON  ,
    			KEY_LOCATION_CHECKING_FREQUENCY  ,
    			KEY_IS_OVERRIDE_PHONE_SOUND  ,
    			KEY_IS_SYNCED
		};
    	String selectWhere = KEY_ROWID + "=" + memberId;
        return database.query(DATABASE_TABLE, selectColumns, selectWhere , null, null, null, null);  
    }
    
}
