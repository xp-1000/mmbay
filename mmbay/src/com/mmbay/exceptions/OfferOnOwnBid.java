package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class OfferOnOwnBid extends ExceptionManager {

	public OfferOnOwnBid()
	{
		raise("Cannot add offer, buyer cannot also be the seller");
	}

}
