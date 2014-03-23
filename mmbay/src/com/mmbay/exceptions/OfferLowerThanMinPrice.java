package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class OfferLowerThanMinPrice extends ExceptionManager {

	public OfferLowerThanMinPrice()
	{
		raise("Cannot add offer, offer price must be upper than bid minimum price");
	}

}
