package com.samsung.activity;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.samsung.database.DatabaseHelper;
import com.samsung.model.Cart;
import com.samsung.model.Product;
import com.samsung.model.R;
import com.samsung.model.Transaction;
import com.samsung.view.adapter.CartListViewAdapter;
import com.samsung.view.adapter.InventoryListViewAdapter;








import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
// reviewed
public class CartListActivity extends ListActivity {

	// ------------------------------------------------------------------
	// Activity overrides
	// ------------------------------------------------------------------
	protected Map<Integer, MenuItem> menuItemsMap;
DatabaseHelper dbhelper = new DatabaseHelper(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.cart_full_layout);
		final Cart cart= (Cart) getApplication();
		setListAdapter(new CartListViewAdapter(this,cart.getCart()));
		final Button button = (Button) findViewById(R.id.checkout);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
			public void onClick(View v) {
                // Perform action on click
            	int id = dbhelper.createOrderId();
            	int totalprice = 0;
            	if(cart.getCart().size()==0){
            		Toast.makeText(CartListActivity.this,
    						R.string.cartempty, Toast.LENGTH_LONG)
    						.show();
            	}
            	else{
            	for (Product p: cart.getCart()){
            		Transaction tr = new Transaction();
            		tr.setQuantity(p.getCartquantity());
            		tr.setProductid(p.getProductid());
            		tr.setOrderid(id);
            		totalprice+= tr.getQuantity()*Integer.parseInt(p.getPrice().substring(1));
            		dbhelper.updateProduct(p.getQuantity()-p.getCartquantity(), p.getProductid());
            		dbhelper.createTransaction(tr);
            	}
            cart.getCart().clear();
            	Intent intent = new Intent(CartListActivity.this,OrderListActivity.class);
            	startActivity(intent);
            	
            	
            	}
            }
        });
        final Button button1 = (Button) findViewById(R.id.checkout1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	cart.getCart().clear();
            	Intent intent = new Intent(CartListActivity.this,
                        CartListActivity.class);

                startActivity(intent);
            	}
            
        });
		//TextView titleView = (TextView) findViewById(R.id.list_title);
		//titleView.setText(R.string.cart_title);
	}
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

	
}
