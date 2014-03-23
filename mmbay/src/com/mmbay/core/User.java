package com.mmbay.core;

import com.mmbay.config.UserConfiguration;
import com.mmbay.utils.Encryption;

public class User {
	
	private String login;
	private String password;
	private String first;
	private String last;
	private boolean connected;
	private UserConfiguration userConfiguration;
	
	public User(String login, String password, String first, String last) {
		this.setLogin(login);
		this.setPassword(password);
		this.setFirst(first);
		this.setLast(last);
		this.setConnected(false);
		setUserConfiguration(new UserConfiguration());
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return Encryption.decrypt(password);
	}

	public void setPassword(String password) {
		this.password = Encryption.encrypt(password);
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}


	public UserConfiguration getUserConfiguration() {
		return userConfiguration;
	}


	public void setUserConfiguration(UserConfiguration userConfiguration) {
		this.userConfiguration = userConfiguration;
	}

}
