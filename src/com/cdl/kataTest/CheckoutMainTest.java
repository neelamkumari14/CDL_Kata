package com.cdl.kataTest;

import com.cdl.kata.CheckoutKata;
import com.cdl.kata.SpecialOfferItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is to test the checkout
 */
public class CheckoutMainTest {

    CheckoutKata checkout = new CheckoutKata();
    Map<String, String> priceMap ;
    Map<String, SpecialOfferItem> offersMap ;

    @BeforeEach
    public void checkout(){
        priceMap =new HashMap<>();
        priceMap.put("A","50");
        priceMap.put("B","30");
        priceMap.put("C","20");
        priceMap.put("D","15");

        offersMap = new HashMap<>();
        offersMap.put("A",new SpecialOfferItem("A",3,130.0));
        offersMap.put("B",new SpecialOfferItem("B",2,45.0));

    }

	@Test
	public void calculateTotalPriceWithAandB(){
        CheckoutKata.addItemToQuantityMap("A");
        CheckoutKata.addItemToQuantityMap("B");
		Assertions.assertEquals( 80.0,checkout.calculateTotalPrice( priceMap,offersMap));
	}

    @Test
    public void calculateTotalPriceWithTwoAandTwoB(){
        CheckoutKata.addItemToQuantityMap("A");
        CheckoutKata.addItemToQuantityMap("B");
        Assertions.assertEquals( 145.0,checkout.calculateTotalPrice( priceMap,offersMap));
    }

    @Test
    public void calculateTotalPriceWithThreeAandTwoBandOneC(){
        CheckoutKata.addItemToQuantityMap("C");
        CheckoutKata.addItemToQuantityMap("A");
        CheckoutKata.addItemToQuantityMap("D");
        Assertions.assertEquals( 210.0,checkout.calculateTotalPrice( priceMap,offersMap));
    }



}
