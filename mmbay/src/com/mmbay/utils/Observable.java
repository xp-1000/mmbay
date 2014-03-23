package com.mmbay.utils;

public interface Observable {
	  public void addObserver(Observer obs);
	  public void updateObserver();
	  public void delObserver();
	}
