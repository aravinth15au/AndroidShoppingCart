package com.samsung.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.model.Product;
import com.samsung.model.R;
import com.samsung.view.adapter.InventoryListViewAdapter.ViewHolder;

public class CartListViewAdapter extends InventoryListViewAdapter {

	public CartListViewAdapter(Context context, List<? extends Product> products) {
		super(context, products);
		
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void customizeView(int position, ViewHolder holder) {
		// TODO Auto-generated method stub
		super.customizeView(position, holder);
	}
	
	protected <T extends Product> void customizeView(T product, ViewHolder holder) {

		holder.getQuantityView().setText("Qty: "+Integer.toString(product.getCartquantity()));
		holder.getNameView().setText(
				product.getName() + "  " + product.getModel() + "  "
						+ product.getPrice());

		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.cartlist_view_tablelayout, null);

			holder = getViewHolder(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		customizeView(position, holder);

		return convertView;
	}

}
