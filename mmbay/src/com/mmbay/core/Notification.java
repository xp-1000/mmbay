package com.mmbay.core;

public class Notification {
	
	private String src;
	private BidEvent event;
	
	public Notification (String src, BidEvent event) {
		this.setSrc(src);
		this.setEvent(event);
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BidEvent getEvent() {
		return event;
	}

	public void setEvent(BidEvent event) {
		this.event = event;
	}

}
