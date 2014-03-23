package com.mmbay.tools;

import com.mmbay.core.BidManagement;
import com.mmbay.core.UserManagement;
import com.mmbay.factory.BidFactory;
import com.mmbay.factory.UserFactory;

public class BiddingSession {
	
	private UserFactory userManager;
	private BidFactory bidManager;
	
	public BiddingSession () {
		this.userManager = new UserManagement();
		this.bidManager = new BidManagement();
	}


	
	public void importUsersFromArray()
	{
		
	}
	
	public void exportUsersToArray()
	{
		
	}
	
	public void importBidsFromArray()
	{
		
	}
	
	public void exportBidsToArray()
	{
		
	}



	public UserFactory getUserManager() {
		return userManager;
	}



	public void setUserManager(UserManagement userManager) {
		this.userManager = userManager;
	}



	public BidFactory getBidManager() {
		return bidManager;
	}



	public void setBidManager(BidManagement bidManager) {
		this.bidManager = bidManager;
	}

}
