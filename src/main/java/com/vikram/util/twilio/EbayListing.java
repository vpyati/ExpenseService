package com.vikram.util.twilio;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.call.AddItemCall;
import com.ebay.soap.eBLBaseComponents.AmountType;
import com.ebay.soap.eBLBaseComponents.BestOfferDetailsType;
import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.ebay.soap.eBLBaseComponents.CountryCodeType;
import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
import com.ebay.soap.eBLBaseComponents.FeesType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ListingDurationCodeType;
import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
import com.ebay.soap.eBLBaseComponents.ProductListingDetailsType;
import com.ebay.soap.eBLBaseComponents.ReturnPolicyType;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;
import com.vikram.util.ShippingRecoHelper;

public class EbayListing {
	
	private static String ITEM_TITLE = "Apple iPhone 5c 16GB  White AT&amp;T Smartphone";
	private static String EBAY_API_ENDPOINT = "https://api.ebay.com/wsapi";
	private static Logger logger = LoggerFactory.getLogger(EbayListing.class);
	
	private String token;
	private String paypalId;
	private String epid;
	
	public EbayListing(String token, String paypalId, String epid){
		this.token = token;
		this.paypalId = paypalId == null?"vikrampyati@gmail.com":paypalId;
		this.epid = epid;
	}
	
	
	public String list(){
		
		// [Step 1] Initialize eBay ApiContext object
		ApiContext apiContext = getApiContext();

		// [Step 2] Create a new item object.
		logger.info("===== [2] Item Information ====");
		ItemType item = createItem();
		
		// [Step 3] Create call object and execute the call.
		logger.info("===== [3] Execute the API call ====");
		logger.info("Begin to call eBay API, please wait ...");
	
		AddItemCall api = new AddItemCall(apiContext);
		api.setItem(item);
		try {
			FeesType fees = api.addItem();
		} catch (Exception e) {
			logger.error("Unable to create item", e);
			return "-1";
		}
		
		return item.getItemID();
	}
	
	private ItemType createItem() {
        ItemType item = new ItemType();
        item.setSite(SiteCodeType.US);
        item.setCurrency(CurrencyCodeType.USD);
        item.setListingType(ListingTypeCodeType.AUCTION);
        item.setTitle(ITEM_TITLE);
       // item.setDescription("iphone 6s with 64 gb and gold case.In a very good condition.List its");
        item.setConditionID(1000);
        item.setCountry(CountryCodeType.US);
        
        addPriceSection(item);
        
        item.setPostalCode("95126");

        BestOfferDetailsType bo = new BestOfferDetailsType();
        bo.setBestOfferEnabled(new Boolean(false));
        item.setBestOfferDetails(bo);

        CategoryType cat = new CategoryType();
        cat.setCategoryID("9355");
        item.setPrimaryCategory(cat);

        item.setQuantity(new Integer(1));

        // Payment

        List<BuyerPaymentMethodCodeType> paymentMethods = new ArrayList<BuyerPaymentMethodCodeType>();
        paymentMethods.add(BuyerPaymentMethodCodeType.PAY_PAL);
        
        BuyerPaymentMethodCodeType[] payment = new BuyerPaymentMethodCodeType[paymentMethods.size()];
        paymentMethods.toArray(payment);
        item.setPaymentMethods(payment);
        
        item.setPayPalEmailAddress(paypalId);
        //
        item.setShippingDetails(ShippingRecoHelper.createShippingDetails());

        //fill in mandatory fields
        item.setDispatchTimeMax(Integer.valueOf(1));
        ReturnPolicyType returnPolicy = new ReturnPolicyType();
        returnPolicy.setReturnsAcceptedOption("ReturnsAccepted");
        item.setReturnPolicy(returnPolicy);

        item.setSKU("1122334455-01");

        addProductDetails(item);
        
        return item;

	}


	private ApiContext getApiContext(){

		ApiContext apiContext = new ApiContext();

		// set Api Token to access eBay Api Server
		ApiCredential cred = apiContext.getApiCredential();

		cred.seteBayToken(token);
		// set Api Server Url
		apiContext.setApiServerUrl(EBAY_API_ENDPOINT);

		return apiContext;
	}

	private static void addPriceSection(ItemType item) {
		
		AmountType startPrice = getAmount(102.0);
		AmountType binPrice = getAmount(190.0);
		item.setStartPrice(startPrice);
		item.setBuyItNowPrice(binPrice);

		item.setListingDuration(ListingDurationCodeType.DAYS_7.value());
		item.setListingType(ListingTypeCodeType.CHINESE);
	}
	
	private static AmountType getAmount(double value) {
		AmountType at = new AmountType();
        at.setValue(value);
		return at;
	}
	
	private void addProductDetails(ItemType item) {

		ProductListingDetailsType productlistingDetailsType = new ProductListingDetailsType();
		productlistingDetailsType.setProductReferenceID(epid);
		//productlistingDetailsType.setUPC("885909741496");
		productlistingDetailsType.setIncludeStockPhotoURL(true);
		productlistingDetailsType.setUseFirstProduct(true);
		productlistingDetailsType.setUseStockPhotoURLAsGallery(true);
		productlistingDetailsType.setReturnSearchResultOnDuplicates(true);
		item.setProductListingDetails(productlistingDetailsType);

	}

}
