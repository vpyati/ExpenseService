package com.vikram.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.call.AddFixedPriceItemCall;
import com.ebay.sdk.call.AddItemCall;
import com.ebay.sdk.helper.ConsoleUtil;
import com.ebay.sdk.util.eBayUtil;
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
import com.ebay.soap.eBLBaseComponents.NameValueListArrayType;
import com.ebay.soap.eBLBaseComponents.NameValueListType;
import com.ebay.soap.eBLBaseComponents.ProductListingDetailsType;
import com.ebay.soap.eBLBaseComponents.ReturnPolicyType;
import com.ebay.soap.eBLBaseComponents.SellerPaymentProfileType;
import com.ebay.soap.eBLBaseComponents.SellerProfilesType;
import com.ebay.soap.eBLBaseComponents.SellerReturnProfileType;
import com.ebay.soap.eBLBaseComponents.SellerShippingProfileType;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;
import com.ebay.soap.eBLBaseComponents.VariationType;
import com.ebay.soap.eBLBaseComponents.VariationsType;

public class EbayCallHelper {
	
	
	// sample category ids supporting variations
	private static Set<String> sampleCatIDSet = new HashSet<String>();
	
	static {
		  sampleCatIDSet.add("4174");
		  sampleCatIDSet.add("27432");
		  sampleCatIDSet.add("42428");
	}

	public static void main(String[] args) {

		try {

			System.out.print("\n");
			System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
			System.out.print("+ Welcome to eBay SDK for Java Sample +\n");
			System.out.print("+  - ConsoleAddFixedItem              +\n");
			System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
			System.out.print("\n");

			// [Step 1] Initialize eBay ApiContext object
			System.out.println("===== [1] Account Information ====");
			ApiContext apiContext = getApiContext();

			// [Step 2] Create a new item object.
			System.out.println("===== [2] Item Information ====");
			ItemType item = createItem();
			
			// [Step 3] Create call object and execute the call.
			System.out.println("===== [3] Execute the API call ====");
			System.out.println("Begin to call eBay API, please wait ...");
			
			
			
			AddItemCall api = new AddItemCall(apiContext);
			api.setItem(item);
			FeesType fees = api.addItem();
			System.out.println("End to call eBay API, show call result ...");
			System.out.println();

			// [Setp 4] Display results.
			System.out.println("The list was listed successfully!");

			double listingFee = eBayUtil.findFeeByName(fees.getFee(),
					"ListingFee").getFee().getValue();
			System.out.println("Listing fee is: "
					+ new Double(listingFee).toString());
			System.out.println("Listed Item ID: " + item.getItemID());
		} catch (Exception e) {
			System.out.println("Fail to list the item.");
			e.printStackTrace();
		}
	}

	/**
	 * Populate eBay SDK ApiContext object with data input from user
	 * 
	 * @return ApiContext object
	 */
	private static ApiContext getApiContext() throws IOException {

		String input;
		ApiContext apiContext = new ApiContext();

		// set Api Token to access eBay Api Server
		ApiCredential cred = apiContext.getApiCredential();
		input = ConsoleUtil
				.readString("Enter your eBay Authentication Token: ");
		cred.seteBayToken(input);
		// set Api Server Url
		input = ConsoleUtil
				.readString("Enter eBay SOAP server URL (e.g., https://api.ebay.com/wsapi): ");
		apiContext.setApiServerUrl(input);

		return apiContext;
	}

	/**
	 * Build sample variations
	 * 
	 * @return VariationsType object
	 */
	public static VariationsType buildVariationsType() {
		// listing variations
		VariationsType variations = new VariationsType();

		// first variation
		VariationType variation1 = new VariationType();
		// create the content of VariationSpecifics
		NameValueListArrayType nvArray1 = new NameValueListArrayType();
		NameValueListType nv11 = new NameValueListType();
		nv11.setName("Color");
		nv11.setValue(new String[] { "RED" });
		NameValueListType nv12 = new NameValueListType();
		nv12.setName("Size");
		nv12.setValue(new String[] { "M" });
		nvArray1.setNameValueList(new NameValueListType[] { nv11, nv12 });
		// set variation-level specifics for first variation
		variation1.setVariationSpecifics(nvArray1);
		// set start price
		AmountType amount1 = new AmountType();
		amount1.setValue(Double.valueOf(100));
		variation1.setStartPrice(amount1);
		// set quantity
		variation1.setQuantity(new Integer(10));
		// set variation name
		variation1.setVariationTitle("RED,M");

		// second variation
		VariationType variation2 = new VariationType();
		// create the content of specifics for each variation
		NameValueListArrayType nvArray2 = new NameValueListArrayType();
		NameValueListType nv21 = new NameValueListType();
		nv21.setName("Color");
		nv21.setValue(new String[] { "BLACK" });
		NameValueListType nv22 = new NameValueListType();
		nv22.setName("Size");
		nv22.setValue(new String[] { "L" });
		nvArray2.setNameValueList(new NameValueListType[] { nv21, nv22 });
		// set variation-level specifics for first variation
		variation2.setVariationSpecifics(nvArray2);
		// set start price
		AmountType amount2 = new AmountType();
		amount2.setValue(Double.valueOf(110));
		variation2.setStartPrice(amount2);
		// set quantity
		variation2.setQuantity(new Integer(20));
		// set variation name
		variation2.setVariationTitle("BLACK,L");

		// set variation
		variations.setVariation(new VariationType[] { variation1, variation2 });

		// create the content of specifics for variations
		NameValueListArrayType nvArray3 = new NameValueListArrayType();
		NameValueListType nv31 = new NameValueListType();
		nv31.setName("Color");
		nv31.setValue(new String[] { "BLACK", "RED" });
		NameValueListType nv32 = new NameValueListType();
		nv32.setName("Size");
		nv32.setValue(new String[] { "M", "L" });
		nvArray3.setNameValueList(new NameValueListType[] { nv31, nv32 });

		// set variationSpecifics
		variations.setVariationSpecificsSet(nvArray3);
		return variations;
	}

	public static ItemType createItem() throws Exception {
        ItemType item = new ItemType();
        item.setSite(SiteCodeType.US);
        item.setCurrency(CurrencyCodeType.USD);
        item.setListingType(ListingTypeCodeType.AUCTION);
        item.setTitle(ConsoleUtil.readString("Title: "));
        item.setDescription("iphone 6s with 64 gb and gold case.In a very good condition.List its");
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
        
        item.setPayPalEmailAddress("vikrampyati@gmail.com");
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
	
 private static void addProductDetails(ItemType item){
    	 
    	 ProductListingDetailsType productlistingDetailsType = new ProductListingDetailsType();
    	 productlistingDetailsType.setUPC("885909741496");
    	 productlistingDetailsType.setIncludeStockPhotoURL(true);
    	 productlistingDetailsType.setUseFirstProduct(true);
    	 productlistingDetailsType.setUseStockPhotoURLAsGallery(true);
    	 productlistingDetailsType.setReturnSearchResultOnDuplicates(true);
    	 item.setProductListingDetails(productlistingDetailsType);
    	
    	 
    }
}
