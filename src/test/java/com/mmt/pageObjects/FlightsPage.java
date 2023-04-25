package com.mmt.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.mmt.testCases.BaseClass;

public class FlightsPage {

	WebDriver ldriver;
	SoftAssert softassert = new SoftAssert();

	String NextMonthXpath = "//div[contains(text(),'?')]".replace("?", GetNextMonth());

	public FlightsPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	@FindBy(xpath = "//input[@id='fromCity']")
	@CacheLookup
	WebElement FromJourneyLnk;

	@FindBy(xpath = "//input[@id='toCity']")
	@CacheLookup
	WebElement ToJourneyLnk;

	@FindBy(xpath = "//input[@placeholder='From']")
	@CacheLookup
	WebElement FromJourneyInput;

	@FindBy(xpath = "//p[contains(text(),'Ahmedabad')]")
	@CacheLookup
	WebElement FromJourneyRes;

	@FindBy(xpath = "//p[contains(text(),'Pune')]")
	@CacheLookup
	WebElement ToJourneyRes;

	@FindBy(xpath = "//input[@placeholder='To']")
	@CacheLookup
	WebElement ToJourneyInput;

	@FindBy(xpath = "//a[text()='Search']")
	@CacheLookup
	WebElement SearchBtn;

	@FindBy(xpath = "//span[contains(text(),'Departure')]")
	@CacheLookup
	WebElement DepartureDD;

	@FindBy(xpath = "//span[contains(text(),'Travellers & Class')]")
	@CacheLookup
	WebElement TravellerClassDD;

	@FindBy(xpath = "//div[@class='DayPicker-Month'][2]//div[@class='DayPicker-Week'][1]//p[text()='1']")
	WebElement NextMonthDay1;

	@FindBy(xpath = "//li[@data-cy='adults-2']")
	WebElement Adults2;

	@FindBy(xpath = "//li[@data-cy='infants-1']")
	WebElement Infants1;

	@FindBy(xpath = "//li[@data-cy='children-1']")
	WebElement Childern1;

	@FindBy(xpath = "//button[@data-cy='travellerApplyBtn']")
	WebElement ApplyBtn;

	@FindBy(xpath = "//span[@aria-label='Previous Month']")
	WebElement PreArrow;

	@FindBy(xpath = "//span[@aria-label='Next Month']")
	WebElement NextArrow;

	@FindBy(xpath = "//div[@class='rangeslider__handle']")
	WebElement PriceSlider;

	@FindBy(xpath = "//div[@class='rangeslider__handle-label']")
	WebElement PriceSlider2;

	@FindBy(xpath = "//button[text()='OKAY, GOT IT!']")
	WebElement GotItBtn;

	@FindBy(xpath = "//div[@class='hsw_inner']/div[contains(@class,'hsw_inputBox')]//div[@class='multiDropDownVal']")
	WebElement TripType_Header;

	@FindBy(xpath = "//div[@class='hsw_inner']/div[contains(@class,'hsw_inputBox')]/input")
	public List<WebElement> TripData_Header;

	@FindBy(xpath = "//ul[@class='appliedFilter']")
	WebElement AppliedFilterText;

	@FindBy(xpath = "//button[./span[contains(text(),'View Prices')]]/preceding-sibling::div/p")
	public List<WebElement> FlightPrices;

	@FindBy(xpath = "//div[./p[contains(text(),'Stops From Ahmedabad')]]//p[contains(text(),'Non Stop')]")
	WebElement StopsFromAhmedabadText;

	@FindBy(xpath = "//div[./p[contains(text(),'Stops From Ahmedabad')]]//div[./p[contains(text(),'Non Stop')]]/preceding-sibling::span/input[@id='listingFilterCheckbox']")
	WebElement NonStopsFromAhmedabadCheckbox;

	public void SelectCities() {
		FromJourneyLnk.click();
		BaseClass.Pause(1000);
		BaseClass.WaitForClickable(10, FromJourneyInput);
		FromJourneyInput.sendKeys("Ahmedabad");
		BaseClass.Pause(1000);
		FromJourneyRes.click();
		BaseClass.Pause(1000);
		ToJourneyLnk.click();
		BaseClass.Pause(1000);
		BaseClass.WaitForClickable(10, ToJourneyInput);
		ToJourneyInput.sendKeys("Pune");
		BaseClass.Pause(1000);
		ToJourneyRes.click();
		BaseClass.Pause(1000);
		
	}

	public void SelectDateandTravellers() {
		while (PreArrow.isDisplayed()) {
			PreArrow.click();
			BaseClass.Pause(2000);
		}
		SelectNextMonth();
		BaseClass.Pause(2000);
		TravellerClassDD.click();
		BaseClass.Pause(2000);
		TravellerClassDD.click();
		Adults2.click();
		Infants1.click();
		Childern1.click();
		ApplyBtn.click();

		SearchBtn.click();
		BaseClass.Pause(2000);
	}

	public void VerifyDetailsonHeader() throws IOException {
		String NextMonth = GetNextMonth();
		String[] ExpectedData = { "One Way", "Ahmedabad", "Pune", NextMonth, "4 Travellers, Economy" };

		BaseClass.WaitForClickable(40, GotItBtn);
		GotItBtn.click();
		
		softassert.assertTrue(TripType_Header.getText().contains(ExpectedData[0]), "Trip type is not One Way");
		softassert.assertTrue(TripData_Header.get(0).getAttribute("value").contains(ExpectedData[1]),
				"From is not 'Ahmedabad'");
		softassert.assertTrue(TripData_Header.get(1).getAttribute("value").contains(ExpectedData[2]),
				"To is not 'Pune'");
		softassert.assertTrue(TripData_Header.get(2).getAttribute("value").contains(ExpectedData[3]),
				"Depart value is not correct");
		softassert.assertTrue(TripData_Header.get(4).getAttribute("value").contains(ExpectedData[4]),
				"Passangers and class value is not correct");
	}

	public void VerifyPriceSlider() {

		Actions action = new Actions(BaseClass.driver);
		action.dragAndDropBy(PriceSlider2, -200, 0).perform();
		BaseClass.Pause(2000);
		softassert.assertTrue(AppliedFilterText.getText().equals("₹3,996 - ₹6,900"), "Filtered Price is correct");
		for (int i = 0; i <= FlightPrices.size() - 1; i++) {
			int FlighPriceVal = Integer.parseInt(FlightPrices.get(i).getText().substring(2).replace(",", "").trim());
			softassert.assertTrue(FlighPriceVal < 6900, "Price filtration is not proper");
		}
	}

	public void VerifyNonStopFiltration() {
		int FromAhdNonStopCount = Integer.parseInt(StopsFromAhmedabadText.getText().replaceAll("[^0-9]", ""));
		BaseClass.Pause(2000);
		NonStopsFromAhmedabadCheckbox.click();
		BaseClass.Pause(3000);

		int TotalFlights = FlightPrices.size();
		softassert.assertTrue(TotalFlights == FromAhdNonStopCount,
				"Count mismatched for Non Stop Flights search results on Right side");
	}

	public String GetNextMonth() {
		Calendar cal = GregorianCalendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy");

		Date currentMonth = new Date();
		cal.setTime(currentMonth);		
		// Add next month
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		String nextMonthAsString = df.format(cal.getTime());
		return nextMonthAsString;
	}

	public void SelectNextMonth() {
		while (!BaseClass.driver.findElement(By.xpath(NextMonthXpath)).isDisplayed()) {
			NextArrow.click();
			BaseClass.Pause(2000);

		}
		NextMonthDay1.click();
	}

}
