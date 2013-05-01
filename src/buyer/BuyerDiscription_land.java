package buyer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import seller.act.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuyerDiscription_land extends Activity {

	String land_type, location, contact;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.buyer_discription_land);
		Button land_request = (Button) findViewById(R.id.buyer_land_request);
		land_request.setOnClickListener(new View.OnClickListener() {
			TextView land_typeTextview = (TextView) findViewById(R.id.buyer_land_typeText);
			TextView land_locationTextview = (TextView) findViewById(R.id.buyer_land_locationText);
			TextView land_contactTextview = (TextView) findViewById(R.id.buyer_land_ContactText);

			@Override
			public void onClick(View arg0) {
				
				land_type = land_typeTextview.getText().toString();
				location = land_locationTextview.getText().toString();
				contact = land_contactTextview.getText().toString();
				new AsyncServerConnect().execute();

			}
		});

		Button exit = (Button) findViewById(R.id.buyer_landExit);
		exit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();

			}
		});

	}

	private class AsyncServerConnect extends AsyncTask<Void, Void, String> {
		private String METHOD_NAME = "insertToBuyerLand";
		// our webservice method name
		private String NAMESPACE = "http://dbase.com";
		// Here package name in webservice with reverse order.
		private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		// NAMESPACE + method name
		private static final String URL = "http://10.0.2.2:8080/EBrokeWebServices/services/DataBaseConnection_Webservice?wsdl";

		@Override
		protected String doInBackground(Void... arg0) {
			String test = "";
			try {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

				PropertyInfo typeProp = new PropertyInfo();
				typeProp.setName("land_type");
				typeProp.setValue(land_type);
				typeProp.setType(String.class);
				request.addProperty(typeProp);

				PropertyInfo locationProp = new PropertyInfo();
				locationProp.setName("location");
				locationProp.setValue(location);
				locationProp.setType(String.class);
				request.addProperty(locationProp);

				PropertyInfo contactProp = new PropertyInfo();
				contactProp.setName("contact");
				contactProp.setValue(contact);
				contactProp.setType(String.class);
				request.addProperty(contactProp);

				String deviceId = Settings.System.getString(
						getContentResolver(), Settings.System.ANDROID_ID);

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
				test = result.toString();
				if (test.equals("true")) {
					TextView status = (TextView) findViewById(R.id.buyer_status);
					status.setText("Succesfully Inserted");
				} else {
					TextView status = (TextView) findViewById(R.id.buyer_status);
					status.setText("Insertion Unsucessful!");
				}

			} catch (Exception e) {
				Log.i("Webservice accessing", e.getMessage());
			}
			return test;
		}

	}
}
