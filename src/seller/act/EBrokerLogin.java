package seller.act;

import buyer.BuyerSelection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * @author Compaq
 * 
 */
public class EBrokerLogin extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button logbutton = (Button) findViewById(R.id.loginbutton);
		String AndroidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
		System.out.println("My ID is: " + AndroidId);
		/**
		 * when the login button clicks the following methods will run
		 */
		logbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RadioButton loginRadio = (RadioButton) findViewById(R.id.loginbuyer);                        //reference loginradio button
				if (loginRadio.isChecked()) {
					Intent i = new Intent(EBrokerLogin.this,BuyerSelection.class);                           
					startActivity(i);
				} else {
					Intent i = new Intent(EBrokerLogin.this,
							SellerSelection.class);
					startActivity(i);
				}

			}
		});
	
	}
}