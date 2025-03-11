package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.PizzaGeneratorPage;
import pages.pagecomponents.PizzaListComponent;
import pages.pagecomponents.ToastComponent;
import utils.Enums;
import utils.Utils;

import static system.PageRepository.*;
import static utils.Enums.PizzaAppTab.*;
import static utils.Utils.waitForSeconds;

public class PizzaGeneratorTests extends BaseTest
{
    @BeforeMethod
    public void setUp() {
        getLandingPage().clickAppTab(PIZZA_GENERATOR);
    }

    @Test
    public void cannotPopulateZeroOrElevenPizzas()
    {
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();

        generatorPage.fillPizzaCount(0);
        Assert.assertEquals(generatorPage.getPizzaCount(), 1);

        generatorPage.fillPizzaCount(11);
        Assert.assertEquals(generatorPage.getPizzaCount(), 10);
    }

    @Test(groups = { "sanity" })
    public void canGenerateOnePizza()
    {
        getPizzaGeneratorPage().clickGenerateAndArchive();
        Assert.assertEquals(getToastComponent().getToastMessage(), "Generated one pizza.");
        getLandingPage().clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(getPizzaListComponent().getNumberOfPizzaItems(), 1);
    }

    @Test
    public void canGenerateTenPizzas()
    {
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();

        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.fillPizzaCount(10);
        generatorPage.clickGenerateAndArchive();
        Assert.assertEquals(getToastComponent().getToastMessage(), "Generated 10 pizzas.");
        landingPage.clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(getPizzaListComponent().getNumberOfPizzaItems(), 10);
    }

    @Test
    public void canGenerateVegetarianPizzas() {
        canGeneratePizzasOfDiet(Enums.DietType.VEGETARIAN);
    }

    @Test
    public void canGenerateVeganPizzas() {
        canGeneratePizzasOfDiet(Enums.DietType.VEGAN);
    }

    private void canGeneratePizzasOfDiet(Enums.DietType dietType){
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();


        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.fillPizzaCount(5);
        Assert.assertEquals(
                generatorPage.selectPizzaDiet( dietType),
                dietType.getValue()
        );
        generatorPage.clickGenerateAndArchive();
        Assert.assertEquals(getToastComponent().getToastMessage(), "Generated 5 pizzas.");
        landingPage.clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(
                getPizzaListComponent().getDietCompliantPizzasCount( dietType),
                5
        );
    }

    @Test
    public void canGenerateAndPreviewAllPizzas() {
        canGenerateAndPreviewPizzasOfDiet(Enums.DietType.ALL, Utils.getRandomNumberBetween(2,10));
    }

    @Test
    public void canGenerateAndPreviewVegetarianPizzas() {
        canGenerateAndPreviewPizzasOfDiet(Enums.DietType.VEGETARIAN, Utils.getRandomNumberBetween(2,10));
    }

    @Test
    public void canGenerateAndPreviewVeganPizzas() {
        canGenerateAndPreviewPizzasOfDiet(Enums.DietType.VEGAN, Utils.getRandomNumberBetween(2,10));
    }

    /**
     * A common routine for generate & preview tests. It will create a number of pizzas of specified
     * diet type and verify that they got created, with appropriate toast message and diet type.
     *
     * @param dietType      Type of pizza diet to create
     * @param pizzaCount    How many pizzas to create
     */
    private void canGenerateAndPreviewPizzasOfDiet(Enums.DietType dietType, int pizzaCount){
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();

        getLandingPage().clickAppTab(PIZZA_GENERATOR);
        generatorPage.fillPizzaCount(pizzaCount);
        Assert.assertEquals(
                generatorPage.selectPizzaDiet( dietType),
                dietType.getValue()
        );
        generatorPage.clickGenerateAndPreview();
        Assert.assertEquals(getToastComponent().getToastMessage(), "Generated "+pizzaCount+" pizzas.");
        Assert.assertEquals(getPizzaListComponent().getDietCompliantPizzasCount( dietType), pizzaCount);
    }

    @Test
    public void canSeePreviewEmptyTextDisplayAndVanish() {
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();

        Assert.assertTrue(
                generatorPage.isNoPizzasTextPresent(),
                "The 'No pizzas for preview' text was not initially present on page!"
        );
        getLandingPage().clickAppTab(PIZZA_GENERATOR);
        generatorPage.fillPizzaCount(1);
        generatorPage.clickGenerateAndPreview();
        Assert.assertTrue(
                generatorPage.hasNoPizzasTextVanished(),
                "The 'No pizzas for preview' text was visible on page after pizzas were generated!"
        );
    }

