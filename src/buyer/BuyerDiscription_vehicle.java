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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuyerDiscription_vehicle extends Activity {

	String brand, type, contact;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.buyer_discription_vehicle);
		Button requestButton = (Button) findViewById(R.id.buyer_vehicle_requestButton);

		requestButton.setOnClickListener(new View.OnClickListener() {
			TextView brandText = (TextView) findViewById(R.id.buyer_vehicle_barandTextview);
			TextView typeText = (TextView) findViewById(R.id.buyer_vehicle_typeTextview);
			TextView contactText = (TextView) findViewById(R.id.buyer_vehicle_contactTextview);

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				brand = brandText.getText().toString();
				type = typeText.getText().toString();
				contact = contactText.getText().toString();

				new AsyncServerConnect().execute();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		Button exit = (Button) findViewById(R.id.buyer_vehicle_exit);
		exit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private class AsyncServerConnect extends AsyncTask<Void, Void, String> {
		private String METHOD_NAME = "insertToBuyerVehicle";
		// our webservice method name
		private String NAMESPACE = "http://dbase.com";
		// Here package name in webservice with reverse order.
		private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		// NAMESPACE + method name
		private static final String URL = "http://10.0.2.2:8080/EBrokeWebServices/services/DataBaseConnection_Webservice?wsdl";

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String test = "";
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
				System.out.println(test);
				if (test.equals("true")) {
					TextView status = (TextView) findViewById(R.id.buyer_vehicle_status);
					status.setText("SucessfullynInserted");
				} else {
					TextView status = (TextView) findViewById(R.id.buyer_vehicle_status);
					status.setText("Insertion unsuccessful!");
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error - " + e);
			}
			return test;
		}

	}

}
