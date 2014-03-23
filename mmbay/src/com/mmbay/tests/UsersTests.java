package com.mmbay.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mmbay.persistence.UsersTestDb;

import com.mmbay.core.User;
import com.mmbay.core.UserManagement;
import com.mmbay.factory.UserFactory;

public class UsersTests {
	
	String refLogin = UsersTestDb.refLogin, refFirst = UsersTestDb.refFirst, 
			refLast = UsersTestDb.refLast, refPassword = UsersTestDb.refPassword;
	UserFactory userManagement;
	
	@Before
	public void init() {
		userManagement = new UserManagement(); 
		userManagement.add(refLogin, refPassword, refFirst, refLast);
	}
	
	@Test
	public void testCreateUser() {		
		User testUser = userManagement.getLast();
		
		assertEquals(testUser.getLogin(), refLogin);
		assertEquals(testUser.getPassword(), refPassword);
		assertEquals(testUser.getFirst(), refFirst);
		assertEquals(testUser.getLast(), refLast);
	}
	
	@Test
	public void testConnect() {
		
		assertEquals(userManagement.connect(refLogin, refPassword), true);
		assertEquals(userManagement.isConnected(refLogin), true);
		
	}
	
	@Test
	public void testDisconnect() {

		userManagement.connect(refLogin, refPassword);
		userManagement.disconnect(refLogin);
		assertEquals(userManagement.isConnected(refLogin), false);
		
	}

}
