package pages;

import org.openqa.selenium.By;
import utils.Enums.PizzaAppTab;

import static system.DriverCoordinator.getWebDriver;
import static utils.WebElementUtils.*;

public class LandingPage
{
    By pizzaGeneratorButtonBy = By.xpath("//button[text()='Pizza Generator']");
    By newPizzaButtonBy = By.xpath("//button[text()='New Pizza']");
    By pizzaArchiveButtonBy = By.xpath("//button[text()='Pizza Archive']");

    public void launchPizzaPage(){
        //TODO: Put the URL in a config file?
        //Navigate to main pizza website
        getWebDriver().get("https://main.d3ljwfp72dyhph.amplifyapp.com/");
    }

    /**
     * Clicks the tab at the top of the app to switch between pages
     *
     * @param tab   Enum representing the page
     */
    public void clickAppTab(PizzaAppTab tab){
        switch (tab){
            case NEW_PIZZA -> waitAndClick(newPizzaButtonBy);
            case PIZZA_ARCHIVE -> waitAndClick(pizzaArchiveButtonBy);
            case PIZZA_GENERATOR -> waitAndClick(pizzaGeneratorButtonBy);
        }
    }
}