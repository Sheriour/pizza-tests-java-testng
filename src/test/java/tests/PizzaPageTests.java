package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.PizzaArchivePage;
import pages.PizzaGeneratorPage;

import static system.PageRepository.*;
import static utils.Enums.PizzaAppTab.*;

public class PizzaPageTests extends BaseTest
{
    @Test
    public void cannotPopulateZeroOrElevenPizzas()
    {
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();

        landingPage.clickAppTab(PIZZA_GENERATOR);

        generatorPage.fillPizzaCount(0);
        Assert.assertEquals(generatorPage.getPizzaCount(), 1);

        generatorPage.fillPizzaCount(11);
        Assert.assertEquals(generatorPage.getPizzaCount(), 10);
    }

    @Test
    public void canGenerateOnePizza()
    {
        LandingPage landingPage = getLandingPage();
        PizzaGeneratorPage generatorPage = getPizzaGeneratorPage();
        PizzaArchivePage archivePage = getPizzaArchivePage();

        landingPage.clickAppTab(PIZZA_GENERATOR);
        generatorPage.clickGenerateAndArchive();
        Assert.assertEquals(generatorPage.getToastMessage(), "Generated one pizza.");
        landingPage.clickAppTab(PIZZA_ARCHIVE);
        Assert.assertEquals(archivePage.getNumberOfPizzaItems(), 1);
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
        Assert.assertEquals(archivePage.getNumberOfPizzaItems(), 10);
    }
}