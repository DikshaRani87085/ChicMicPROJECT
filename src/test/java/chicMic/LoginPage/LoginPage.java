package chicMic.LoginPage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginPage {
	WebDriver driver;
	String ChicmicUrl = "https://d20omqbtwm8stm.cloudfront.net/interview/add-candidate";

	@BeforeTest
	public void browserOpen() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	@Test
	public void chicMicURL() {
		// open chicmic url in chrome browser
		driver.get(ChicmicUrl);
		String webtitle = driver.getTitle();
		System.out.println(webtitle);
		// signin in the portal
		driver.findElement(By.id("timedragon_id_1859")).sendKeys("CHM/2014/068");
		driver.findElement(By.id("timedragon_id_1867")).sendKeys("123456");
		driver.findElement(By.id("timedragon_id_1874")).click();
	}

	@Test(dependsOnMethods = { "chicMicURL" })
	public void uniqueAddCandidate() throws AWTException {
		// go to add candidate page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("timedragon_id_14151")));
		driver.findElement(By.xpath("//a[@id='Interview']")).click();
		driver.findElement(By.id("AddCandidate")).click();

		// enter details of the candidate
		driver.findElement(By.xpath("//input[@id='candidateDetail-name']")).sendKeys("Diksha");
		driver.findElement(
				By.xpath("//label[@id='timedragon_id_12994']/following-sibling::input[@id='candidateDetail-email']"))
				.sendKeys("qualityanalyst476@gmail.com");
		driver.findElement(By.id("candidateDetail-contactNumber")).sendKeys("9817340654");
		driver.findElement(By.xpath("//button[contains(text(),\"Verify\")]")).click();

		// Enter Basic details of Candidate

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),\" Verified \")]")));

		// Wait for any loading overlay to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader-body")));
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.id("timedragon_id_12784")));

		// actions.sendKeys(Keys.PAGE_UP).perform();
		driver.findElement(By.xpath("//select[@id='candidateDetail-contactedPlatform']")).click();
		driver.findElement(By.id("candidateDetail-contactedPlatform-INDEED")).click();
		driver.findElement(By.xpath("//input[@name='appliedForTeam']")).sendKeys("QA");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("timedragon_id_12738")));
		driver.findElement(By.xpath("//span[contains(text(),'QA')]")).click();
		driver.findElement(By.xpath("//input[@name='designation']")).sendKeys("QA");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("timedragon_id_12738")));
		driver.findElement(By.xpath("//span[contains(text(),'Associate QA Engineer')]")).click();
		driver.findElement(By.xpath("//input[@name='skillSet']")).sendKeys("Java");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("timedragon_id_12738")));
		driver.findElement(By.xpath("//span[contains(text(),'Java')]")).click();
		actions.moveToElement(driver.findElement(By.xpath("//h3[contains(text(),\"Experience Details\")]")));

		WebElement browserButton = driver.findElement(By.xpath("//span[contains(text(),'browse')]"));

		// Upload file using Robot class
		uploadFile(browserButton, "C:\\Users\\Lenovo\\eclipse-workspace\\chicMic\\src\\test\\java\\cVFolder\\DIkshaCV.pdf");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader-body")));

		// Move to Experience detail option
		actions.moveToElement(driver.findElement(By.id("candidate_detail_totalExperience_label_3177")));

		// Enter Experience Year Details
		selectDropdownValue("candidateDetail-totalWorkExperience_years", "2");

		// Enter Experience Month Details
		selectDropdownValue("candidateDetail-totalWorkExperience_months", "8");

		// Enter relevant Experience
		selectDropdownValue("candidateDetail-relavantWorkExperience_years", "2");
		selectDropdownValue("candidateDetail-relavantWorkExperience_months", "8");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader-body")));

		// Choose last appraisal date
		driver.findElement(By.xpath("//input[@id='Last Appraisal Date']")).click();
		selectDate("January", "2024", "2");

		// No of working days in last company
		selectDropdownValue("candidateDetail-workingDays", "5");

		// Last working day
		driver.findElement(By.id("Last Working Date")).click();
		driver.findElement(By.xpath("//td//span[contains(text(),\"22\")]")).click();

		// Current Salary
		driver.findElement(By.id("candidateDetail-currentSalary")).sendKeys("400000");

		// Expected Salary
		driver.findElement(By.id("candidateDetail-expectedtSalary")).sendKeys("600000");

		// Notice Period
		selectDropdownValue("candidateDetail-noticePeriod", "3");

		// Move to Address detail option
		actions.moveToElement(driver.findElement(By.id("timedragon_id_12784")));

		// Enter native State
		selectDropdownValue("candidateDetail-addresses-stateNative", "Haryana (HR)");

		// Enter Native city
		selectDropdownValue("candidateDetail-addresses-cityNative", "Ambala");

		// Current State
		selectDropdownValue("candidateDetail-addresses-currentState", "Haryana (HR)");

		// Enter Current city
		driver.findElement(By.id("candidateDetail-Current-address"))
				.sendKeys("Vill-Rajouli, P.O-Badhouli, Distt-Ambala, Haryana, Pin Code-134203");

		// Click on Send Screening Button
		driver.findElement(By.id("candidate_detail_submit_btn_3241")).click();

	}

	// Upload file method using robot class
	public void uploadFile(WebElement element, String filePath) throws AWTException {

		element.click();
		Robot rb = new Robot();
		rb.delay(1000);
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
	}

	// select date method
	public void selectDate(String month, String year, String day) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		String currentMonth = driver
				.findElement(By
						.xpath("(//div[@class='p-datepicker-title ng-tns-c2639595829-5']//button[@type='button'])[1]"))
				.getText();
		String currentYear = driver
				.findElement(By
						.xpath("(//div[@class='p-datepicker-title ng-tns-c2639595829-5']//button[@type='button'])[2]"))
				.getText();

		while (!(currentMonth.equals(month) && currentYear.equals(year))) {
			driver.findElement(By.className("p-datepicker-prev-icon")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("(//div[@class='p-datepicker-title ng-tns-c2639595829-5']//button[@type='button'])[1]")));
			currentMonth = driver
					.findElement(By.xpath(
							"(//div[@class='p-datepicker-title ng-tns-c2639595829-5']//button[@type='button'])[1]"))
					.getText();
			currentYear = driver
					.findElement(By.xpath(
							"(//div[@class='p-datepicker-title ng-tns-c2639595829-5']//button[@type='button'])[2]"))
					.getText();
		}

		driver.findElement(By.xpath("(//span[contains(text(),'" + day + "')])[1]")).click();
	}

	// Select drop down method
	public void selectDropdownValue(String string, String string2) {
		WebElement dropdownElement = driver.findElement(By.id(string));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByValue(string2);

	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
