package com.mmbay.core;

import java.util.ArrayList;
import java.util.List;

import com.mmbay.factory.NotificationFactory;

public class internalNotifier implements NotificationFactory {
	
	private List<String> dests;
	private List<Notification> notifs;
	
	public internalNotifier () {
		dests = new ArrayList<String>();
		notifs = new ArrayList<Notification>();
	}
	
	public void send(Notification notification,Product product, String dest) {
		switch (notification.getEvent())
		{
			case BIDCANCELED:
				System.out.println("send to " + dest + " : " + notification.getSrc() + " has canceled bid for " + product.getId());
				break;
			case NEWOFFER:
				System.out.println("send to " + dest + " : " + notification.getSrc() + " made a new offer on your bid for : " + product.getId());
				break;
			case NEWUPPEROFFER:
				System.out.println("send to " + dest + " : " + notification.getSrc() + " made a new offer upper than you on bid for : " + product.getId());
				break;
			case RESERVEDREACHED:
				System.out.println("send to " + dest + " : " + notification.getSrc() + " has reached your reserved price on bid for : " + product.getId());
				break;
			default:
				break;
				
		}
		dests.add(dest);
		notifs.add(notification);
	}
	
	public List[] getAllNotifications() {
		List liste[] = new ArrayList[2];
		liste[0] = dests;
		liste[1] = notifs;
		return liste;
	}

}
