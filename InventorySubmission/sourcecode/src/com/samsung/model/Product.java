package com.samsung.model;

public class Product {
	private String memory;
	private String color;
	private Integer quantity;
	private String model;
	private String name;
	private String type;
	private String OS;
	private String resolution;
	private String display;
	private String camera;
	private String price;
	private Integer productid;
	private Integer cartquantity;
	public Product(String memory, String color, Integer quantity, String model,
			String name, String type, String oS, String resolution,
			String display, String camera, String price, Integer productid,
			Integer cartquantity) {
		super();
		this.memory = memory;
		this.color = color;
		this.quantity = quantity;
		this.model = model;
		this.name = name;
		this.type = type;
		OS = oS;
		this.resolution = resolution;
		this.display = display;
		this.camera = camera;
		this.price = price;
		this.productid = productid;
		this.cartquantity = cartquantity;
	}
	public Product() {
		// TODO Auto-generated constructor stub
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Integer getCartquantity() {
		return cartquantity;
	}
	public void setCartquantity(Integer cartquantity) {
		this.cartquantity = cartquantity;
	}
}
