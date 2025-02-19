package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


import static system.DriverCoordinator.getWait;
import static utils.WebElementUtils.*;

public class PizzaGeneratorPage
{
    By generateArchiveButtonBy = By.xpath("//button[text()='Generate & Archive']");
    By toastBy = By.cssSelector("div[role='alert']");

    public void clickGenerateAndArchive(){
        waitAndClick(generateArchiveButtonBy);
    }

    public String getToastMessage(){
        try{
            WebElement toast = getWait().until(ExpectedConditions.visibilityOfElementLocated(toastBy));
            return toast.getText();
        } catch (Exception e) {
            System.out.println("Could not locate a toast ");
            return "";
        }
    }
}