package com.mmt.testCases;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.mmt.pageObjects.FlightsPage;
import com.mmt.utilities.Reporting;


public class FlighSearchTests extends BaseClass {

	@Test
	public void TC01_VerifyFlighBooking() throws IOException, InterruptedException {

		
		FlightsPage flightspage = new FlightsPage(driver);
		flightspage.SelectCities();
		logger.info("Cities selected");
		captureScreen(driver,"Cities selected");
		
		/*
		 * flightspage.SelectDateandTravellers();
		 * captureScreen(driver,"Dates and Travellers selected");
		 * logger.info("Dates and Travellers selected");
		 * 
		 * flightspage.VerifyDetailsonHeader();
		 * captureScreen(driver,"Travelling Details on Header");
		 * logger.info("Travelling Details on Header");
		 * 
		 * flightspage.VerifyPriceSlider(); captureScreen(driver,"Price Slider done");
		 * logger.info("Price Slider done");
		 * 
		 * flightspage.VerifyNonStopFiltration();
		 * captureScreen(driver,"Verifying non stop functionality");
		 * logger.info("Verifying non stop functionality");
		 */
	}
	
	
}

