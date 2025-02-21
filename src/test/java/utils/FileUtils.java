package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;

public class FileUtils
{
    public static void attachAllureScreenshot(WebDriver driver){
        try  {
            FileInputStream screenshotStream = new FileInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
            //Allure.addAttachment("", "image/png", screenshotStream, "png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
