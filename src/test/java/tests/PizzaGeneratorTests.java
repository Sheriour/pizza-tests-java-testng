package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.PizzaArchivePage;
import pages.PizzaGeneratorPage;
import utils.Enums;

import static system.PageRepository.*;
import static utils.Enums.PizzaAppTab.*;

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
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();
        PizzaArchivePage archivePage = getPizzaArchivePage();

        generatorPage.clickGenerateAndArchive();
        Assert.assertEquals(generatorPage.getToastMessage(), "Generated one pizza.");
        landingPage.clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(archivePage.getPizzaListComponent().getNumberOfPizzaItems(), 1);
    }

    @Test
    public void canGenerateTenPizzas()
    {
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();
        PizzaArchivePage archivePage = getPizzaArchivePage();

        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.fillPizzaCount(10);
        generatorPage.clickGenerateAndArchive();
        Assert.assertEquals(generatorPage.getToastMessage(), "Generated 10 pizzas.");
        landingPage.clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(archivePage.getPizzaListComponent().getNumberOfPizzaItems(), 10);
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
        PizzaArchivePage archivePage = getPizzaArchivePage();

        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.fillPizzaCount(5);
        Assert.assertEquals(
                generatorPage.selectPizzaDiet( dietType),
                dietType.getValue()
        );
        generatorPage.clickGenerateAndArchive();
        Assert.assertEquals(generatorPage.getToastMessage(), "Generated 5 pizzas.");
        landingPage.clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(archivePage.getPizzaListComponent().getDietCompliantPizzasCount( dietType), 5);
    }

    @Test
    public void canGenerateAndPreviewAllPizzas() {
        canGenerateAndPreviewPizzasOfDiet(Enums.DietType.ALL);
    }

    @Test
    public void canGenerateAndPreviewVegetarianPizzas() {
        canGenerateAndPreviewPizzasOfDiet(Enums.DietType.VEGETARIAN);
    }

    @Test
    public void canGenerateAndPreviewVeganPizzas() {
        canGenerateAndPreviewPizzasOfDiet(Enums.DietType.VEGAN);
    }

    private void canGenerateAndPreviewPizzasOfDiet(Enums.DietType dietType){
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();

        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.fillPizzaCount(5);
        Assert.assertEquals(
                generatorPage.selectPizzaDiet( dietType),
                dietType.getValue()
        );
        generatorPage.clickGenerateAndPreview();
        Assert.assertEquals(generatorPage.getToastMessage(), "Generated 5 pizzas.");
        Assert.assertEquals(generatorPage.getPizzaListComponent().getDietCompliantPizzasCount( dietType), 5);
    }
}