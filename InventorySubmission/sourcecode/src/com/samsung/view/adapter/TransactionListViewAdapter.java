package com.samsung.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsung.model.R;
import com.samsung.model.Transaction;
import com.samsung.view.adapter.OrderListViewAdapter.ViewHolder;

public class TransactionListViewAdapter  extends BaseAdapter {
	protected final LayoutInflater inflater;
	private final Context context;
	private final List<? extends Transaction> orders;
	public TransactionListViewAdapter(Context context,List<? extends Transaction> products) {
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
					R.layout.transactionlist_view_tablelayout, null);

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
		

		holder.getNameView().setText( order.getQuantity().toString()+"  "+order.getName()+"  "+order.getModel());
		holder.getPriceView().setText("");
		holder.getQuantityView().setText("");

		

	
	}

	
	protected void customizeRemoveImage(ImageView removeImage) {
		removeImage.setMaxWidth(0);
		removeImage.setVisibility(View.INVISIBLE);
	}
	protected ViewHolder getViewHolder(View view) {
		
		TextView nameView = (TextView) view.findViewById(R.id.trList_name);
		
		TextView quantityView = (TextView) view.findViewById(R.id.productsList_quantity);
		TextView priceView = (TextView) view
				.findViewById(R.id.productsList_price);
		
		

		ViewHolder holder = new ViewHolder(quantityView, nameView,
				priceView);

		return holder;
	}
	protected static class ViewHolder {
		private TextView quantityView;
		private TextView nameView;
		private TextView priceView;
		

		public ViewHolder(TextView quantityView, TextView nameView,
				TextView priceView) {
			super();
			this.quantityView = quantityView;
			this.nameView = nameView;
			this.priceView = priceView;
			
		}

		public TextView getQuantityView() {
			return quantityView;
		}

		public TextView getNameView() {
			return nameView;
		}

		public TextView getPriceView() {
			return priceView;
		}
		
	}
	
	

}


