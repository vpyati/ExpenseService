package com.vikram;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikram.model.TwilioParameters;

@RestController
@RequestMapping("/open/twilio")
public class TwilioProxy {

	private static Logger logger = LoggerFactory.getLogger(TwilioProxy.class);
	
	private static String SERVICE_ENDPOINT = "http://imagecleanup.ebay.com/imagecleanup/v1/listdial";
	
	@RequestMapping(method = RequestMethod.GET)
	public String forward(HttpServletRequest request, HttpServletResponse servletResponse) { 		
		
		try {
			HttpResponse response = invokeEbayService(request);
			HttpEntity entity = response.getEntity();
			servletResponse.setContentType("application/xml");
			return EntityUtils.toString(entity, "UTF-8");
			
		} catch (Exception e) {
			logger.error("Unable to invoke ebay service", e);
			servletResponse.setStatus(500);
			return "";
		}
	}
	
	private HttpResponse invokeEbayService(HttpServletRequest request) throws Exception  {
		HttpClient client = HttpClientBuilder.create().build();

		String serviceEndpoint = SERVICE_ENDPOINT ;
		
		HttpPost post = new HttpPost(serviceEndpoint);
		addHeaders(post,request);
		addParameters(post,request);
		
		return client.execute(post);		
	}

	private void addParameters(HttpPost post, HttpServletRequest request) throws Exception {
		
		TwilioParameters parameters = new TwilioParameters();
		
		Map<String, String[]> paramMap = request.getParameterMap();
		if(paramMap == null || paramMap.isEmpty()){
			return;
		}
		
		for(Entry<String, String[]> entry:paramMap.entrySet()){
			
			String paramName = entry.getKey();
			String[] paramValues = entry.getValue();
			
			if(paramName == null || paramValues==null || paramValues.length ==0){
				continue;
			}
			
			for(String paramValue:paramValues){
				parameters.getParameters().put(paramName, paramValue);
			}			
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(parameters);
				
		try {
			post.setEntity(new StringEntity(json));		
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
		
	}

	private void addHeaders(HttpPost post, HttpServletRequest request) {
		if(request.getHeaderNames() == null ){
			return;
		}
		
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()){
			String headerName = headers.nextElement();
			post.addHeader(headerName, request.getHeader(headerName));
		}	
				
		post.addHeader("Authorization", "TOKEN "+getUserToken());
	}

	private String getUserToken() {
		
		String token = System.getProperty("USER_TOKEN");
		
		if(token == null){
			token = System.getenv("USER_TOKEN"); 
		}

		return token == null?"":token;		
	}


	
	
}
