package pages.pagecomponents;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static system.DriverCoordinator.getWait;
import static utils.WebElementUtils.waitAndClick;
import static utils.WebElementUtils.waitForElementToVanish;

@Slf4j
public class ToastComponent
{
    By closeToastButtonBy = By.cssSelector("[id*='toast'] button[aria-label='close']");
    By toastBy = By.id("success-toast");

    public void closeToast(){
        waitAndClick(closeToastButtonBy);
        waitForElementToVanish(closeToastButtonBy);
    }

    /**
     * Get the message present in a displayed toast
     *
     * @return  The string representation of the message visible in the toast
     */
    public String getToastMessage(){
        try{
            WebElement toast = getWait().until(ExpectedConditions.visibilityOfElementLocated(toastBy));
            return toast.getText();
        } catch (Exception e) {
            log.error("Could not locate a toast!");
            throw e;
        }
    }
}
