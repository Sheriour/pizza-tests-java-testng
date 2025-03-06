package pages;

import pages.pagecomponents.PizzaListComponent;

public class PizzaArchivePage
{
    PizzaListComponent pizzaListComponent;

    public PizzaArchivePage(){
        pizzaListComponent = new PizzaListComponent();
    }

    public PizzaListComponent getPizzaListComponent() {
        return pizzaListComponent;
    }
}