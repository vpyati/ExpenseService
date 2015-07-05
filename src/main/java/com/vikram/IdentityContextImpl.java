package com.vikram;

import com.vikram.openidconnect.login.core.identity.Identity;

public class IdentityContextImpl implements IdentityContext {

	private ThreadLocal<Identity> cache = new ThreadLocal<Identity>();

	@Override
	public Identity getIdentity() {
		return cache.get();
	}

	@Override
	public void setIdentity(Identity identity) {
		cache.set(identity);
	}

}
