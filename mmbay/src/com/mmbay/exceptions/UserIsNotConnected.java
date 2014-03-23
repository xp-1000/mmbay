package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class UserIsNotConnected extends ExceptionManager {

	public UserIsNotConnected(String action)
	{
		raise("Cannot " + action + ", user is not connected");
	}

}
