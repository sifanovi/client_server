package com.ovi_rahim.online;




import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class Product extends Activity implements OnItemSelectedListener {
    
	
	
	String product_name;
	String product ;
	Button selectpro;
	
	
	private Spinner spinnerproduct;
	
	// array list for spinner adapter
	private ArrayList<Get_product> ProductList;
	
	ProgressDialog pDialog;
	ProgressDialog pDialog1;

	String p_name;

	
	private String get_product = "http://raahimalamincse.com/sample/getproduct.php";
	
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product);
		
		p_name=getIntent().getStringExtra("product");
		
		selectpro=(Button) findViewById(R.id.SelectProduct);
		
     
	
		spinnerproduct = (Spinner) findViewById(R.id.spinProduct);
		
		
		ProductList = new ArrayList<Get_product>();

		// spinner item select listener
		spinnerproduct.setOnItemSelectedListener(this);
		

		// Add new category click event
new  get_product().execute(p_name);


		selectpro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Product.this,Product_details.class);
				i.putExtra("product",product_name);
				startActivity(i);
			}
		});

	}


	
	private void populateSpinner() {
		List<String> lables = new ArrayList<String>();
		
		

		for (int i = 0; i < ProductList.size(); i++) {
			lables.add(ProductList.get(i).getProduct_name());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerproduct.setAdapter(spinnerAdapter);
	}

	/**
	 * Async task to get all food categories
	 * */
	private class get_product extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Product.this);
			pDialog.setMessage("Fetching Products..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... arg) {
			String product_name=arg[0];
		
			
			List<NameValuePair> param=new ArrayList<NameValuePair>(); 
			param.add(new BasicNameValuePair("pm",product_name));
			
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(get_product, ServiceHandler.GET,param);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray categories = jsonObj
								.getJSONArray("product_get");						

						for (int i = 0; i < categories.length(); i++) {
							JSONObject catObj = (JSONObject) categories.get(i);
						 Get_product pro = new Get_product(catObj.getInt("product_id"),catObj.getString("product_name"));
							ProductList.add(pro);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			populateSpinner();
			
			}
				
				
		
			
			
		}
	
	
	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		product_name=parent.getItemAtPosition(position).toString();
		Toast.makeText(
				getApplicationContext(),
						parent.getItemAtPosition(position).toString() + " Selected" ,
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {		
	}
}