    @Test
    public void canRemovePizzaFromGeneratorPreview() {
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();
        PizzaListComponent listComponent = getPizzaListComponent();

        getLandingPage().clickAppTab(PIZZA_GENERATOR);
        //Generate a random number of pizzas
        int pizzasToGenerate = Utils.getRandomNumberBetween(2, 10);
        generatorPage.fillPizzaCount(pizzasToGenerate);
        generatorPage.clickGenerateAndPreview();

        //We will pick a random pizza off the list for deletion
        int indexOfDeletedPizza = Utils.getRandomNumberBetween(1, pizzasToGenerate);
        //Keep track of which pizza name is about to be deleted
        String nameOfDeletedPizza = listComponent.getNameOfPizzaAt(indexOfDeletedPizza);
        listComponent.deleteNthPizzaOnList(indexOfDeletedPizza);
        //Verify that pizza with this name is no longer present on the list
        Assert.assertTrue(
                listComponent.pizzaNameVanishedFromList(nameOfDeletedPizza),
                "The pizza "+nameOfDeletedPizza+" was still present on the list after deletion!"
        );

        //Verify that the number of pizzas is now 1 fewer than originally generated
        int pizzaCountAfterDeletion = listComponent.getNumberOfPizzaItems();
        Assert.assertEquals(
                pizzaCountAfterDeletion,
                pizzasToGenerate - 1,
                "The total number of pizzas did not change after deletion!");
    }

    @Test
    public void canAddPizzasFromGeneratorPreviewToArchive() {
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();
        ToastComponent toastComponent = getToastComponent();

        landingPage.clickAppTab(PIZZA_GENERATOR);

        //Generate a random number of pizzas
        int pizzasToGenerate = Utils.getRandomNumberBetween(2, 10);
        generatorPage.fillPizzaCount(pizzasToGenerate);
        generatorPage.clickGenerateAndPreview();
        toastComponent.closeToast();

        //A new toast won't be displayed if the actions take place in quick succession
        waitForSeconds(2);

        //Archive pizzas and assert toast
        generatorPage.clickArchiveAllButton();
        Assert.assertEquals(
                toastComponent.getToastMessage(),
                "Added "+pizzasToGenerate+" pizzas to Archive."
        );

        landingPage.clickAppTab(PIZZA_ARCHIVE);

        Assert.assertEquals(
                getPizzaListComponent().getNumberOfPizzaItems(),
                pizzasToGenerate,
                "The number of generated pizzas did not match the number of pizzas in archive!"
        );
    }

    @Test
    public void regeneratingPreviewResetsPizzas() {
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();
        PizzaListComponent listComponent = getPizzaListComponent();

        getLandingPage().clickAppTab(PIZZA_GENERATOR);

        //Initially let's generate 4 pizzas
        int initialPizzaCount = 4;
        generatorPage.fillPizzaCount(initialPizzaCount);
        generatorPage.clickGenerateAndPreview();
        String firstNameSet = listComponent.getAllPizzaNamesAsString();

        //Now let's generate 4 pizzas again. These should replace the first set of 4
        generatorPage.fillPizzaCount(initialPizzaCount);
        generatorPage.clickGenerateAndPreview();
        String secondNameSet = listComponent.getAllPizzaNamesAsString();

        //Assert that new set of pizza names is different from the first
        Assert.assertNotEquals(firstNameSet, secondNameSet);
        //Assert that there are still 4 pizzas in preview
        Assert.assertEquals(
                getPizzaListComponent().getNumberOfPizzaItems(),
                initialPizzaCount,
                "The number of generated pizzas did not match the number of requested " + initialPizzaCount
        );

        //Now let's generate 6 pizzas. These should replace the second set of 4
        int regeneratedPizzaCount = 6;
        generatorPage.fillPizzaCount(regeneratedPizzaCount);
        generatorPage.clickGenerateAndPreview();

        //Assert that there are now 6 pizzas in preview
        Assert.assertEquals(
                listComponent.getNumberOfPizzaItems(),
                regeneratedPizzaCount,
                "The number of re-generated pizzas did not match the requested " + regeneratedPizzaCount);
    }
}