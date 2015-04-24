package com.samsung.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.samsung.model.Product;

import com.samsung.model.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InventoryListViewAdapter extends BaseAdapter {
	protected final LayoutInflater inflater;
	private final Context context;
	private final List<? extends Product> products;

	public InventoryListViewAdapter(Context context,
			List<? extends Product> products) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.products = products;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return products.size();
	}

	@Override
	public Product getItem(int position) {
		// TODO Auto-generated method stub
		return products.get(position);
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
					R.layout.inventorylist_view_tablelayout, null);

			holder = getViewHolder(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		customizeView(position, holder);

		return convertView;
	}

	protected void customizeView(int position, ViewHolder holder) {
		Product product = products.get(position);
		customizeView(product, holder);
	}


	protected <T extends Product> void customizeView(T product,
			ViewHolder holder) {
		//customizeQuantityTextView(holder.getQuantityView(),
			//	product.getQuantity());
		holder.getQuantityView().setText("Qty: "+ Integer.toString(product.getQuantity()));
		holder.getNameView().setText(
				product.getName() + "  " + product.getModel() + "  "
						+ product.getPrice());
		holder.getdesc2View().setText(
				"Camera: " + (product.getCamera()==null?"":product.getCamera() )+ "  Display: "
						+ (product.getDisplay()==null?"":product.getDisplay()));
		holder.getPriceView().setText(
				"Memory: " + (product.getMemory()==null?"":product.getMemory()) + "  Color: "
						+ product.getColor() + "  OS: " + product.getOS());

		// holder.getdesc2View().setText(product.getResolution());
	}


	protected void customizeQuantityTextView(TextView quantityView, Integer text) {
		quantityView.setText(Integer.toString(text));
		// quantityView.setWidth(2);
	}

	
	protected void customizeRemoveImage(ImageView removeImage) {
		removeImage.setMaxWidth(0);
		removeImage.setVisibility(View.INVISIBLE);
	}

	protected ViewHolder getViewHolder(View view) {
		TextView quantityView = (TextView) view
				.findViewById(R.id.productsList_quantity);
		TextView nameView = (TextView) view
				.findViewById(R.id.productsList_name);
		TextView desc1View = (TextView) view.findViewById(R.id.desc1);
		ImageView removeImage = (ImageView) view.findViewById(R.id.imageView1);
		TextView desc2View = (TextView) view.findViewById(R.id.desc2);

		ViewHolder holder = new ViewHolder(quantityView, nameView, desc1View,
				removeImage, desc2View);

		return holder;
	}

	protected static class ViewHolder {
		private TextView quantityView;
		private TextView nameView;
		private TextView priceView;
		private ImageView removeImage;
		private TextView desc2View;

		public ViewHolder(TextView quantityView, TextView nameView,
				TextView priceView, ImageView removeImage, TextView desc2View) {
			super();
			this.quantityView = quantityView;
			this.nameView = nameView;
			this.priceView = priceView;
			this.removeImage = removeImage;
			this.desc2View = desc2View;
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

		public ImageView getRemoveImage() {
			return removeImage;
		}

		public TextView getdesc2View() {
			return desc2View;
		}
	}

}
