package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class OfferLowerThanLast extends ExceptionManager {

	public OfferLowerThanLast()
	{
		raise("Cannot add offer, offer price must be upper than last offer on bid");
	}

}
