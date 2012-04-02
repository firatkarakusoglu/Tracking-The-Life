package tracking.life.android.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import tracking.life.android.database.DatabaseHelper;

public class LocationListDbAdapter {
	// Database fields
	public static final String DATABASE_TABLE = "location_list";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MEMBER_ID = "memberId";
	public static final String KEY_CATEGORY_ID = "categoryId";
	public static final String KEY_TITLE= "title";
	public static final String KEY_LONGITUDE= "longitude";
	public static final String KEY_LATITUDE= "latitude";
	public static final String KEY_IS_FAVORITE= "isFavorite";
	public static final String KEY_IS_SYNCED= "isSynced";

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public LocationListDbAdapter(Context context) {
		this.context = context;
	}
	
	public LocationListDbAdapter open() throws SQLException {
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
	public long insertNewLocation(long memberId, String longitude, String latitude) {
		
		ContentValues values = new ContentValues();
		values.put(KEY_MEMBER_ID, memberId);
		values.put(KEY_LONGITUDE, longitude);
		values.put(KEY_LATITUDE, latitude);
		
		return database.insert(DATABASE_TABLE, null, values);
	}

	 /** 
     * Update the item 
     */  
    public boolean updateLocationTitle(long rowId, String locationTitle) {  
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_TITLE, locationTitle);
        
        return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;  
    }
	
    public boolean updateLocationCategoryId(long rowId, long locationCategoryId) {  
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_CATEGORY_ID, locationCategoryId);
        return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;  
    }
    
    public boolean updateIsFavorite(long rowId, int isFavorite) {  
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_IS_FAVORITE, isFavorite);
        return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;  
    }
    
    public boolean makeFavorite(long rowId) {  
        return  updateIsFavorite(rowId, 1);
    }
    
    public boolean makeNotFavorite(long rowId) {  
        return  updateIsFavorite(rowId, 0);
    }
    
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
    public Cursor selectAllLocationListByMemberId(long memberId) {  
    	
    	String[] selectColumns = new String[]
		{
			KEY_ROWID,
			KEY_MEMBER_ID,
			KEY_CATEGORY_ID,
			KEY_TITLE,
			KEY_LONGITUDE,
			KEY_LATITUDE,
			KEY_IS_FAVORITE,
			KEY_IS_SYNCED,
		}; 
    	String selectWhere = KEY_MEMBER_ID + "=" + memberId;
        return database.query(DATABASE_TABLE, selectColumns, selectWhere , null, null, null, null);  
    }
    
}
