package com.vikram.util;

import com.vikram.openidconnect.login.core.identity.Identity;
import com.vikram.openidconnect.login.core.providers.OAuthProvider;

public class TestIdentity {
	
	public static Identity get(){	
		return get("vikrampyatitest@gmail.com");		
	}
	
	public static Identity get(String UID){
		return UID==null?getIdentityInternal("vikrampyatitest@gmail.com"):getIdentityInternal(UID);		
	}

	private static Identity getIdentityInternal(final String UID) {
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
				return UID;
			}
			
			@Override
			public String getAccessToken() {
				return "abcd";
			}
		};
	}

}
