package com.mmbay.tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mmbay.persistence.ProductsTestDb;
import com.mmbay.persistence.UsersTestDb;
import com.mmbay.core.Bid;
import com.mmbay.core.BidEvent;
import com.mmbay.core.BidStatus;
import com.mmbay.core.Notification;
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
import com.mmbay.tools.BiddingSession;

public class BidsTests {
	
	private BiddingSession session;
	private UserFactory userManagement;
	private BidFactory bidManagement;
	private User seller, buyer, otherBuyer;
	private Calendar dueDate;

	@Before
	public void init() {
		// Managers creation
		session = new BiddingSession();
		userManagement = session.getUserManager();
		bidManagement = session.getBidManager();
		// Seller User creation and connection
		userManagement.add(UsersTestDb.sellerLogin, UsersTestDb.sellerPassword, UsersTestDb.sellerFirst, UsersTestDb.sellerLast);
		seller = userManagement.getLast();
		userManagement.connect(UsersTestDb.sellerLogin, UsersTestDb.sellerPassword);
		// Buyers User creation and connection
		userManagement.add(UsersTestDb.buyerLogin, UsersTestDb.buyerPassword, UsersTestDb.buyerFirst, UsersTestDb.buyerLast);
		userManagement.connect(UsersTestDb.buyerLogin, UsersTestDb.buyerPassword);
		buyer = userManagement.getLast();
		String otherVar = "other"; 
		userManagement.add(otherVar + UsersTestDb.buyerLogin, otherVar + UsersTestDb.buyerPassword, otherVar + UsersTestDb.buyerFirst, otherVar + UsersTestDb.buyerLast);
		userManagement.connect(otherVar + UsersTestDb.buyerLogin, otherVar + UsersTestDb.buyerPassword);
		otherBuyer = userManagement.getLast();
		dueDate = new GregorianCalendar(2014, Calendar.DECEMBER, 24);
		
	}

	
	@Test
	public void testCreateBid()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		// First Bid creation
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		// The new bid must exist with CREATED status
		assertEquals(BidStatus.CREATED, bid.getStatus());
	}
	
	@Test(expected=UserIsNotConnected.class) 
	public void testCreateBidDisconnected()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		String tmpName = "tmpName";
		userManagement.add(UsersTestDb.sellerLogin, UsersTestDb.sellerPassword, UsersTestDb.sellerFirst, UsersTestDb.sellerLast);
		User tmpUser = userManagement.getLast();
		
		// Test creation Bid with no connected user. Exception must be raised
		bidManagement.add(tmpUser, dueDate, tmpName, "tmpDescr", 0.0f);
		Bid bid = bidManagement.getByProductId(tmpName);
		// getByProductId must return a null value because no bid was created
		assertEquals(null, bid);
	}
	
	@Test
	public void testPublishBid()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		// Test Publishing first bid
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.publish(seller, ProductsTestDb.productId);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		// The new status of current bid must be PUBLISHED
		assertEquals(BidStatus.PUBLISHED, bid.getStatus());
	}
	
	@Test
	public void testCreateOffers() 
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		// Test creation 2 offers on first Bid
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productReserve+1);
		bidManagement.addOffertoBid(ProductsTestDb.productId, otherBuyer, ProductsTestDb.productReserve+2);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		// The offers number on current bid must be two
		assertEquals(2, bid.getOffersCount());
	}
	
	@Test(expected=OfferOnOwnBid.class) 
	public void testCreateOfferOnOwnBid() 
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		// Test creation 1 offer when seller = buyer
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, seller, ProductsTestDb.productReserve+1);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		// The offers number on current bid must be 0
		assertEquals(0, bid.getOffersCount());
	}
	
	
	@Test(expected=OfferLowerThanMinPrice.class) 
	public void testCreatedOfferLowerMinPrice()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.setMinPrice(ProductsTestDb.productId,ProductsTestDb.productMin);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productMin-1);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		assertEquals(0, bid.getOffersCount());
	}
	
	@Test
	public void testCreateOfferUpperMinPrice()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.setMinPrice(ProductsTestDb.productId,ProductsTestDb.productMin);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productMin+1);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		assertEquals(1, bid.getOffersCount());
	}
	
	@Test
	public void testCancelBid() 
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		buyer.getUserConfiguration().setNotifiedForBidCanceled(true);
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productMin+1);
		bidManagement.cancel(seller, ProductsTestDb.productId);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		// The new bid status must be canceled
		assertEquals(BidStatus.CANCELED, bid.getStatus());
		// The last notification must be a cancel by seller for buyer
		List list [] = bid.getNotification().getAllNotifications();
		List<String> listDests = list[0];
		List<Notification> listNotifs = list[1];
		assertEquals(buyer.getLogin(), listDests.get(1));
		assertEquals(BidEvent.BIDCANCELED, listNotifs.get(1).getEvent());
		assertEquals(seller.getLogin(), listNotifs.get(1).getSrc());
	}
	
	
	@Test
	public void testCancelBidReserveUnreached() 
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice, ProductsTestDb.productReserve);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productReserve-1);
		bidManagement.cancel(seller, ProductsTestDb.productId);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		assertEquals(BidStatus.CANCELED, bid.getStatus());
	}
	
	@Test(expected=BidCancelReserveNotRaised.class) 
	public void testCancelBidReserveReached()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice, ProductsTestDb.productReserve);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productReserve+1);
		bidManagement.cancel(seller, ProductsTestDb.productId);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		assertEquals(BidStatus.PUBLISHED, bid.getStatus());
		
	}
	
	@Test
	public void testNotifReserveReached() 
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		buyer.getUserConfiguration().setNotifiedForReservedReached(true);
		buyer.getUserConfiguration().setNotifiedForUpperOffer(true);
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice, ProductsTestDb.productReserve);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productReserve+1);
		bidManagement.addOffertoBid(ProductsTestDb.productId, otherBuyer, ProductsTestDb.productReserve+2);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		// The last notification must be a reserved price raised 
		List list [] = bid.getNotification().getAllNotifications();
		List<String> listDests = list[0];
		List<Notification> listNotifs = list[1];
		assertEquals(buyer.getLogin(), listDests.get(3));
		assertEquals(BidEvent.RESERVEDREACHED, listNotifs.get(3).getEvent());
		assertEquals(otherBuyer.getLogin(), listNotifs.get(3).getSrc());
		
	}
	
	@Test
	public void testNotifNewOffers()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		buyer.getUserConfiguration().setNotifiedForUpperOffer(true);
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productReserve+1);
		bidManagement.addOffertoBid(ProductsTestDb.productId, otherBuyer, ProductsTestDb.productReserve+2);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		List list [] = bid.getNotification().getAllNotifications();
		List<String> listDests = list[0];
		List<Notification> listNotifs = list[1];
		assertEquals(seller.getLogin(), listDests.get(0));
		assertEquals(BidEvent.NEWOFFER, listNotifs.get(0).getEvent());
		assertEquals(buyer.getLogin(), listNotifs.get(0).getSrc());
		assertEquals(buyer.getLogin(), listDests.get(1));
		assertEquals(BidEvent.NEWUPPEROFFER, listNotifs.get(1).getEvent());
		assertEquals(otherBuyer.getLogin(), listNotifs.get(1).getSrc());
		assertEquals(seller.getLogin(), listDests.get(2));
		assertEquals(BidEvent.NEWOFFER, listNotifs.get(2).getEvent());
		assertEquals(otherBuyer.getLogin(), listNotifs.get(2).getSrc());
	}
	
	@Test
	public void testCompleteBid()  
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		//Fenetre fen = new Fenetre();
		Calendar dueDate = new GregorianCalendar(2013, Calendar.DECEMBER, 24);
		
		bidManagement.add(seller, dueDate, ProductsTestDb.productId, ProductsTestDb.productDescr, ProductsTestDb.productPrice);
		bidManagement.publish(seller, ProductsTestDb.productId);
		bidManagement.addOffertoBid(ProductsTestDb.productId, buyer, ProductsTestDb.productMin+1);
		Bid bid = bidManagement.getByProductId(ProductsTestDb.productId);
		try {
	        Thread.sleep(1000);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
		assertEquals(BidStatus.COMPLETED, bid.getStatus());
	}

}
