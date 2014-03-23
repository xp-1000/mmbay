package com.mmbay.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mmbay.factory.UserFactory;


public class UserManagement implements UserFactory {
	
	private List<User> usersList;
	
	public UserManagement() {
		usersList = new ArrayList<User>();
	}
	
	public void add(String username, String password, String first, String last) {
		usersList.add(new User(username,password,first,last));
	}
	
	public void remove()
	{
		
	}
	
	public boolean isConnected(String login) {
		User user = this.getByLogin(login);
		if (user.isConnected() == true)
		 	return true;
	 	else
	 		return false;
	}
	
	public boolean connect(String login, String password) {
		User user = this.getByLogin(login);
		if(user != null) {
			if (user.getPassword().equals(password))
			{
				user.setConnected(true);
				return true;
			}
			else {
				System.err.println("Password ist not correct");
				return false;
			}
		}
		else
		{
			System.err.println("Login does not exist");
			return false;
		}
	}
	
	public void disconnect(String login) {
		User user = this.getByLogin(login);
		this.disconnect(user);
	}
	
	public void disconnect(User user) {
		user.setConnected(false);
	}
	
	public User getLast()
	{
		return usersList.get(usersList.size()-1);
	}
	
	public User getByLogin(String login) {
		Iterator<User> iter = usersList.iterator();
		
		while (iter.hasNext()) {
			User user = iter.next();
			if (user.getLogin().equals(login))
			{
				return user;
			}
		}
		return null;
		
	}

}
