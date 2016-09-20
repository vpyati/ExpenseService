package com.vikram.util.twilio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientTwilio {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientTwilio.class);

	public String getJSONResponse(String url) throws HttpException{
				
		logger.info("Making http call to URL "+url);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
 
		// add request header
		request.addHeader("User-Agent", USER_AGENT);
 
		return getResponse(client, request);		
		
	}
	
	public String getJSONResponse(String url,Map<String,String> postParams) throws HttpException{
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		
		addParams(postParams, request);
	 
		// add request header
		request.addHeader("User-Agent", USER_AGENT);
 
		return getResponse(client, request);
	}

	private void addParams(Map<String, String> postParams, HttpPost request)
			throws HttpException {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry:postParams.entrySet()){
			urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		
		
		try {
			request.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			throw new HttpException("",e1);
		}
	}

	private String getResponse(HttpClient client, HttpRequestBase request)
			throws HttpException {
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
			BufferedReader rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IllegalStateException | IOException e) {
			logger.error("Exception making http call ",e);
			throw new HttpException("Exception making http call ",e);
		}finally{
			HttpClientUtils.closeQuietly(response);
		}
	}
	

}
