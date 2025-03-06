package utils;

import org.openqa.selenium.By;

public class LocatorUtils
{
    public static By ByDataTestId(String dataTestId) {
        return By.cssSelector("[data-test-id='"+dataTestId+"']");
    }
}
