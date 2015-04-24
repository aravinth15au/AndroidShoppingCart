package com.samsung.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.samsung.model.Product;
import com.samsung.model.Transaction;
import com.samsung.xml.model.Device;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// for our logs
	public static final String LOG_TAG = "DatabaseHandler.java";

	// database version
	private static final int DATABASE_VERSION = 1;

	// database name
	protected static final String DATABASE_NAME = "Database1";

	// table details

	public String deviceId = "id";
	public String deviceName = "name";
	public String devicetype = "type";
	public String deviceOS = "OS";
	public String deviceCamera = "camera";
	public String deviceres = "resolution";
	public String deviceprice = "price";

	public String choiceid = "modelid";
	public String choicememory = "memory";
	public String choicemodel = "model";
	public String choicequantity = "quantity";
	public String choicecolor = "color";
	public String orderid = "id";

	// constructor
	public DatabaseHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	// creating Tables
	public void onCreate() {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "";
		String tableName = "devices";
		sql = "DROP TABLE IF EXISTS " + tableName;
		db.execSQL(sql);
		sql = "";
		sql += "CREATE TABLE " + tableName;
		sql += " ( ";
		sql += deviceId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sql += deviceName + " TEXT, ";
		sql += devicetype + " TEXT, ";
		sql += deviceOS + " TEXT, ";
		sql += deviceCamera + " TEXT, ";
		sql += deviceres + " TEXT, ";
		sql += deviceprice + " TEXT ";

		sql += " ) ";

		tableName = "products";
		sql = "";
		sql = "DROP TABLE IF EXISTS " + tableName;
		db.execSQL(sql);
		sql = "";
		sql += "CREATE TABLE " + tableName;
		sql += " ( ";
		sql += choiceid + " INTEGER PRIMARY KEY AUTOINCREMENT, ";

		sql += choicemodel + " TEXT, ";
		sql += choicecolor + " TEXT, ";
		sql += choicememory + " TEXT, ";
		sql += choicequantity + " INTEGER, ";
		sql += deviceName + " TEXT, ";
		sql += devicetype + " TEXT, ";
		sql += deviceOS + " TEXT, ";
		sql += deviceCamera + " TEXT, ";
		sql += deviceres + " TEXT, ";
		sql += deviceprice + " TEXT ";

		sql += " ) ";

		tableName = "orders";

		db.execSQL(sql);
		sql = "";
		sql = "DROP TABLE IF EXISTS " + tableName;
		db.execSQL(sql);
		sql = "";
		sql += "CREATE TABLE " + tableName;
		sql += " ( ";
		sql += orderid + " INTEGER, ";
		sql += " modelid INTEGER,";

		sql += choicequantity
				+ " INTEGER, FOREIGN KEY(modelid) REFERENCES products(id) ";

		sql += " ) ";

		db.execSQL(sql);

	}

	// When upgrading the database, it will drop the current table and recreate.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String tableName = "devices";
		String sql = "DROP TABLE IF EXISTS " + tableName;
		db.execSQL(sql);

		onCreate(db);
	}

	// Create location record.
	// @param - location contains location details to be added as single row.
	public boolean createDevice(Device device) {
		String tableName = "devices";
		boolean createSuccessful = false;

		ContentValues values = new ContentValues();

		values.put(deviceName, device.getName());
		values.put(deviceCamera, device.getCamera());
		values.put(deviceOS, device.getOS());
		values.put(deviceprice, device.getPrice());
		values.put(deviceres, device.getResolution());
		values.put(devicetype, device.getType());

		SQLiteDatabase db = this.getWritableDatabase();

		createSuccessful = db.insert(tableName, null, values) > 0;
		db.close();

		return createSuccessful;
	}

	// Read all location record.
	public List<Product> readProducts() {

		List<Product> recordsList = new LinkedList<Product>();
		String tableName = "products";
		// select query
		String sql = "";
		sql += "SELECT * FROM " + tableName;
		sql += " ORDER BY " + choiceid + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
		Cursor cursor = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				Product pd = new Product(cursor.getString(cursor
						.getColumnIndex(choicememory)), cursor.getString(cursor
						.getColumnIndex(choicecolor)), cursor.getInt(cursor
						.getColumnIndex(choicequantity)),
						cursor.getString(cursor.getColumnIndex(choicemodel)),
						cursor.getString(cursor.getColumnIndex(deviceName)),
						cursor.getString(cursor.getColumnIndex(devicetype)),
						cursor.getString(cursor.getColumnIndex(deviceOS)),
						cursor.getString(cursor.getColumnIndex(deviceres)),
						cursor.getString(cursor.getColumnIndex(deviceres)),
						cursor.getString(cursor.getColumnIndex(deviceCamera)),
						cursor.getString(cursor.getColumnIndex(deviceprice)),
						cursor.getInt(cursor.getColumnIndex(choiceid)), 0);

				// add to list
				recordsList.add(pd);

			} while (cursor.moveToNext());
		}

		// close the database
		db.close();

		// return the list of records
		return recordsList;
	}

	public List<Transaction> readOrders() {

		List<Transaction> recordsList = new LinkedList<Transaction>();
		String tableName = "orders";
		// select query
		String sql = "";
		sql += "SELECT DISTINCT id FROM " + tableName;
		sql += " ORDER BY " + orderid + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
		Cursor cursor = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				Transaction pd = new Transaction();
				pd.setOrderid(cursor.getInt(cursor.getColumnIndex(orderid)));

				// add to list
				recordsList.add(pd);

			} while (cursor.moveToNext());
		}

		// close the database
		db.close();

		// return the list of records
		return recordsList;
	}

	public List<Transaction> getOrders(int orderid) {

		List<Transaction> recordsList = new LinkedList<Transaction>();
		String tableName = "orders";
		// select query
		String sql = "";
		sql += "SELECT model,id,name,o.quantity,price  FROM " + tableName;
		sql += " o, products p where o.modelid=p.modelid and o.id = " + orderid;

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
		Cursor cursor = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				Transaction pd = new Transaction();
				pd.setOrderid(cursor.getInt(cursor.getColumnIndex("id")));
				pd.setModel(cursor.getString(cursor.getColumnIndex(choicemodel)));
				pd.setName(cursor.getString(cursor.getColumnIndex(deviceName)));
				pd.setQuantity(cursor.getInt(cursor
						.getColumnIndex(choicequantity)));
				pd.setPrice(cursor.getString(cursor.getColumnIndex(deviceprice)));
				// add to list"
				recordsList.add(pd);

			} while (cursor.moveToNext());
		}

		// close the database
		db.close();

		// return the list of records
		return recordsList;
	}

	public boolean createProduct(Product product) {

		boolean createSuccessful = false;
		String tableName = "products";
		ContentValues values = new ContentValues();

		values.put(deviceName, product.getName());
		values.put(deviceCamera, product.getCamera());
		values.put(deviceOS, product.getOS());
		values.put(deviceprice, product.getPrice());
		values.put(deviceres, product.getResolution());
		values.put(devicetype, product.getType());
		values.put(choicequantity, product.getQuantity());
		values.put(choicecolor, product.getColor());
		values.put(choicememory, product.getMemory());
		values.put(choicemodel, product.getModel());

		SQLiteDatabase db = this.getWritableDatabase();

		createSuccessful = db.insert(tableName, null, values) > 0;
		db.close();

		return createSuccessful;
	}

	public int createOrderId() {
		// select query
		String sql = "";
		sql += "SELECT * FROM orders ORDER BY id DESC LIMIT 1";

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
		Cursor cursor = db.rawQuery(sql, null);
		int id = 0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {

			id = cursor.getInt(cursor.getColumnIndex(orderid)) + 1;

		} else {
			id = 1;
		}
		return id;

	}

	public boolean createTransaction(Transaction transaction) {

		boolean createSuccessful = false;
		String tableName = "orders";
		ContentValues values = new ContentValues();
		values.put(choiceid, transaction.getProductid());
		values.put(orderid, transaction.getOrderid());
		values.put(choicequantity, transaction.getQuantity());
		SQLiteDatabase db = this.getWritableDatabase();
		createSuccessful = db.insert(tableName, null, values) > 0;
		db.close();

		return createSuccessful;
	}

	// Delete location record.

	public boolean delete(int id) {
		boolean deleteSuccessful = false;
		String tableName = "products";
		SQLiteDatabase db = this.getWritableDatabase();
		deleteSuccessful = db.delete(tableName, "id ='" + id + "'", null) > 0;
		db.close();

		return deleteSuccessful;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	public boolean updateProduct(int i, Integer productid) {
		// TODO Auto-generated method stub
		boolean updateSuccessful = false;

		ContentValues values = new ContentValues();
		String tableName = "products";
		values.put(choicequantity, i);

		String where = choiceid + " = ?";

		// you should use commas when you have multiple conditions
		String[] whereArgs = { Integer.toString(productid) };

		SQLiteDatabase db = this.getWritableDatabase();

		// use the update command
		updateSuccessful = db.update(tableName, values, where, whereArgs) > 0;
		db.close();

		return updateSuccessful;
	}

}