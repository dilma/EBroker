package seller.act;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SellerDiscription_land extends Activity {

	String type_land, location, discription;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.seller_discription_land);
		Button landSubmit = (Button) findViewById(R.id.land_submit);
		landSubmit.setOnClickListener(new View.OnClickListener() {
			TextView seller_land_typeTextview = (TextView) findViewById(R.id.seller_land_typeText);
			TextView seller_land_locationTextView = (TextView) findViewById(R.id.seller_land_locationText);
			TextView seller_land_discriptionTextview = (TextView) findViewById(R.id.seller_land_discriptionText);

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				type_land = seller_land_typeTextview.getText().toString();
				location = seller_land_locationTextView.getText().toString();
				discription = seller_land_discriptionTextview.getText()
						.toString();
				new AsyncServerConnect().execute();
			}
		});
	}

	private class AsyncServerConnect extends AsyncTask<Void, Void, String> {
		private String METHOD_NAME = "insertToSellerLand";
		// our webservice method name
		private String NAMESPACE = "http://dbase.com";
		// Here package name in webservice with reverse order.
		private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		// NAMESPACE + method name
		private static final String URL = "http://10.0.2.2:8080/EBrokeWebServices/services/DataBaseConnection_Webservice?wsdl";

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String message = "";
			int k = 0;
			try {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

				PropertyInfo brandProp = new PropertyInfo();
				brandProp.setName("type_land");
				brandProp.setValue(type_land);
				brandProp.setType(String.class);
				request.addProperty(brandProp);

				PropertyInfo typeProp = new PropertyInfo();
				typeProp.setName("location");
				typeProp.setValue(location);
				typeProp.setType(String.class);
				request.addProperty(typeProp);

				PropertyInfo discriptionProp = new PropertyInfo();
				discriptionProp.setName("discription");
				discriptionProp.setValue(discription);
				discriptionProp.setType(String.class);
				request.addProperty(discriptionProp);
				

				String deviceId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
				PropertyInfo diviceIDProp = new PropertyInfo();
				diviceIDProp.setName("diviceID");
				diviceIDProp.setValue(deviceId);
				diviceIDProp.setType(String.class);
				request.addProperty(diviceIDProp);

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);
				Object result = envelope.getResponse();
				String dd = result.toString();
				String[] parts = dd.split("[,\\s\\-:\\?\\]\\[\\ ]");
				message = "Land Type - " + type_land + "\n" + "Location - "
						+ location + "\n" + "Discription - " + discription+"\n"+"Reference -"+deviceId;
				if (parts.length == 1) {
					k = 0;
				} else {
					k = 1;
				}

				for (int i = k; i < parts.length; i += 2) {
					System.out.println("Contact- " + parts[i]);
					sendSMS(parts[i], message);
					Thread.sleep(1000);
				}
				k = 0;

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error in ksoap-" + e);
			}
			return message;
		}
	}

	/**
	 * ..
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	private void sendSMS(String phoneNumber, String message) {
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				SellerDiscription_land.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, pi, null);

	}
}
