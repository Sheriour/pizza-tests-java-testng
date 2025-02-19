package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.PizzaGeneratorPage;
import system.DriverCoordinator;
import system.PageRepository;

import static utils.Enums.PizzaAppTab.*;
import static system.DriverCoordinator.*;

public class PizzaPageTests
{
    @BeforeMethod
    public void setUp() {
        PageRepository.getPage(LandingPage.class).launchPizzaPage();
    }

    @Test
    public void myTest()
    {
        LandingPage landingPage = PageRepository.getPage(LandingPage.class);
        PizzaGeneratorPage generatorPage = PageRepository.getPage(PizzaGeneratorPage.class);

        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.clickGenerateAndArchive();
        Assert.assertTrue(generatorPage.isToastVisibleWithMessage("Created one Pizza."));
        landingPage.clickAppTab(PIZZA_ARCHIVE);
    }

    @AfterMethod
    public void teardown(ITestResult result){
        if (DriverCoordinator.hasDriver()) {
            if (!result.isSuccess()) {
                //TODO: figure out reporting and how to attach screenshots in Allure

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