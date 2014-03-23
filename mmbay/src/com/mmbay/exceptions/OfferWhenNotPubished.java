package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class OfferWhenNotPubished extends ExceptionManager {

	public OfferWhenNotPubished()
	{
		raise("Cannot add offer, buyer cannot also be the seller");
	}

}
