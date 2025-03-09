package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static system.DriverCoordinator.getWait;

@Slf4j
public class WebElementUtils {

    /**
     * Waits using default wait and clicks the element when it's available.
     *
     * @param elementBy     By of the element to be clicked
     * @param failMessage   Message to output in case of failure
     */
    public static void waitAndClick(By elementBy, String failMessage){
        try {
            waitAndClick(elementBy);
        }
        catch (Exception e){
            log.error(failMessage);
            throw e;
        }
    }

    /**
     * Waits using default wait and clicks the element when it's available
     *
     * @param element       WebElement to be clicked
     * @param failMessage   Message to output in case of failure
     */
    public static void waitAndClick(WebElement element, String failMessage){
        try {
            waitAndClick(element);
        }
        catch (Exception e){
            log.error(failMessage);
            throw e;
        }
    }

    /**
     * Waits using default wait and clicks the element when it's available.
     *
     * @param elementBy     By of the element to be clicked
     */
    public static void waitAndClick(By elementBy){
        getWait()
                .until(ExpectedConditions.elementToBeClickable(elementBy))
                .click();
    }

    /**
     * Waits using default wait and clicks the element when it's available
     *
     * @param element       WebElement to be clicked
     */
    public static void waitAndClick(WebElement element){
        getWait()
                .until(ExpectedConditions.elementToBeClickable(element))
                .click();
    }

    /**
     * Waits using default wait and clicks the element when it's available
     *
     * @param elementBy     By of the element to be clicked
     * @param text          Text to input in the field
     * @param failMessage   Message to output in case of failure
     */
    public static void waitAndFillField(By elementBy, String text, String failMessage){
        try {
            WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(elementBy));
            element.clear();
            element.sendKeys(text);
        }
        catch (Exception e){
            log.error(failMessage);
            throw e;
        }
    }

    /**
     * Waits using default wait until element vanishes
     *
     * @param elementBy By of the element expected to vanish
     * @return          True if the element vanished, false otherwise
     */
    public static boolean waitForElementToVanish(By elementBy){
        try {
            getWait().until(ExpectedConditions.invisibilityOfElementLocated(elementBy));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits using default wait until element appears
     *
     * @param elementBy By of the element expected to appear
     * @return          True if the element appeared, false otherwise
     */
    public static boolean waitForElementToAppear(By elementBy){
        try {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(elementBy));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}