package com.vikram.util.twilio;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PriceReco {
	
	private double startPrice = 198.0;;
	private double binPrice = 328.0; 
	private HttpClientTwilio client = new HttpClientTwilio();
	
	private static String PRICE_URL = "http://www.ebay.com/s/phone/price?sherpaTitle=$title&ePid=$epid&productReferenceId=$epid&condition=1000";
	
	
	public PriceReco(String productRefId, String title){
		invokeUrl(productRefId,title);
	}
	
	private void invokeUrl(String productRefId, String title) {
		String priceUrl = PRICE_URL.replace("$title", title);
		priceUrl = priceUrl.replace("$epid", productRefId);
		
		try {
			String response = client.getJSONResponse(priceUrl);
			
			JSONParser parse = new JSONParser();
			JSONObject resp =  (JSONObject) parse.parse(response);		
			parse(resp);
		} catch (Exception e ) {
			startPrice = 102.0;
			binPrice = 258.0;
		}
		
	}

	private void parse(JSONObject resp) {
		
		boolean success =parseGuarantee(resp);
		if(success){
			return;
		}
		
		parseTrendingPrice(resp);		
	}

	private boolean parseTrendingPrice(JSONObject resp) {
		
		if(!resp.containsKey("trendingPrice")){
			return false ;
		}
		
		JSONObject price = (JSONObject) resp.get("trendingPrice");
		if(!price.containsKey("value")){
			return false;
		}
		

		String value = (String) price.get("value");
		if(value == null || value.isEmpty()){
			return false;
		}
		
		binPrice = Double.parseDouble(value);
		
		return true;
	}

	private boolean parseGuarantee(JSONObject resp) {

		if(!resp.containsKey("guaranteeEligibility")){
			return false ;
		}
		
		JSONObject guarantee = (JSONObject) resp.get("guaranteeEligibility");
		if(!guarantee.containsKey("price")){
			return false;
		}
		
		JSONObject price = (JSONObject) guarantee.get("price");
		if(price == null || price.isEmpty()){
			return false;
		}
		
		String value = (String) price.get("value");
		if(value == null || value.isEmpty()){
			return false;
		}
		
		binPrice = Double.parseDouble(value);
		
		return true;

	}

	public double getStartPrice(){
		return startPrice;
	}
	
	
	public double getBinPrice(){
		return binPrice;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		
		String ITEM_TITLE_RECO = "Apple iPhone 5s 16GB  Space Gray AT&T Smartphone";


		PriceReco reco = new PriceReco("168553410",URLEncoder.encode(ITEM_TITLE_RECO, "UTF-8"));
		double sp = reco.getStartPrice();
		double bp = reco.getBinPrice();
		
		System.out.println(sp+" "+bp);
		
	}

}
