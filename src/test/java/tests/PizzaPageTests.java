package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.PizzaArchivePage;
import pages.PizzaGeneratorPage;
import system.DriverCoordinator;
import utils.FileUtils;

import java.lang.reflect.Method;

import static system.PageRepository.*;

import static utils.Enums.PizzaAppTab.*;

public class PizzaPageTests
{
    @BeforeMethod
    public void setUpTest(Method method) {
        getPage(LandingPage.class).launchPizzaPage();
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
            DriverCoordinator.quitWebDriver();
        }
        deleteAllPages();
    }

    @AfterMethod
    public void screenshotCapture(ITestResult result){
        if (!result.isSuccess()) {
            FileUtils.attachAllureScreenshot(DriverCoordinator.getWebDriver());
        }
    }
}