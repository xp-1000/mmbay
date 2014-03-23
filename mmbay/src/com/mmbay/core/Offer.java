package com.mmbay.core;

public class Offer {
	private User buyer;
	private float price;
	
	public Offer (User buyer, float price) {
		this.setBuyer(buyer);
		this.setPrice(price);
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
