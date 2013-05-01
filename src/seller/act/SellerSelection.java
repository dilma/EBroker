package seller.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.RadioButton;

public class SellerSelection extends Activity {

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.vehicle_or_land);
		TextView buyer_seller=(TextView) findViewById(R.id.buyer_seller);
		buyer_seller.setText("Seller");
		Button next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {

			// This code runs when next button clicks 
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				RadioButton vehicleRadioButton = (RadioButton) findViewById(R.id.vehicleradio);
				if (vehicleRadioButton.isChecked()) {
					Intent inten = new Intent(SellerSelection.this,SellerDiscription_vehicle.class); //move to the SellerDiscription_vehicle class
					startActivity(inten);
				}else{
					Intent inten = new Intent(SellerSelection.this,SellerDiscription_land.class);    //move to the SellerDiscription_land class
					startActivity(inten);
					
				}
			}
		});
	}

}
