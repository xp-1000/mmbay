package com.mmbay.factory;

import com.mmbay.core.User;

public interface UserFactory {
		
	public void add(String username, String password, String first, String last);
	
	public void remove();
	
	public boolean isConnected(String login);
	
	public boolean connect(String login, String password) ;
	
	public void disconnect(String login);
	
	public void disconnect(User user);
	
	public User getLast();
	
	public User getByLogin(String login);

}
