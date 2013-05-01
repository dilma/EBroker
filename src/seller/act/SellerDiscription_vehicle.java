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

/* This class is used to gather the property details*/

/**
 * @author Dilshan Madhawa
 * 
 */
public class SellerDiscription_vehicle extends Activity {

	String brand, type, location, discription;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.seller_discription_vehicle);
		Button submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			TextView brandtextview = (TextView) findViewById(R.id.selDiscripBrand);
			TextView typetextview = (TextView) findViewById(R.id.sellerDiscriptionType);
			TextView locationtextview = (TextView) findViewById(R.id.sellerDiscriptionLocation);
			TextView discriptiontextview = (TextView) findViewById(R.id.sellerDiscriptionDiscription);

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				brand = brandtextview.getText().toString();
				type = typetextview.getText().toString();
				location = locationtextview.getText().toString();
				discription = discriptiontextview.getText().toString();
				new AsyncServerConnect().execute();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}

			}
		});

	}

	private class AsyncServerConnect extends AsyncTask<Void, Void, String> {
		private String METHOD_NAME = "insertToSellerVehicle";	// our webservice method name
		
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
				brandProp.setName("brand");
				brandProp.setValue(brand);
				brandProp.setType(String.class);
				request.addProperty(brandProp);

				PropertyInfo typeProp = new PropertyInfo();
				typeProp.setName("type");
				typeProp.setValue(type);
				typeProp.setType(String.class);
				request.addProperty(typeProp);

				PropertyInfo locationProp = new PropertyInfo();
				locationProp.setName("location");
				locationProp.setValue(location);
				locationProp.setType(String.class);
				request.addProperty(locationProp);

				PropertyInfo discriptionProp = new PropertyInfo();
				discriptionProp.setName("discription");
				discriptionProp.setValue(discription);
				discriptionProp.setType(String.class);
				request.addProperty(discriptionProp);
				
				String deviceId = Settings.System.getString(
						getContentResolver(), Settings.System.ANDROID_ID);
				
				PropertyInfo deviceIDProp = new PropertyInfo();
				deviceIDProp.setName("deviceID");
				deviceIDProp.setValue(deviceId);
				deviceIDProp.setType(String.class);
				request.addProperty(deviceIDProp);

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);
				Object result = envelope.getResponse();
				String dd = result.toString();
				String[] parts = dd.split("[,\\s\\-:\\?\\]\\[\\ ]");
				message = "Vehicle Brand - " + brand + "\n" + "Type - " + type
						+ "\n" + "Location - " + location + "\n"
						+ "Discription - " + discription+"\n"+"Reference -"+deviceId;

				if (parts.length == 1) {
					k = 0;
				} else {
					k = 1;
				}

				for (int i = k; i < parts.length; i++) {
					System.out.println(parts[i]);
					sendSMS(parts[i], message);
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error- " + e);
			}
			return message;
		}

	}

	// SMS Class
	private void sendSMS(String phoneNumber, String message) {
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				SellerDiscription_vehicle.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, pi, null);

	}

}
