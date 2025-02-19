package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Enums.PizzaAppTab;

import static system.DriverCoordinator.getWait;
import static system.DriverCoordinator.getWebDriver;
import static utils.WebElementUtils.*;

public class PizzaGeneratorPage
{
    By generateArchiveButton = By.xpath("//button[text()='Generate & Archive']");

    private By getXpathForAlertWithMessage(String message){
        return By.xpath("//div[@role='alert' and text()='"+message+"']");
    }

    public void clickGenerateAndArchive(){
        waitAndClick(generateArchiveButton);
    }

    public boolean isToastVisibleWithMessage(String message){
        try{
            getWait(2)
                    .until(ExpectedConditions
                            .visibilityOfElementLocated(getXpathForAlertWithMessage(message)));
            return true;
        } catch (Exception e) {
            System.out.println("Could not locate a toast with message " + message);
            return false;
        }
    }
}