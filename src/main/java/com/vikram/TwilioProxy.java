package com.vikram;

import java.io.UnsupportedEncodingException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.sdk.verbs.Gather;
import com.twilio.sdk.verbs.Pause;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
import com.vikram.db.KeyValueStore;
import com.vikram.model.TwilioParameters;
import com.vikram.util.UserDetailBean;
import com.vikram.util.twilio.EbayListing;
import com.vikram.util.twilio.LocalDB;

@RestController
@RequestMapping("/open/twilio")
public class TwilioProxy {

	private static Logger logger = LoggerFactory.getLogger(TwilioProxy.class);
	
	private static String SERVICE_ENDPOINT = "http://imagecleanup.ebay.com/imagecleanup/v1/listdial";
	private static String SERVICE_ENDPOINT_CONFIRM = "http://imagecleanup.ebay.com/imagecleanup/v1/listconfirm";
	
	private static String XML_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	private static String ITEM_TITLE = "Apple iPhone 5c 16GB  White AT&amp;T Smartphone";
	
	@Autowired
	private KeyValueStore keyValueStore;

	
	@RequestMapping(value="addUser",method = RequestMethod.POST)
	public boolean dbAddUserData(@RequestBody() UserDetailBean user) { 	

		return LocalDB.getInstance().addUser(user);
	} 
	
	
	@RequestMapping(method = RequestMethod.GET, produces="application/xml")
	public String forward(HttpServletRequest request, HttpServletResponse servletResponse) { 
		
//		logger.info("Entering the Twilio method");
//		try {
//			HttpResponse response = invokeEbayService(request,SERVICE_ENDPOINT);
//			logger.info("Response status fromo imageclean up = "+response.getStatusLine().getStatusCode());
//			HttpEntity entity = response.getEntity();
//			String resString =  EntityUtils.toString(entity, "UTF-8");
//			logger.info("REsponse from twilo proxy = "+resString);
//			return resString;
//			
//		} catch (Exception e) {
//			logger.error("Unable to invoke ebay service", e);
//			servletResponse.setStatus(500);
//			return "";
//		}
		
		logger.info("Entering the Twilio method changed approach");
		TwilioParameters parameters = getTwilioParameters(request);
		if(parameters == null){
			return "";
		}
		String response = listDialActions(parameters);
		return response==null?"":response;
	}

	
	@RequestMapping(value="test", method = RequestMethod.GET, produces="application/xml")
	public String test(HttpServletRequest request, HttpServletResponse servletResponse) { 		
		logger.info("Entering the Twilio method for test");
		if(keyValueStore == null){
			return getBackup();
		}
		String value = keyValueStore.getValue("twiloxml");
		if(value == null || value.isEmpty()){
			return getBackup();
		}
		return value;
		
		
	}
	
