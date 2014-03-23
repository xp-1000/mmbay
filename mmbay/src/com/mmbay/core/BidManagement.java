package com.mmbay.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.mmbay.exceptions.BidCancelReserveNotRaised;
import com.mmbay.exceptions.BidStatusIfNotSeller;
import com.mmbay.exceptions.OfferLowerThanLast;
import com.mmbay.exceptions.OfferLowerThanMinPrice;
import com.mmbay.exceptions.OfferOnOwnBid;
import com.mmbay.exceptions.OfferWhenNotPubished;
import com.mmbay.exceptions.UserIsNotConnected;
import com.mmbay.factory.BidFactory;


public class BidManagement implements BidFactory{
	
	private List<Bid> bidsList;
	
	public BidManagement() {
		bidsList = new ArrayList<Bid>();
	}
	
	public void add(User seller, Calendar dueDate, String id, String description, float price) throws UserIsNotConnected {
		if (seller.isConnected() == true)
			{
			bidsList.add(new Bid(seller, new Product(id, description),dueDate, price));}
		else
			throw new UserIsNotConnected("add bid");
	}
	
	public void add(User seller, Calendar dueDate, String id, String description, float price, float reservedPrice) throws UserIsNotConnected {
		if (seller.isConnected() == true)
			bidsList.add(new Bid(seller, new Product(id, description),dueDate, price, reservedPrice));
		else
			throw new UserIsNotConnected("add bid");
	}

	public void setMinPrice(String productId, float minPrice) {
		Bid bid = getByProductId(productId);
		bid.setMinPrice(minPrice);
	}

	public void setReservePrice(String productId, float reservePrice) {
		Bid bid = getByProductId(productId);
		bid.setReservePrice(reservePrice);
	}
	
	public void setProperties(String productId, Product product, User seller, Calendar dueDate,float price) {
		Bid bid = getByProductId(productId);
		if (bid.getStatus() != BidStatus.CREATED) {
			bid.setProduct(product);
			bid.setSeller(seller);
			bid.setDueDate(dueDate);
			bid.setPrice(price);
		} else
			System.err.println("An user cannot change properties after bid was published");
	}
	
	public void remove()
	{
		
	}
	
	
	public Bid getLast()
	{
		return bidsList.get(bidsList.size()-1);
	}
	
	public Bid getByProductId(String id) {
		Iterator<Bid> iter = bidsList.iterator();
		
		while (iter.hasNext()) {
			Bid bid = iter.next();
			if (bid.getProduct().getId().equals(id))
			{
				return bid;
			}
		}
		return null;
		
	}

	public void publish(User seller, String productId) throws BidStatusIfNotSeller, UserIsNotConnected {
		if (seller.isConnected() == true)		
		{
			Bid bid = getByProductId(productId);
			bid.publish(seller);
		}
		else
			throw new UserIsNotConnected("publish bid");
	}

	public void cancel(User seller, String productId) throws BidStatusIfNotSeller, BidCancelReserveNotRaised, UserIsNotConnected {
		if (seller.isConnected() == true)		
		{
			Bid bid = getByProductId(productId);
			bid.cancel(seller);
		}
		else
			throw new UserIsNotConnected("cancel bid");
		
	}
	
	public void addOffertoBid(String productId, User buyer, float price ) 
				throws  OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice, UserIsNotConnected {
		if (buyer.isConnected() == true)		
		{
			Bid bid = getByProductId(productId);
			bid.addOffer(buyer, price);

		}
		else
			throw new UserIsNotConnected("add offer");
	}
	
	public boolean isCancelable(String productId) {
		Bid bid = getByProductId(productId);
		return bid.isCancelable();
	}
	
	public void displayBids() {
		Iterator<Bid> iter = bidsList.iterator();
		while (iter.hasNext()) {
			Bid bid = iter.next();
			System.out.println("ProductID : " + bid.getProduct().getId());
			
		}
	}

}
