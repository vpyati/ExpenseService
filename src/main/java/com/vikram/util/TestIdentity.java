package com.vikram.util;

import com.vikram.openidconnect.login.core.identity.Identity;
import com.vikram.openidconnect.login.core.providers.OAuthProvider;

public class TestIdentity {
	
	public static Identity get(){
		
		return new Identity() {
			
			@Override
			public boolean isValid() {
				return true;
			}
			
			@Override
			public OAuthProvider getProvider() {
				return OAuthProvider.GOOGLE;
			}
			
			@Override
			public String getName() {
				return "Vikram Pyati";
			}
			
			@Override
			public String getEmailAddress() {
				return "vikrampyatitest@gmail.com";
			}
			
			@Override
			public String getAccessToken() {
				return "abcd";
			}
		};
		
		
	}

}
