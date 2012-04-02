package tracking.life.android;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import tracking.life.android.database.adapter.MemberDbAdapter;

public class SignupEmail extends Activity {

	private EditText mFirstName, mLastName, mEmail, mPassword;
	private Long memberId;
	private MemberDbAdapter memberDbHelper;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		memberDbHelper = new MemberDbAdapter(this);
		memberDbHelper.open();

		setContentView(R.layout.signup_email);

		mFirstName = (EditText) findViewById(R.id.firstName);
		mLastName = (EditText) findViewById(R.id.lastName);
		mEmail = (EditText) findViewById(R.id.email);
		mPassword = (EditText) findViewById(R.id.password);

		Button joinButton = (Button) findViewById(R.id.signup_email_join);
		memberId = null;
		Bundle extras = getIntent().getExtras();
		memberId = (bundle == null) ? null : (Long) bundle.getSerializable(MemberDbAdapter.KEY_ROWID);
		
		if (extras != null) {
			memberId = extras.getLong(MemberDbAdapter.KEY_ROWID);
		}

        populateFields(); 
        
		joinButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_OK);
				//INSERT MEMBER DATA TO WEBSERVER
				insertMember();
				finish();
			}

		});
	}

    private void populateFields() {  
        if (memberId != null) {  
            Cursor memberData = memberDbHelper.selectMemberByMemberId(memberId);  
            startManagingCursor(memberData);  
   
            mFirstName.setText(memberData.getString(memberData.getColumnIndexOrThrow(MemberDbAdapter.KEY_FIRST_NAME)));  
            mLastName.setText(memberData.getString(memberData.getColumnIndexOrThrow(MemberDbAdapter.KEY_LAST_NAME)));
            mEmail.setText(memberData.getString(memberData.getColumnIndexOrThrow(MemberDbAdapter.KEY_EMAIL)));
            
        }  
    }
	
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(MemberDbAdapter.KEY_ROWID, memberId);
	}

    @Override  
    protected void onPause() {  
        super.onPause();  
        saveState();  
    }  
  
    @Override  
    protected void onResume() {  
        super.onResume();  
        populateFields();  
    }  
	
	//context is saved temporarily into the database when paused
	private void insertMember() {

		String firstName = (String) mFirstName.getText().toString();
		String lastName = (String) mLastName.getText().toString();
		String email = (String) mEmail.getText().toString();
		String password = (String) mPassword.getText().toString();

		memberDbHelper.insertNewMember(email, firstName, lastName, password);
		
	}
    
	//context is saved temporarily into the database when paused
	private void saveState() {

//		String firstName = (String) mFirstName.getText().toString();
//		String lastName = (String) mLastName.getText().toString();
//		String email = (String) mEmail.getText().toString();
//		String password = (String) mPassword.getText().toString();
//
//		memberDbHelper.saveContexToTemp(email, firstName, lastName, password);
//		
//		memberId = (long) 1; //temp data is saved to the first row

	}

}
