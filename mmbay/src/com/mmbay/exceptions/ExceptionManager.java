package com.mmbay.exceptions;

@SuppressWarnings("serial")
public class ExceptionManager extends Exception{
	
	public void raise(String message)
	{
		System.err.println(message);
	}

}
