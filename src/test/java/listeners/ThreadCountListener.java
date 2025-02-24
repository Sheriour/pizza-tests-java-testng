package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;
import system.PropertyManager;

public class ThreadCountListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        int threadCount = PropertyManager.GetInstance().getPropertyInt("threadcount");
        XmlSuite xmlSuite = suite.getXmlSuite();
        xmlSuite.setThreadCount(threadCount);
        if (threadCount > 1)
            xmlSuite.setParallel(XmlSuite.ParallelMode.METHODS);
        System.out.print("you doing anytyhing, bro?");
    }
}
