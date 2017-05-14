package com.ovi_rahim.online;



import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends Activity implements OnItemSelectedListener {
    
	
	
	String category ;
	Button select;
	
	
	private Spinner spinnercategory;
	// array list for spinner adapter
	private ArrayList<Category> categoriesList;
	ProgressDialog pDialog;

	
	// API urls
	// Url to create new category
//	private String URL_NEW_CATEGORY = "http://raahimalamincse.com/sample/new_category.php";
	// Url to get all categories
	private String URL_CATEGORIES = "http://raahimalamincse.com/sample/product.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		select=(Button) findViewById(R.id.Select);
		

	
		spinnercategory = (Spinner) findViewById(R.id.spinFood);
		
		
		categoriesList = new ArrayList<Category>();

		// spinner item select listener
		spinnercategory.setOnItemSelectedListener(this);

		// Add new category click event


		new GetCategories().execute();
		select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Intent i=new Intent(MainActivity.this,Product.class);
				i.putExtra("product",category);
				startActivity(i);
			}
		});

	}

	/**
	 * Adding spinner data
	 * */
	private void populateSpinner() {
		List<String> lables = new ArrayList<String>();
		
		

		for (int i = 0; i < categoriesList.size(); i++) {
			lables.add(categoriesList.get(i).getName());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnercategory.setAdapter(spinnerAdapter);
	}

	/**
	 * Async task to get all food categories
	 * */
	private class GetCategories extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Fetching Producr  categories..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray categories = jsonObj
								.getJSONArray("product");						

						for (int i = 0; i < categories.length(); i++) {
							JSONObject catObj = (JSONObject) categories.get(i);
							Category cat = new Category(catObj.getInt("category_id"),
									catObj.getString("category_name"));
							categoriesList.add(cat);
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

	/**
	 * Async task to create a new food category
	 * */

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		category=parent.getItemAtPosition(position).toString();
		Toast.makeText(
				getApplicationContext(),
						parent.getItemAtPosition(position).toString() + " Selected" ,
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {		
	}
}
