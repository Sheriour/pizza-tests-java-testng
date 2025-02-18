package system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverCoordinator
{
    public static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static String remote_url_chrome = "http://localhost:4444/wd/hub";

    /**
     * Checks if a webdriver instance already exists
     *
     * @return True if a webdriver instance exists, false otherwise
     */
    public static boolean hasDriver(){
        return webDriver.get() != null;
    }

    /**
     * Returns the webdriver instance. Will lazy-load if necessary.
     *
     * @return  Webdriver instance
     */
    public static WebDriver getWebDriver(){
        WebDriver driver = webDriver.get();
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            //options.addArguments("--headless");

            String runmode = PropertyManager.GetInstance().getProperty("runmode");
            try {
                if (runmode.equalsIgnoreCase("local")){
                    driver = new ChromeDriver(options);
                } else if  (runmode.equalsIgnoreCase("remote")){
                    driver = new RemoteWebDriver(new URL(remote_url_chrome), options);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            webDriver.set(driver);
            driver.manage().window().maximize();
        }
        return driver;
    }

    /**
     * Quits the webdriver instance and cleans up the reference
     */
    public static void quitWebDriver(){
        getWebDriver().quit();
        webDriver.remove();
    }

    /**
     * Constructs a default 10-second webdriver explicit wait
     *
     * @return A 10-decond webdriver explicit wait
     */
    public static WebDriverWait getWait(){
        return new WebDriverWait(getWebDriver(), Duration.ofSeconds(10));
    }


    /**
     * Constructs a default webdriver explicit wait with custom wait time
     *
     * @return A webdriver explicit wait with custom wait time
     */
    public static WebDriverWait getWait(int seconds){
        return new WebDriverWait(getWebDriver(), Duration.ofSeconds(seconds));
    }
}
