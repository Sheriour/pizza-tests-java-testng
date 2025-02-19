package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static system.DriverCoordinator.getWait;
import static utils.WebElementUtils.waitAndClick;

public class PizzaArchivePage
{
    By listedPizzaItemBy = By.cssSelector("#pizza-list .list-group-item");

    public Integer getNumberOfPizzaItems(){
        try{
            List<WebElement> pizzaListItems =
                    getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listedPizzaItemBy));
            return pizzaListItems.size();
        } catch (Exception e) {
            //If the wait times out, there are just no items
            return 0;
        }
    }
}