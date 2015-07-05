package com.vikram.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;

import com.vikram.IdentityContext;
import com.vikram.openidconnect.login.core.identity.Identity;
import com.vikram.openidconnect.login.core.identity.IdentityAccessor;
import com.vikram.openidconnect.login.core.providers.OAuthProvider;

public class IdentityFilter implements Filter{

	@Autowired
	private IdentityAccessor identityAccessor;
	
	@Autowired
	private IdentityContext identityContext;
	
	@Override
	public void destroy() {
		identityContext.setIdentity(null);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
				
		String accessToken = httpRequest.getHeader("AUTHORIZATION");
		String provider = httpRequest.getHeader("OAUTH_PROVIDER");
		
		if(accessToken == null || provider == null){
			httpResponse.setStatus(403);
			return;
		}
		
		Identity identity;
		try {
			identity = identityAccessor.getIdentity(accessToken, OAuthProvider.valueOf(provider));
			identityContext.setIdentity(identity);
		} catch (HttpException e) {
			httpResponse.setStatus(403);
			return;
		}
		
		filterChain.doFilter(request, response);		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
