package com.samsung.xml.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.samsung.activity.InventoryListActivity;
import com.samsung.model.Product;
import com.samsung.xml.model.Choice;
import com.samsung.xml.model.Device;

public class XmlParsingHandler {
	private final static String TAG = "XMLParser";

	public static List<Product> parseXml(Context myContext) throws IOException,
			XmlPullParserException {
		// TODO Auto-generated method stub
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		XmlPullParser parser = factory.newPullParser();

		File dir = Environment.getExternalStorageDirectory();
		// File yourFile = new File(dir,
		// "path/to/the/file/inside/the/sdcard.ext");

		// Get the text file
		File file = new File(dir, "Inventory.xml");
		InputStream is = new FileInputStream(file);
		parser.setInput(is, null);

		// parser.nextTag();
		Log.d(TAG, "in xmlparser" + parser.getName());
		// parser.nextText();
		int eventType = parser.getEventType();
		Choice choice = null;
		Device device = null;
		String text = null;
		List<Device> deviceList = new LinkedList<Device>();
		List<Choice> choiceList = new LinkedList<Choice>();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String tagname = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (tagname.equalsIgnoreCase("device")) {

					device = new Device();
					choiceList = new LinkedList<Choice>();
				} else if (tagname.equalsIgnoreCase("choice")) {
					choice = new Choice();
				}
				break;

			case XmlPullParser.TEXT:
				text = parser.getText();
				break;

			case XmlPullParser.END_TAG:
				if (tagname.equalsIgnoreCase("device")) {

					device.setChoices(choiceList);
					deviceList.add(device);

				} else if (tagname.equalsIgnoreCase("choice")) {
					choiceList.add(choice);

				} else if (tagname.equalsIgnoreCase("type")) {
					device.setType(text);
				} else if (tagname.equalsIgnoreCase("name")) {
					device.setName(text);
				} else if (tagname.equalsIgnoreCase("price")) {
					device.setPrice(text);
				} else if (tagname.equalsIgnoreCase("OS")) {
					device.setOS(text);
				} else if (tagname.equalsIgnoreCase("resolution")) {
					device.setResolution(text);
				} else if (tagname.equalsIgnoreCase("camera")) {
					device.setCamera(text);
				} else if (tagname.equalsIgnoreCase("memory")) {
					choice.setMemory(text);
				} else if (tagname.equalsIgnoreCase("quantity")) {
					choice.setQuantity(Integer.parseInt(text));
				} else if (tagname.equalsIgnoreCase("color")) {
					choice.setColor(text);
				} else if (tagname.equalsIgnoreCase("model")) {
					choice.setModel(text);
				}
				break;

			default:
				break;
			}
			eventType = parser.next();
		}

		List<Product> productList = new LinkedList<Product>();
		for (Device device1 : deviceList) {
			for (Choice ch : device1.getChoices()) {
				Product product = new Product();
				product.setCamera(device1.getCamera());
				product.setColor(ch.getColor());
				product.setDisplay(device1.getDisplay());
				product.setQuantity(ch.getQuantity());
				product.setModel(ch.getModel());
				product.setOS(device1.getOS());
				product.setName(device1.getName());
				product.setResolution(device1.getResolution());
				product.setType(device1.getType());
				product.setPrice(device1.getPrice());
				product.setMemory(ch.getMemory());
				productList.add(product);

			}

		}
		return productList;
	}

}
