package com.vikram.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.vikram.openidconnect.login.core.identity.Identity;
import com.vikram.openidconnect.login.core.identity.IdentityAccessor;
import com.vikram.openidconnect.login.core.providers.OAuthProvider;
import com.vikram.util.Environment;
import com.vikram.util.RequestContext;
import com.vikram.util.RequestContext.RequestKey;
import com.vikram.util.TestIdentity;

public class IdentityFilter extends GenericFilterBean{

	private IdentityAccessor identityAccessor;
		
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		if(Environment.isDevelopment(request)){			
			Identity identity = TestIdentity.get();
			RequestContext.get().setValue(RequestKey.IDENTITY, identity);
			filterChain.doFilter(request, response);		
			return;		
		}
		
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
			RequestContext.get().setValue(RequestKey.IDENTITY, identity);
		} catch (HttpException e) {
			httpResponse.setStatus(403);
			return;
		}
		
		filterChain.doFilter(request, response);		
	}

	@Override
	protected void initFilterBean() throws ServletException {
		final WebApplicationContext applicationContext = WebApplicationContextUtils
	            .getWebApplicationContext(this.getServletContext());
		
		identityAccessor = applicationContext.getBean(IdentityAccessor.class);
	}

}
