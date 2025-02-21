package system;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporting
{
    static ExtentReports extentReport;

    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();


    public static void initReport()
    {
        // Initialize ExtentReports
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extentReport-reports/extentReport-report.html");
        extentReport = new ExtentReports();
        extentReport.attachReporter(reporter);
    }

    public static void registerTest(String testName){
        extentTest.set(extentReport.createTest(testName));
    }

    public static void failTest(String testName, String failMessage){
        extentTest.get().fail(failMessage);
    }

    public static void finaliseReport(){
        extentReport.flush();
    }
}
