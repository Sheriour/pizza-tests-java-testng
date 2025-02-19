package system;

import lombok.extern.slf4j.Slf4j;
import pages.LandingPage;
import pages.PizzaArchivePage;
import pages.PizzaGeneratorPage;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class PageRepository {

    private static ThreadLocal<ArrayList<Object>> pagesThread = new ThreadLocal<>();

    /**
     * Get pages list for current thread
     *
     * @return  List of pages for current thread
     */
    private static ArrayList<Object> getPages(){
        ArrayList<Object> pages = pagesThread.get();
        if (pages == null) {
            pages = new ArrayList<>();
            pagesThread.set(pages);
        }
        return pages;
    }

    /**
     * Removes all page references
     */
    public static void deleteAllPages(){
        pagesThread.remove();
        pagesThread.set(new ArrayList<>());
    }

    /**
     * Create or retrieve (if one already exists) a page object from the repository.
     * Only one instance of a given page is allowed.
     *
     * @param pageClass Class of the page
     * @return          Page instance
     * @param <T>       Class of the page
     */
    public static <T> T getPage(Class<T> pageClass){
        ArrayList<Object> pages = getPages();

        try {
            //Find the page object of specific class
            Optional<Object> pageOptional = pages
                    .stream()
                    .filter(x -> x.getClass() == pageClass)
                    .findFirst();
            if (pageOptional.isEmpty()) {
                //If no page object exists yet, create and return it
                T page = pageClass.getConstructor().newInstance();
                pages.add(page);
                return page;
            }
            else {
                //If page object exists, just return it
                return (T)pageOptional.get();
            }
        }
        catch (Exception e){
            System.out.println("Exception encountered when creating a Page Object. If running tests locally without " +
                    "a running Selenium Grid, switch to runmode=local in jiratest.properties file");
            System.out.println(e.getMessage());
            throw new AssertionError("Could not create page " + pageClass.getName());
        }
    }

    public static LandingPage getLandingPage(){
        return getPage(LandingPage.class);
    }

    public static PizzaArchivePage getPizzaArchivePage(){
        return getPage(PizzaArchivePage.class);
    }

    public static PizzaGeneratorPage getPizzaGeneratorPage(){
        return getPage(PizzaGeneratorPage.class);
    }
}