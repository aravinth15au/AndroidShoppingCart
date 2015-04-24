package com.samsung.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.samsung.database.DatabaseHelper;
import com.samsung.model.Cart;
import com.samsung.model.Product;
import com.samsung.model.R;
import com.samsung.view.adapter.InventoryListViewAdapter;
import com.samsung.xml.model.Choice;
import com.samsung.xml.model.Device;
import com.samsung.xml.parsing.XmlParsingHandler;






public class InventoryListActivity extends ListActivity {

	
	private final String TAG = getClass().getSimpleName();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.main);

		try {
			 processInventory();
		
		} catch (Exception e) {
			Log.e(TAG, "Error while parsing", e);
		}

	}
	protected Map<Integer, MenuItem> menuItemsMap;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuItemsMap = new HashMap<Integer, MenuItem>();

		menuItemsMap.put(
				R.string.inventory,
				menu.add(R.string.inventory).setIcon(
						R.drawable.application_view_list));
		menuItemsMap.put(
				R.string.checkout,
				menu.add(R.string.checkout).setIcon(
						R.drawable.cart));
		menuItemsMap.put(
				R.string.order,
				menu.add(R.string.order).setIcon(
						R.drawable.script));
		

		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 if (item.getTitle().equals(
				getString(R.string.inventory))) {
			 Intent intent = new Intent(this, InventoryListActivity.class);
				startActivity(intent);

		} else if (item.getTitle().equals(
				getString(R.string.order))) {
			Intent intent = new Intent(this, OrderListActivity.class);
			startActivity(intent);

		} else if (item.getTitle().equals(
				getString(R.string.checkout))) {
			Intent intent = new Intent(this, CartListActivity.class);
			startActivity(intent);
		}

		return true;
	}
	@Override
	public void onOptionsMenuClosed(Menu menu) {
	openOptionsMenu();
	}
	private void processInventory() throws XmlPullParserException,
			IOException {

		
		ListView listView =  getListView(); 
		final String PREFS_NAME = "MyPrefsFile";

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		List<Product> productList = new LinkedList<Product>();
		if (settings.getBoolean("my_first_time", true)) {
		    //the app is being launched for first time, do something        
		    Log.d("Comments", "First time");
		   
		    DatabaseHelper databaseH = new DatabaseHelper(InventoryListActivity.this);
		    productList= XmlParsingHandler.parseXml(this);
            databaseH.onCreate();
            // CREATE
        	for(Product pro: productList){
            if (databaseH.createProduct(pro)) {
                Log.d(this.TAG, "Product successfully created.");
            }
        	}
        	productList= databaseH.readProducts();
		        
		    settings.edit().putBoolean("my_first_time", false).commit(); 
		}
		else{
			DatabaseHelper databaseH = new DatabaseHelper(InventoryListActivity.this);
        	productList= databaseH.readProducts();
        	
		}
		
	
		getListView().setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Product  product = (Product) InventoryListActivity.this.getListAdapter()
								.getItem(position);
						

						addtoCartDialog(product);
					}
				});
		            
		 setListAdapter(new InventoryListViewAdapter(this,productList));
		        	
		        	
	}
	

	@SuppressLint("NewApi")
	protected void addtoCartDialog(final Product product) {
		 final AlertDialog alertDialog = new AlertDialog.Builder(
				InventoryListActivity.this).create();

		alertDialog.setTitle(getResources().getString(
				R.string.app_name, product.getName()));
		alertDialog.setMessage(getResources().getString(
				R.string.chk));
		//alertDialog.setIcon(R.drawable.cart);
		 final EditText input = new EditText(this); 
		 input.setId(0);
		 input.setInputType(InputType.TYPE_CLASS_NUMBER );
		 alertDialog.setView(input);
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources()
				.getString(R.string.yes),
				new DialogInterface.OnClickListener() {
					@SuppressLint("NewApi")
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(InventoryListActivity.this,CartListActivity.class);
						int q= Integer.parseInt(input.getText().toString());
						product.setCartquantity(q);
						if (q>product.getQuantity()){
							Toast.makeText(InventoryListActivity.this,
									R.string.notadded, Toast.LENGTH_LONG)
									.show();
						}
						else{
						Cart cart= (Cart) getApplication();
						boolean alreadyPresent=false;
						LinkedList<Product> pl = (LinkedList<Product>) cart.getCart();
						for(Product p: pl){
							if(p.getProductid()==product.getProductid()){
								p.setCartquantity(q);
								alreadyPresent=true;
							}
						}
						if(!alreadyPresent)
						pl.add(product);
						cart.setCart(pl);
						startActivity(intent);
						Toast.makeText(InventoryListActivity.this,
								R.string.added, Toast.LENGTH_LONG)
								.show();
					}
					}
				});

		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources()
				.getString(R.string.no), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
				
			}
		});

		alertDialog.show();
	}


	

}