package com.mmbay.factory;

import java.util.List;

import com.mmbay.core.Notification;
import com.mmbay.core.Product;

public interface NotificationFactory {
	
	public void send(Notification notification, Product product, String dest);
	public List[] getAllNotifications();

}
