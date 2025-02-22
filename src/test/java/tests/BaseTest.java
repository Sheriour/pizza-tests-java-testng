package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LandingPage;
import system.DriverCoordinator;
import utils.FileUtils;
import java.lang.reflect.Method;

import static system.PageRepository.*;


public class BaseTest
{
    @BeforeMethod
    public void setUpTest(Method method) {
        getPage(LandingPage.class).launchPizzaPage();
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
