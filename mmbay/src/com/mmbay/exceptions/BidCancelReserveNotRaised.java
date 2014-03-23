package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class BidCancelReserveNotRaised extends ExceptionManager {

	public BidCancelReserveNotRaised()
	{
		raise("Cannot cancel bid, reserved price not reached");
	}

}
