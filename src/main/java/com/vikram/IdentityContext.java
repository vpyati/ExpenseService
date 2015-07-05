package com.vikram;

import com.vikram.openidconnect.login.core.identity.Identity;

public interface IdentityContext {

	Identity getIdentity();

	void setIdentity(Identity identity);

}
