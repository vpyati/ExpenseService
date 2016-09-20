package com.vikram.util;

import java.util.ArrayList;
import java.util.List;

import com.ebay.soap.eBLBaseComponents.AmountType;
import com.ebay.soap.eBLBaseComponents.InsuranceOptionCodeType;
import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
import com.ebay.soap.eBLBaseComponents.ShippingServiceOptionsType;
import com.ebay.soap.eBLBaseComponents.ShippingTypeCodeType;

public class ShippingRecoHelper {
	
	public static ShippingDetailsType createShippingDetails() throws Exception {
        // Shipping details.
        ShippingDetailsType sd = new ShippingDetailsType();

        AmountType at = new AmountType();
        at.setValue(3.0);
        sd.setInsuranceFee(at);
        sd.setInsuranceOption(InsuranceOptionCodeType.OPTIONAL);
        sd.setPaymentInstructions("Please pay using PayPal");
        sd.setShippingType(ShippingTypeCodeType.FLAT);

        //
        ShippingServiceOptionsType st1 = new ShippingServiceOptionsType();

        st1.setShippingService("USPSPriority");
        at = new AmountType();
        at.setValue(8.0);
        st1.setShippingServiceAdditionalCost(at);
        at = new AmountType();
        at.setValue(1);
        st1.setShippingServiceCost(at);
        st1.setShippingServicePriority(new Integer(1));
        at = new AmountType();
        at.setValue(1.0);
        st1.setShippingInsuranceCost(at);

        List<ShippingServiceOptionsType> shippingServiceList = new ArrayList<ShippingServiceOptionsType>();
        shippingServiceList.add(st1);
        
        ShippingServiceOptionsType[] service = new ShippingServiceOptionsType[shippingServiceList.size()];
        shippingServiceList.toArray(service);
        sd.setShippingServiceOptions(service);

        return sd;
    }
}
