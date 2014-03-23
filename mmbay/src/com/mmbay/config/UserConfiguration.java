package com.mmbay.config;

import java.util.ArrayList;
import java.util.List;

public class UserConfiguration {
	
	private boolean notifiedForReservedReached;
	private boolean notifiedForBidCanceled;
	private boolean notifiedForUpperOffer;
	
	public UserConfiguration() {
		this.setNotifiedForReservedReached(false);
		this.setNotifiedForBidCanceled(false);
		this.setNotifiedForUpperOffer(false);
	}
	
	public UserConfiguration(boolean notifyForReservedReached, boolean notifyForBidCanceled, boolean notifyForUpperOffer) {
		this.setNotifiedForReservedReached(notifyForReservedReached);
		this.setNotifiedForBidCanceled(notifyForBidCanceled);
		this.setNotifiedForUpperOffer(notifyForUpperOffer);
	}

	public boolean isNotifiedForReservedReached() {
		return notifiedForReservedReached;
	}

	public void setNotifiedForReservedReached(boolean notifyForReservedReached) {
		this.notifiedForReservedReached = notifyForReservedReached;
	}

	public boolean isNotifiedForBidCanceled() {
		return notifiedForBidCanceled;
	}

	public void setNotifiedForBidCanceled(boolean notifyForBidCanceled) {
		this.notifiedForBidCanceled = notifyForBidCanceled;
	}

	public boolean isNotifiedForUpperOffer() {
		return notifiedForUpperOffer;
	}

	public void setNotifiedForUpperOffer(boolean notifyForUpperOffer) {
		this.notifiedForUpperOffer = notifyForUpperOffer;
	}
}
