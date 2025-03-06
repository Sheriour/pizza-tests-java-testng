package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Enums;

import java.util.List;

import static system.DriverCoordinator.getWait;
import static utils.LocatorUtils.ByDataTestId;

public class PizzaArchivePage
{
    By listedPizzaItemBy = ByDataTestId("pizza-list-item");
    By dietBadgeBy = ByDataTestId("veg-badge");

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

    /**
     * Checks how many pizzas "comply" with a given diet.
     * For ANY pizza type, all would comply.
     * For VEGETARIAN type, both VEGETARIAN and VEGAN pizzas comply.
     * For VEGAN type, only VEGAN comply.
     *
     * @param dietType  Type of diet as enum
     * @return          How many pizzas comply with diet
     */
    public int getDietCompliantPizzasCount(Enums.DietType dietType){
        try{
            if (dietType == Enums.DietType.ANY) {
                List<WebElement> pizzaListItems =
                        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listedPizzaItemBy));
                return pizzaListItems.size();
            } else {
                List<WebElement> pizzaBadgeItems =
                        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dietBadgeBy));
                if (dietType == Enums.DietType.VEGETARIAN) return pizzaBadgeItems.size();
                //else

            }
            //WIP

            return 0;
        } catch (Exception e) {
            //If the wait times out, there are just no items
            return 0;
        }
    }
}