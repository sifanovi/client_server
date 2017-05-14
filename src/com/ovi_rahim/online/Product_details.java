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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Product_details extends Activity  {
    
	
	
	TextView t;
	LinearLayout.LayoutParams param; 
	
	LinearLayout l;
	
	// array list for spinner adapter
	private ArrayList<Get_product> ProductList;
//	private ArrayList<details> dlist;
	ProgressDialog pDialog;
	ProgressDialog pDialog1;
   ArrayList<TextView> prices;
   ArrayList<TextView> details;
   
	String p_name;

	// API urls
	// Url to create new category
//	private String get_product = "http://raahimalamincse.com/sample/getproduct.php";
	private String get_detail = "http://raahimalamincse.com/sample/details.php";
	
	// Url to get all categories


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		p_name=getIntent().getStringExtra("product");
		
		
		 l=new LinearLayout(this);
		 l.setOrientation(LinearLayout.VERTICAL);
		 l.setGravity(Gravity.CENTER);
		 param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		 setContentView(l,param);
		
		 
		 
		
		
		
		
    
	
		
		
		ProductList = new ArrayList<Get_product>();

		prices=new ArrayList<TextView>();
		details=new ArrayList<TextView>();
		// spinner item select listener
		
		// Add new category click event
         new  get_product().execute(p_name);


		

	}

	/**
	 * Adding spinner data
	 * 
	 * 
	 * 
	 * 
	 * */
	
	
	
	
	///////
	
	

	/**
	 * Async task to get all food categories
	 * */
	private class get_product extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Product_details.this);
			pDialog.setMessage("Fetching Products..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... arg) {
			String product_name=arg[0];
		
			
			List<NameValuePair> param=new ArrayList<NameValuePair>(); 
			param.add(new BasicNameValuePair("ps",product_name));
			
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(get_detail, ServiceHandler.GET,param);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray categories = jsonObj
								.getJSONArray("detail");						

						for (int i = 0; i < categories.length(); i++) {
							JSONObject catObj = (JSONObject) categories.get(i);
						 Get_product pro = new Get_product(catObj.getInt("product_price"),catObj.getString("product_detail"));
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
			setview();
			
		}
			
			}
			
				
				
		public void setview()
		{
			for(int i=0;i<ProductList.size();i++)
			 {
				
				 TextView t=new  TextView(this);
				 t.setGravity(Gravity.CENTER);
				 t.setBackgroundResource(R.drawable.button_design);
				 prices.add(t);
				 
				 prices.get(i).setText(String.valueOf("Price of the Product:\n"+ProductList.get(i).getProduct_Id()));
				 l.addView(t);
				 
				 TextView t1=new  TextView(this);
				 t1.setGravity(Gravity.CENTER);
				 t1.setBackgroundResource(R.drawable.button_design);
				 details.add(t1);
				 
				 details.get(i).setText("Details of the Product:\n" +ProductList.get(i).getProduct_name());
				 l.addView(t1);
				 
				 
				
			 }
			 
		}
			
}		
		
	
	
	
	
	
	
	
	
	

