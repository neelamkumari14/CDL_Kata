package com.cdl.kata;;

import com.cdl.kata.Constants.AppConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CheckoutMain {
	public static void main(String[] args) {
		Map<String, String> priceMap = loadPrice();
		Map<String, SpecialOfferItem> offersMap = loadOffers();

		List<String> items = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		System.out.println(AppConstants.START_SCAN_AND_CALCULATION);
		try {String userInput = scanner.nextLine();
			while (!userInput.equals(AppConstants.EXCLAMATION_MARK)) {
				CheckoutKata.addItemToQuantityMap(userInput);
				userInput = scanner.nextLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		CheckoutKata checkout = new CheckoutKata();
		System.out.println(AppConstants.STAR_VIEW);
		CheckoutKata.reviewCartItem(priceMap);
		System.out.println(AppConstants.TOTAL_PRICE + checkout.calculateTotalPrice(  priceMap,offersMap));
		System.out.println(AppConstants.STAR_VIEW);

	}

	public static Map<String,String> loadPrice(){
		// load from the filesystem
		String propertiesFilename = AppConstants.PRICE_PROPERTIES_PATH;
		Map<String, String> priceProperties = new HashMap<>();

		try (InputStream stream = new FileInputStream(propertiesFilename))
		{
			Properties prop = new Properties() {
				@Override
				public synchronized Object put(Object key, Object value) {
					return priceProperties.put(key.toString(), value.toString());
				}
			};
			prop.load(stream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return priceProperties;
	}

	public static Map<String, SpecialOfferItem>  loadOffers(){
		// load from the filesystem
		String propertiesFilename = AppConstants.OFFER_PROPERTIES_PATH;
		Map<String, SpecialOfferItem> itemOffersProperties = new TreeMap<>();

		try (InputStream stream = new FileInputStream(propertiesFilename))
		{
			Properties prop = new Properties() {
				@Override
				public synchronized Object put(Object key, Object value) {
					String mapKey = key.toString();
					String[] arrOfStr = mapKey.split(AppConstants.HYPHEN, 2);
					String s1 = arrOfStr[0];
					String s2 = arrOfStr[1];
					SpecialOfferItem offer = new SpecialOfferItem(s1, Integer.valueOf(s2),Double.valueOf(value.toString()));
					return itemOffersProperties.put(s1,offer);
				}
			};
			prop.load(stream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return itemOffersProperties;
	}
}
