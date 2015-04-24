package com.samsung.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.samsung.database.DatabaseHelper;
import com.samsung.model.R;
import com.samsung.model.Transaction;
import com.samsung.view.adapter.OrderListViewAdapter;

public class OrderListActivity extends ListActivity {

	private final String TAG = getClass().getSimpleName();

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
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.main);

		try {
			showOrders();
			
		} catch (Exception e) {
			Log.e(TAG, "Error while parsing", e);
		}

	}

	private void showOrders() throws XmlPullParserException,
			IOException {

		
		
		List<Transaction> orderList = new LinkedList<Transaction>();
		
	
		
		            DatabaseHelper databaseH = new DatabaseHelper(OrderListActivity.this);
		            orderList= databaseH.readOrders();
		         
		        	getListView().setOnItemClickListener(
		    				new AdapterView.OnItemClickListener() {
		    					@Override
		    					public void onItemClick(AdapterView<?> parent, View view,
		    							int position, long id) {
		    						Transaction  order = (Transaction) OrderListActivity.this.getListAdapter()
		    								.getItem(position);
		    						

		    						showTransactions(order);
		    					}
		    				});
		    		setListAdapter(new OrderListViewAdapter(this,orderList));
		        	
		        	

		

	}
	

	@SuppressLint("NewApi")
	protected void showTransactions(final Transaction order) {
		
						
						Intent intent = new Intent(OrderListActivity.this,TransactionListActivity.class);
						intent.putExtra("orderid", order.getOrderid());
						startActivity(intent);
						
						
					}
				
	

}
