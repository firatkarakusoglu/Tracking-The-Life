package tracking.life.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	// TextView is used

	private void turnGPSOnOff(){
		  String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		  if(!provider.contains("gps")){
		    final Intent poke = new Intent();
		    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		    poke.setData(Uri.parse("3")); 
		    sendBroadcast(poke);
		    Toast.makeText(this, "Your GPS is Enabled",Toast.LENGTH_SHORT).show();
		  }
		}
	
	//
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		// TODO Auto-generated method stub
		Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
		Button buttonRegister = (Button) findViewById(R.id.buttonRegister);

		final EditText textUsername = (EditText) findViewById(R.id.editTextUsername);
		final EditText textPassword = (EditText) findViewById(R.id.editTextPassword);

		final TextView textWarning = (TextView) findViewById(R.id.textViewWarning);

		buttonLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				textWarning.clearComposingText();

				String stringUsername = textUsername.getText().toString();
				String stringPassword = textPassword.getText().toString();

				if (stringUsername.equals("admin")
						&& stringPassword.equals("admin")) {
					startActivity(new Intent("tracking.life.android.LOGIN"));
				} else
					textWarning.setText("Username or password is wrong!");
			}
		});

	
		Button buttonGpsOnOff = (Button) findViewById(R.id.buttonGpsOnOff);
		buttonGpsOnOff.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				turnGPSOnOff();
			}
		});
	}
}
