package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import system.DriverCoordinator;
import system.PageRepository;

import static system.DriverCoordinator.*;

public class PizzaPageTests
{
    @BeforeClass
    public void setUp() {

        //Maximize the browser window
        getWebDriver().manage().window().maximize();

        //Navigate to main pizza website
        getWebDriver().get("https://main.d3ljwfp72dyhph.amplifyapp.com/");
    }

    @Test
    public void myTest()
    {
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Pizza Generator']"))).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Generate & Archive']"))).click();
    }

    @AfterMethod
    public void teardown(ITestResult result){
        if (DriverCoordinator.hasDriver()) {
            if (!result.isSuccess()) {
                //TODO: figure out reporting and how to attach screenshots in testng

                // Take a screenshot...
                //final byte[] screenshot = ((TakesScreenshot)DriverCoordinator.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                // embed it in the report.
                //.attach(screenshot, "image/png", "screenshot");
            }
            DriverCoordinator.quitWebDriver();
        }
        PageRepository.deleteAllPages();
    }
}