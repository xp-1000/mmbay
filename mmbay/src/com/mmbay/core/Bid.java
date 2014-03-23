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
import com.mmbay.factory.NotificationFactory;
import com.mmbay.utils.Observer;
import com.mmbay.utils.Timer;

public class Bid {

	private Product product;
	private User seller;
	private Calendar dueDate;
	private float price;
	private float minPrice;
	private float reservePrice;
	private BidStatus status;
	private List<Offer> offers;
	private NotificationFactory notifier;
	private Timer timer;
	
	
	public Bid (User seller, Product product, Calendar calendar, float price) {
		this.setSeller(seller);
		this.setProduct(product);
		this.setDueDate(calendar);
		this.setPrice(price);
		this.setNotification(new internalNotifier());
		offers = new ArrayList<Offer>();
		status = BidStatus.CREATED;
		this.timer = new Timer(dueDate);
		this.timer.addObserver(new Observer(){
		      public void update() {
		        setStatus(BidStatus.COMPLETED);
		      }
		    });
		this.timer.start();
	}
	
	public Bid (User seller, Product product, Calendar calendar, float price, float reservedPrice) {
		this.setSeller(seller);
		this.setProduct(product);
		this.setDueDate(calendar);
		this.setPrice(price);
		this.setReservePrice(reservedPrice);
		this.setNotification(new internalNotifier());
		offers = new ArrayList<Offer>();
		status = BidStatus.CREATED;
	}
	
	public Product getProduct() {
		return product;
	}

	protected void setProduct(Product product) {
		this.product = product;
	}

	public Calendar getDueDate() {
		return dueDate;
	}

	protected void setDueDate(Calendar calendar) {
		this.dueDate = calendar;
	}

	public float getPrice() {
		return price;
	}

	protected void setPrice(float price) {
		this.price = price;
	}

	public float getMinPrice() {
		return minPrice;
	}

	protected void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	protected float getReservePrice() {
		return reservePrice;
	}

	protected void setReservePrice(float reservePrice) {
		this.reservePrice = reservePrice;
	}

	public BidStatus getStatus() {
		return status;
	}

	protected void setStatus(BidStatus status) {
		this.status = status;
	}

	public User getSeller() {
		return seller;
	}

	protected void setSeller(User seller) {
		this.seller = seller;
	}
	
	public NotificationFactory getNotification() {
		return notifier;
	}

	public void setNotification(NotificationFactory notification) {
		this.notifier = notification;
	}
	
	public int getOffersCount() {
		return offers.size();
	}
	
	public void addOffer(User buyer, float price) throws OfferOnOwnBid, OfferWhenNotPubished, OfferLowerThanLast, OfferLowerThanMinPrice {
		boolean offerAdded = false;
		if (this.minPrice == 0.0f || this.minPrice < price)
		{
			if (!buyer.getLogin().equals(seller.getLogin()))
			{
				if (this.getStatus() == BidStatus.PUBLISHED)
				{
					if (getOffersCount() > 0)
					{
						Offer lastOffer = getLastOffer();
						if (price > lastOffer.getPrice())
						{
							offers.add(new Offer(buyer, price));
							offerAdded = true;
							User oldBestBuyer = offers.get(offers.size()-2).getBuyer();
							if (oldBestBuyer.getUserConfiguration().isNotifiedForUpperOffer())
								notifier.send(new Notification(buyer.getLogin(), BidEvent.NEWUPPEROFFER), this.getProduct(), oldBestBuyer.getLogin());
						}
						else
							throw new OfferLowerThanLast();
					}
					else
					{
						offers.add(new Offer(buyer, price));
						offerAdded = true;
					}
				}
				else
					throw new OfferWhenNotPubished();
			}
			else
				throw new OfferOnOwnBid();
			
			if (offerAdded) {
				
				notifier.send(new Notification(buyer.getLogin(), BidEvent.NEWOFFER),this.getProduct(), seller.getLogin());
				if (reservedPriceReached())
					this.sendNotifToOffers(new Notification(buyer.getLogin(), BidEvent.RESERVEDREACHED),this.getProduct(), buyer.getLogin());				
			}
		}
		else
			throw new OfferLowerThanMinPrice();
	}
	
	private void sendNotifToOffers(Notification notification, Product product, String exception) {
		Iterator<Offer> iter = offers.iterator();
		while (iter.hasNext()) {
			Offer offer = iter.next();
			if (exception == null || !exception.equals(offer.getBuyer().getLogin()))
			{
				boolean toAlert = false;
				if (notification.getEvent() == BidEvent.BIDCANCELED && offer.getBuyer().getUserConfiguration().isNotifiedForBidCanceled() == true)
					toAlert = true;
				if (notification.getEvent() == BidEvent.RESERVEDREACHED && offer.getBuyer().getUserConfiguration().isNotifiedForReservedReached() == true)
					toAlert = true;
				if (toAlert)
					notifier.send(notification, product, offer.getBuyer().getLogin());
			}
			
		}
	}
	
private void sendNotifToOffers(Notification notification, Product product) {
		
		this.sendNotifToOffers(notification, product, null);
	}

	public List<Offer> getOffers() {
		return offers;
	}
	
	protected void displayOffers() {
		Iterator<Offer> iter = offers.iterator();
		
		while (iter.hasNext()) {
			Offer offer = iter.next();
			System.out.println("Buyer : " + offer.getBuyer() + " | Price : " + offer.getPrice());
		}
	}
	
	public boolean reservedPriceReached() {
		boolean reached = false;
		
		if (reservePrice == 0.0f )
			reached = false;
		else
		{
			if (getOffersCount() == 0)
				reached = false;
			else
			{
				Offer lastOffer = getLastOffer();
				if ( reservePrice <= lastOffer.getPrice() )
					reached = true;
				else
					reached = false;
			}
		}
		return reached;
			
	}
	
	protected Offer getLastOffer() {
		if (getOffersCount() > 0)
			return offers.get(getOffersCount()-1);
		else
			return null;
	}

	protected void publish(User seller) throws BidStatusIfNotSeller {
		if (seller.equals(this.getSeller()))
			this.setStatus(BidStatus.PUBLISHED);
		else
			throw new BidStatusIfNotSeller("publish");
		
	}

	public void cancel(User seller) throws BidStatusIfNotSeller, BidCancelReserveNotRaised {
		if (seller.equals(this.getSeller()))
		{
			if (this.isCancelable())
			{
				this.setStatus(BidStatus.CANCELED);
				this.sendNotifToOffers(new Notification(seller.getLogin(), BidEvent.BIDCANCELED), this.getProduct());
			}
			else
				throw new BidCancelReserveNotRaised();
		}
		else
			throw new BidStatusIfNotSeller("cancel");
		
	}
	
	public boolean isCancelable() {
		boolean isCancelable = false;
		switch (this.getStatus()) {
			case CREATED:
				isCancelable = true;
				break;
			case CANCELED:
			case COMPLETED:
				isCancelable = false;
				break;
			case PUBLISHED:
				if (this.reservedPriceReached())
					isCancelable = false;
				else
					isCancelable = true;
				break;
		}
		return isCancelable;
	}
	
}
