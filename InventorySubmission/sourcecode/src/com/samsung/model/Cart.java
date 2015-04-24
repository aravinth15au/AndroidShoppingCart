package com.samsung.model;

import java.util.LinkedList;
import java.util.List;

import android.app.Application;

public class Cart extends Application {
	private static List<Product> cart;

	// private static Cart me;

	@Override
	public void onCreate() {
		super.onCreate();
		// me = this ;

	}

	
	public List<Product> getCart() {
		if (cart == null)
			return new LinkedList<Product>();
		return cart;
	}

	public void setCart(List<Product> cart) {
		this.cart = cart;
	}

	public void add(Product product) {

	}

}
