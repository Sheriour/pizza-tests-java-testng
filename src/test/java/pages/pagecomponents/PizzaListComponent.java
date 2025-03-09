package pages.pagecomponents;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Enums;

import java.util.List;
import java.util.Objects;

import static system.DriverCoordinator.getWait;
import static utils.LocatorUtils.ByDataTestId;
import static utils.WebElementUtils.waitForElementToVanish;

@Slf4j
public class PizzaListComponent {

    By listedPizzaItemBy = ByDataTestId("pizza-list-item");
    By dietBadgeBy = ByDataTestId("veg-badge");
    By deletePizzaButtonBy = ByDataTestId("delete-pizza-button");
    By pizzaNamesBy = By.cssSelector("[data-test-id='pizza-name']");

    private By getPizzaNameBy(String pizzaName){
        return By.xpath("//*[@data-test-id='pizza-name' and text()='"+pizzaName+"']");
    }

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

    /**
     * Will look up the nth item on the pizza list and return the name of that pizza.
     * If the list contains fewer pizzas than requested index, this will return null.
     *
     * @param index Location of the pizza on the list
     * @return      Pizza name or null
     */
    public String getNameOfPizzaAt(int index){
        try {
            List<WebElement> pizzaNameElements =
                    getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pizzaNamesBy));
            if (pizzaNameElements.size() < index) {
                log.error("Requested a name for " + index + " pizza but list contained only " + pizzaNameElements.size());
                return null;
            }
            return pizzaNameElements.get(index-1).getText();
        }
        catch (Exception e) {
            log.error("Encountered an exception when trying to locate a pizza name at index " + index + ".");
            throw e;
        }
    }

    /**
     * Waits until given pizza name vanishes from the list
     *
     * @param pizzaName Name of the pizza name expected to vanish
     * @return          True if the pizza name vanished, false otherwise
     */
    public boolean pizzaNameVanishedFromList(String pizzaName){
        return waitForElementToVanish(getPizzaNameBy(pizzaName));
    }

    /**
     * Click the delete button for a pizza at a given position on the list
     *
     * @param index Position of the pizza on the list
     */
    public void deleteNthPizzaOnList(int index){
        try {
            List<WebElement> pizzaDeleteElements =
                    getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(deletePizzaButtonBy));
            if (pizzaDeleteElements.size() < index) {
                log.error("Requested to delete " + index + " pizza but list contained only " + pizzaDeleteElements.size());
            }
            //With the log line above, we can let this hit indexOutOfBounds to fail the test in case this happens
            pizzaDeleteElements.get(index-1).click();
        }
        catch (Exception e) {
            log.error("Encountered an exception when trying to delete a pizza at index " + index + ".");
            throw e;
        }
    }
}