	private String getBackup(){
	
	    TwiMLResponse twiml = new TwiMLResponse();

    	try {
 	
        Gather gather = new Gather();
        gather.setAction("www.trackthespending.in/services/open/twilio/confirm");
        gather.setMethod("GET");
        gather.setNumDigits(1);
        gather.setTimeout(10);
        twiml.append(gather);
        
        gather.append(new Say("You are listing the following item"));
        gather.append(getPause(1));
        gather.append(new Say(ITEM_TITLE));
        gather.append(getPause(1));
        gather.append(new Say("Press one to confirm"));
        
        twiml.append(new Say("We didn't receive any input. Goodbye!"));
        
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

       return XML_START+twiml.toXML();
  
		
	}
	
	@RequestMapping(value= "/confirm",method = RequestMethod.GET, produces="application/xml")
	public String confirm(HttpServletRequest request, HttpServletResponse servletResponse) { 		
		logger.info("Entering the Twilio confirm method");
//		try {
//			HttpResponse response = invokeEbayService(request,SERVICE_ENDPOINT_CONFIRM);
//			logger.info("Response status fromo imageclean up = "+response.getStatusLine().getStatusCode());
//			HttpEntity entity = response.getEntity();
//			String resString =  EntityUtils.toString(entity, "UTF-8");
//			logger.info("REsponse from twilo proxy = "+resString);
//			return resString;
//			
//		} catch (Exception e) {
//			logger.error("Unable to invoke ebay service", e);
//			servletResponse.setStatus(500);
//			return "";
//		}
		
		TwilioParameters parameters = getTwilioParameters(request);
		if(parameters == null){
			return "";
		}
		String response = listConfirm(parameters);
		return response == null?"":response;
	}

	
	
	private HttpResponse invokeEbayService(HttpServletRequest request, String serviceEndPoint) throws Exception  {
		HttpClient client = HttpClientBuilder.create().build();
		
		HttpPost post = new HttpPost(serviceEndPoint);
		addHeaders(post,request);
		addParameters(post,request);
		
		return client.execute(post);		
	}

	private void addParameters(HttpPost post, HttpServletRequest request) throws Exception {
		
		TwilioParameters parameters = getTwilioParameters(request);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(parameters);
		
		logger.info("Set the json to http post "+json);
		
		try {
			post.setEntity(new StringEntity(json));		
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
		
	}


	private TwilioParameters getTwilioParameters(HttpServletRequest request) {
		TwilioParameters parameters = new TwilioParameters();
		
		Map<String, String[]> paramMap = request.getParameterMap();
		if(paramMap == null || paramMap.isEmpty()){
			return null;
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
		return parameters;
	}

	private void addHeaders(HttpPost post, HttpServletRequest request) {
				
		post.addHeader("Authorization", "TOKEN "+getUserToken());
		post.addHeader("Content-Type", "application/json");
		post.addHeader("Accept", "application/json");

	}
	

	private String getUserToken() {
		
		String token = System.getProperty("USER_TOKEN");
		
		if(token == null){
			token = System.getenv("USER_TOKEN"); 
		}

		return token == null?"":token;		
	}

	private Pause getPause(int l) {
		Pause pause = new Pause();
        pause.setLength(l);
		return pause;
	}
    

 private String listDialActions(TwilioParameters parameters) { 	
    	
    	// Create a TwiML response and add our friendly message.
        TwiMLResponse twiml = new TwiMLResponse();

    	String fromPhone = parameters.getParameters().get("From");
    	if(fromPhone == null){
    		fromPhone = parameters.getParameters().get("FROM");
    	}
    	
    	if(fromPhone == null){
    		logger.info("Unable to get FROM phone number");
    	}else{
    		logger.info("From phone "+fromPhone);
    	}
    	
    	UserDetailBean userDetail = LocalDB.getInstance().getUser(fromPhone);
    	String token = userDetail == null?null:userDetail.getToken();
    	if(token == null){
    		twiml = getEnrolmentMessage();
    		return XML_START+twiml.toXML();
    	}
        
    	try {
 	
        Gather gather = new Gather();
        gather.setAction("http://www.trackthespending.in/services/open/twilio/confirm");
        gather.setMethod("GET");
        gather.setNumDigits(1);
        twiml.append(gather);
        
        gather.append(new Say("You are listing the following item"));
        gather.append(getPause(1));
        gather.append(new Say(ITEM_TITLE));
        gather.append(getPause(1));
        gather.append(new Say("Press one to confirm"));
        
        twiml.append(new Say("We didn't receive any input. Goodbye!"));
        
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

       return XML_START+twiml.toXML();
    }
	
	private TwiMLResponse getEnrolmentMessage() {
		TwiMLResponse twiml = new TwiMLResponse();
		try {
			twiml.append(new Say(
					"You have not enrolled to the Phone listing program"));
			twiml.append(getPause(1));
			twiml.append(new Say("Please visit the eBay website to enrol"));
			twiml.append(getPause(1));
			twiml.append(new Say("Goodbye !"));

		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		return twiml;
	}
	
	public String listConfirm(TwilioParameters parameters) { 	
    	TwiMLResponse twiml;
    	String fromPhone = parameters.getParameters().get("From");
    	if(fromPhone == null){
    		fromPhone = parameters.getParameters().get("FROM");
    	}
    	
    	
    	UserDetailBean userDetail = LocalDB.getInstance().getUser(fromPhone);
    	String token = userDetail==null?null:userDetail.getToken();
    	if(token == null){
    		twiml = getEnrolmentMessage();
    		return XML_START+twiml.toXML();
    	}
    	
    	EbayListing listing = new EbayListing(token, userDetail.getPaypalId(), "168513369");
    	String itemId = listing.list();
    	    	
    	if("-1".equals(itemId)){
    		twiml = getFailureMessage();
    	}else{
            twiml = getSuccessMessage();
    	}
  
       return XML_START+twiml.toXML();
    }
	
	private TwiMLResponse getFailureMessage() {
		TwiMLResponse twiml = new TwiMLResponse();
        try {
			twiml.append(new Say("Sorry, we are unable to process your request at the momemt "));
			twiml.append(getPause(1));
			twiml.append(new Say("Please try again later"));
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
        return twiml;
	}



	private TwiMLResponse getSuccessMessage() {
		TwiMLResponse twiml = new TwiMLResponse();

    	try {
            twiml.append(new Say("Congratulations!!"));
            twiml.append(getPause(1));
            twiml.append(new Say("You have successfully listed the item"));
            twiml.append(getPause(1));
            twiml.append(new Say("Please visit My Ebay to check your listing"));
            twiml.append(getPause(1));
            twiml.append(new Say("GoodBye !"));
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
		return twiml;
	}
}
