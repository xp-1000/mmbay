package com.mmbay.core;

public class Product {
	
	private String id;
	private String description;
	
	public Product(String name, String description) {
		this.setName(name);
		this.setDescription(description);
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.id = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
