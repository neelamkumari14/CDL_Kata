package com.cdl.kata;;
import com.cdl.kata.Constants.AppConstants;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is to checkout operations
 */
public class CheckoutKata {

	private static Map<String, Integer> itemQuantityMap = new ConcurrentHashMap<String, Integer>();
	private static List<String> wronglyAddedItemList = new ArrayList<String>();

	/**
	 * This method adds item and quantity into quantity map
	 * @param item
	 */
	public static void addItemToQuantityMap(String item) {
		// If the item does not exist in the cart then add it
		if (!itemQuantityMap.containsKey(item))
			itemQuantityMap.put(item, 1);
		else {
			// if the cart already contains the item then just update the quantity
			itemQuantityMap.put(item, itemQuantityMap.get(item) + 1);
		}

	}

	/**
	 * This method calculates total price for the items
	 * @param priceMap
	 * @param offersMap
	 * @return
	 */
	public  double calculateTotalPrice(Map<String,String> priceMap,Map<String, SpecialOfferItem> offersMap){
		Double total = 0.0;
		Set<String> keys = itemQuantityMap.keySet();
		//for (Iterator<String> i = keys.iterator(); i.hasNext();) {
		Iterator<String> it = itemQuantityMap.keySet().iterator();
		while(it.hasNext()){
			Double itemTotal = 0.0;
			String scannedItem = it.next();
			Integer scannedQuantity = itemQuantityMap.get(scannedItem);

			if(offersMap.containsKey(scannedItem)){
				SpecialOfferItem offer = offersMap.get(scannedItem);
				Long  offerQuantity =Long.valueOf( offer.quantity);

				itemTotal = (scannedQuantity % offerQuantity)*Long.valueOf(priceMap.get(scannedItem)) + (scannedQuantity / offer.quantity) * offer.price;
			}
			else // If there is no offer on the scannedItem then just fetch the price from the price map and calculate the total
			{
				itemTotal= (scannedQuantity)*Double.valueOf(priceMap.get(scannedItem));
			}
			total +=itemTotal;
			System.out.println(AppConstants.ITEM + scannedItem + AppConstants.QUANTITY + scannedQuantity + AppConstants.TOTAL + itemTotal);
		}

		System.out.println(AppConstants.CART_REVIEW_COMPLETED);
		if (!wronglyAddedItemList.isEmpty()) {
			System.out.println(
					AppConstants.WRONG_ADDED_ITEM_LIST + wronglyAddedItemList);
		}
		System.out.println(AppConstants.TOTAL_AMOUNT_CHECKOUT + total);

		return total;
	}

	/**
	 * This method reviews the cart and remove the wrongly added item
	 * @param  - modifies quantity map
	 * @return void
	 */
	public static void reviewCartItem(Map<String, String> priceMap) {
		Iterator<String> it = itemQuantityMap.keySet().iterator();
		while (it.hasNext()) {
			String item = it.next();
			if (!priceMap.containsKey(item)) {
				wronglyAddedItemList.add(item);
				itemQuantityMap.remove(item);
			}

		}

	}

}
