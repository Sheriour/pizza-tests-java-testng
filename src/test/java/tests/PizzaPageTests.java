package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.PizzaArchivePage;
import pages.PizzaGeneratorPage;
import system.DriverCoordinator;
import system.ExtentReporting;

import java.lang.reflect.Method;

import static system.PageRepository.*;

import static utils.Enums.PizzaAppTab.*;

public class PizzaPageTests
{

    @BeforeClass
    public void setUpReport(){
        ExtentReporting.initReport();
    }

    @BeforeMethod
    public void setUpTest(Method method) {
        getPage(LandingPage.class).launchPizzaPage();
        ExtentReporting.registerTest(method.getName());
    }

    @Test
    public void generateOnePizza()
    {
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();
        PizzaArchivePage archivePage = getPizzaArchivePage();

        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.clickGenerateAndArchive();
        Assert.assertEquals(generatorPage.getToastMessage(), "Generated one pizza.");
        landingPage.clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(archivePage.getNumberOfPizzaItems(), 2);
    }

    @Test
    public void generateOnePizzaAgain()
    {
        LandingPage landingPage = getLandingPage();
    }

    @AfterMethod
    public void teardown(ITestResult result){
        if (DriverCoordinator.hasDriver()) {
            if (!result.isSuccess()) {

                ExtentReporting.failTest(result.getMethod().getMethodName(), "Failed.");

                //TODO: figure out reporting and how to attach screenshots in extent
                //FileUtils.attachAllureScreenshot(DriverCoordinator.getWebDriver());

                // Take a screenshot...
                //final byte[] screenshot = ((TakesScreenshot)DriverCoordinator.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                // embed it in the report.
                //.attach(screenshot, "image/png", "screenshot");
            }
            DriverCoordinator.quitWebDriver();
        }
        deleteAllPages();
    }

    @AfterClass
    public void writeReport(){
        ExtentReporting.finaliseReport();
    }
}