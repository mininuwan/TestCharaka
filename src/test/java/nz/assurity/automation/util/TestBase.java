package nz.assurity.automation.util;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.Date;

public class TestBase {
    private long testSuiteDuration = 0;
    private long testSuiteStartTime = 0;

    @BeforeClass
    public void init()
    {
        testSuiteDuration = 0;
        testSuiteStartTime = new Date().getTime();
    }

    @AfterMethod(alwaysRun = true)
    public void updateMethod(ITestContext iTestContext) {
        testSuiteDuration = new Date().getTime() - testSuiteStartTime;
    }
}
