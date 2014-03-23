package com.mmbay.tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.mmbay.core.Bid;
import com.mmbay.core.BidStatus;
import com.mmbay.core.User;
import com.mmbay.exceptions.BidCancelReserveNotRaised;
import com.mmbay.exceptions.BidStatusIfNotSeller;
import com.mmbay.exceptions.OfferLowerThanLast;
import com.mmbay.exceptions.OfferLowerThanMinPrice;
import com.mmbay.exceptions.OfferOnOwnBid;
import com.mmbay.exceptions.OfferWhenNotPubished;
import com.mmbay.exceptions.UserIsNotConnected;
import com.mmbay.factory.BidFactory;
import com.mmbay.factory.UserFactory;
import com.mmbay.persistence.ProductsTestDb;
import com.mmbay.persistence.UsersTestDb;
import com.mmbay.tools.BiddingSession;

public class ObserverTest {

	private BiddingSession session;
	private UserFactory userManagement;
	private BidFactory bidManagement;
	private User seller, buyer, otherBuyer;


	
	

}
