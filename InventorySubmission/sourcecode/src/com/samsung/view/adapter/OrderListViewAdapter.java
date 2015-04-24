package com.samsung.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsung.model.Product;
import com.samsung.model.R;
import com.samsung.model.Transaction;
import com.samsung.view.adapter.InventoryListViewAdapter.ViewHolder;

public class OrderListViewAdapter extends BaseAdapter {
	protected final LayoutInflater inflater;
	private final Context context;
	private final List<? extends Transaction> orders;
	public OrderListViewAdapter(Context context,List<? extends Transaction> products) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.orders = products;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orders.size();
	}
	@Override
	public Transaction getItem(int position) {
		// TODO Auto-generated method stub
		return orders.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.orderlist_view_tablelayout, null);

			holder = getViewHolder(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		customizeView(position, holder);

		return convertView;
	}
	protected void customizeView(int position, ViewHolder holder) {
		Transaction product = orders.get(position);
		customizeView(product, holder);
	}

	
	protected <T extends Transaction> void customizeView(T order, ViewHolder holder) {
		

		holder.getNameView().setText("Order ID "+ order.getOrderid());

		

	}

	

	protected ViewHolder getViewHolder(View view) {
		
		TextView nameView = (TextView) view.findViewById(R.id.orderList_name);
		
		ImageView removeImage = (ImageView) view
				.findViewById(R.id.imageView1);
		

		ViewHolder holder = new ViewHolder( nameView,
				removeImage);

		return holder;
	}
	protected static class ViewHolder {
	
		private TextView nameView;
		
		private ImageView removeImage;
		

		public ViewHolder(TextView nameView, ImageView removeImage) {
			super();
			this.nameView = nameView;
			this.removeImage = removeImage;
			
		}

	

		public TextView getNameView() {
			return nameView;
		}

		

		public ImageView getRemoveImage() {
			return removeImage;
		}

		
	}
	
	

}
