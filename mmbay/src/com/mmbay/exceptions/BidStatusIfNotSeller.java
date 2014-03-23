package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class BidStatusIfNotSeller extends ExceptionManager {

	public BidStatusIfNotSeller(String action)
	{
		raise("Cannot " + action + " bid, user is not the bid seller");
	}

}
