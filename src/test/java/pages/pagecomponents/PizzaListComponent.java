package pages.pagecomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Enums;

import java.util.List;
import java.util.Objects;

import static system.DriverCoordinator.getWait;
import static utils.LocatorUtils.ByDataTestId;

public class PizzaListComponent {

    By listedPizzaItemBy = ByDataTestId("pizza-list-item");
    By dietBadgeBy = ByDataTestId("veg-badge");

    /**
     * Gets the total number of pizza items on the list
     *
     * @return number of pizza items on the list
     */
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
            if (dietType == Enums.DietType.ALL) {
                //For "All", just count all the pizza items
                List<WebElement> pizzaListItems =
                        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listedPizzaItemBy));
                return pizzaListItems.size();
            } else {
                //Retrieve all the V/Ve badges
                List<WebElement> pizzaBadgeItems =
                        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dietBadgeBy));
                if (dietType == Enums.DietType.VEGETARIAN) {
                    //If "Vegetarian", return the total number of V/Ve badges
                    return pizzaBadgeItems.size();
                }
                else if (dietType == Enums.DietType.VEGAN) {
                    //If "Vegan", return the number of Ve badges
                    return (int)pizzaBadgeItems.stream().filter(x -> Objects.equals(x.getText(), "Ve")).count();
                }
                else return 0; //Default to 0 if sent enum value is not recognised
            }
        } catch (Exception e) {
            //If any wait times out, there are just no items
            return 0;
        }
    }
}
