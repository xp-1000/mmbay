package com.mmbay.utils;

public class Encryption {
	
	public static String decrypt(String encrypted_pass) {
		String decrypted_pass="";
        for (int i=0; i<encrypted_pass.length();i++) {
            int c=encrypted_pass.charAt(i)^48;
            decrypted_pass=decrypted_pass+(char)c;
        }
	return decrypted_pass;
	}
	
	public static String encrypt(String pass) {
		String crypte="";
        for (int i=0; i<pass.length();i++) {
            int c=pass.charAt(i)^48;
            crypte=crypte+(char)c;
        }
	return crypte;
	}

}
