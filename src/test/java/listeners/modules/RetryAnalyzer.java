package listeners.modules;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import system.PropertyManager;

public class RetryAnalyzer implements IRetryAnalyzer
{
    static boolean retry = PropertyManager.GetInstance().getPropertyBool("retryFailedTests");
    static int retryLimit = PropertyManager.GetInstance().getPropertyInt("retryTestCount");

    int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        System.out.println("retry " + retry);
        System.out.println("retryLimit " + retryLimit);

        //If config disabled retries, quit...
        if (!retry)
            return false;

        //... otherwise attempt the configured number of retries
        if(retryCount < retryLimit)
        {
            retryCount++;
            return true;
        }
        return false;
    }
}