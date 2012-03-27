package tracking.life.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName="trackingTheLifeAndroidDB";
	
	static final String userInfoTable="UserInfo";
	static final String colUserId="UserId";
	static final String colUserFirstName="EmployeeName";
	static final String colUserLastName="Age";
	static final String colUserMobileNumber="MobileNumber";
	static final String colUserEmail="Email";
	static final String colUserUsername="Username";
	static final String colUserPassword="Password";
	static final String colUserIsActive="IsActive";
	
	
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, 33);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		 db.execSQL("CREATE TABLE "+userInfoTable+" ("+colUserId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+  
		 colUserFirstName+" TEXT,"+ 
				 colUserLastName  +" TEXT , "+
				 colUserMobileNumber +" TEXT, "+
				 colUserEmail +" TEXT, "+
				 colUserUsername +" TEXT, "+
				 colUserPassword +" TEXT, "+
				 colUserIsActive +" TEXT;");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
