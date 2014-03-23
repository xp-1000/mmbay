package com.mmbay.factory;

import java.util.Calendar;

import com.mmbay.core.Bid;
import com.mmbay.core.Product;
import com.mmbay.core.User;
import com.mmbay.exceptions.BidCancelReserveNotRaised;
import com.mmbay.exceptions.BidStatusIfNotSeller;
import com.mmbay.exceptions.OfferLowerThanLast;
import com.mmbay.exceptions.OfferLowerThanMinPrice;
import com.mmbay.exceptions.OfferOnOwnBid;
import com.mmbay.exceptions.OfferWhenNotPubished;
import com.mmbay.exceptions.UserIsNotConnected;

public interface BidFactory {
	
	public void add(User seller, String id, String description, float price) throws UserIsNotConnected;
	
	public void add(User seller, String id, String description, float price, float reservedPrice) throws UserIsNotConnected;

	public void setMinPrice(String productId, float minPrice);

	public void setReservePrice(String productId, float reservePrice);
	
	public void setProperties(String productId, Product product, User seller, Calendar dueDate,float price);
	
	public void remove();
	
	public Bid getLast();
	
	public Bid getByProductId(String id) ;

	public void publish(User seller, String productId) throws BidStatusIfNotSeller, UserIsNotConnected;

	public void cancel(User seller, String productId) throws BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected ;
	
	public void addOffertoBid(String productId, User buyer, float price ) 
			throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, UserIsNotConnected ;
	
	public boolean isCancelable(String ProductId);
	
	public void displayBids();

}
