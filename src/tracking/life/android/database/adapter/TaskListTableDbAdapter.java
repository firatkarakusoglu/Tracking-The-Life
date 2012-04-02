package tracking.life.android.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import tracking.life.android.database.DatabaseHelper;

public class TaskListTableDbAdapter {
	// Database fields
	public static final String DATABASE_TABLE = "task_list";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MEMBER_ID = "memberId";
	public static final String KEY_LOCATION_LIST_ID = "locationListId";
	public static final String KEY_TASK_DETAILS = "taskDetails";
	public static final String KEY_IS_NEAR_ALARM_ON = "isNearAlarmOn";
	public static final String KEY_TIME_STAMP = "timeStamp";
	public static final String KEY_IS_SYNCED= "isSynced";
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public TaskListTableDbAdapter(Context context) {
		this.context = context;
	}
	
	public TaskListTableDbAdapter open() throws SQLException {
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
	public long insertNewTask(long memberId, long locationListId, String taskDetails) {
		
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
    public Cursor selectTaskListByLocationId(long memberId, long locationId) {  
    	
    	String[] selectColumns = new String[]
		{
    			KEY_ROWID,
    			KEY_TASK_DETAILS,
    			KEY_IS_NEAR_ALARM_ON,
    			KEY_TIME_STAMP,
    			KEY_IS_SYNCED
		};
    	String selectWhere = KEY_MEMBER_ID + "=" + memberId + " AND "+ KEY_LOCATION_LIST_ID + "=" + locationId;
        return database.query(DATABASE_TABLE, selectColumns, selectWhere , null, null, null, null);  
    }
}
