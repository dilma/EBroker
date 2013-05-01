package buyer;
/*Buyer selection class*/
import seller.act.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class BuyerSelection extends Activity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.vehicle_or_land);
		TextView buyer_seller = (TextView) findViewById(R.id.buyer_seller);
		buyer_seller.setText("Buyer");
		Button next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {

		/**
		 * 	 This code runs when next button clicks
		 */

			@Override
			public void onClick(View view) {
				
				RadioButton vehicleRadioButton = (RadioButton) findViewById(R.id.vehicleradio);
				if (vehicleRadioButton.isChecked()) {
					Intent inten = new Intent(BuyerSelection.this,
							BuyerDiscription_vehicle.class);

					startActivity(inten);
				} else {
					Intent inten = new Intent(BuyerSelection.this,
							BuyerDiscription_land.class);
					startActivity(inten);
				}
			}
		});
	}

}
