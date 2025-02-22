package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static system.DriverCoordinator.getWait;
import static utils.WebElementUtils.*;

@Slf4j
public class PizzaGeneratorPage
{
    By generateArchiveButtonBy = By.xpath("//button[text()='Generate & Archive']");
    By toastBy = By.cssSelector("div[role='alert']");
    By pizzaCountBy = By.id("pizzaCountInput");

    /**
     * Populates the pizza count input to define how many pizzas will be generated
     *
     * @param count Number of pizzas to generate
     */
    public void fillPizzaCount(int count){
        waitAndFillField(
                pizzaCountBy,
                String.valueOf(count),
                "Could not populate pizza count in Generator."
        );
    }

    /**
     * Retrieves the value currently present in pizza count input
     *
     * @return  Value currently present in pizza count input
     */
    public int getPizzaCount(){
        try {
            WebElement pizzaCountInput = getWait().until(ExpectedConditions.visibilityOfElementLocated(pizzaCountBy));
            return Integer.parseInt(pizzaCountInput.getDomProperty("value"));
        }
         catch (Exception e) {
            log.error("Could not retrieve pizza count!");
            throw e;
         }
    }

    /**
     * Clicks the "Generate & Archive" button which generates pizzas and inserts them into Archive page
     */
    public void clickGenerateAndArchive(){
        waitAndClick(generateArchiveButtonBy);
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