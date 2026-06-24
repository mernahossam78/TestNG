package CustomListeners;

import org.testng.*;

public class TestNGListeners implements IInvokedMethodListener, ITestListener, IExecutionListener, IRetryAnalyzer {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        /*the below condition is to check if the method is a test method
        if it is a test method it will get the method and get its name and then says it started
         */
        if (method.isTestMethod()) {
            System.out.println(method.getTestMethod().getMethodName() + "Started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            System.out.println(method.getTestMethod().getMethodName() + "Finished");
        }
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + "Passed");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + "Failed");
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + "Skipped");
    }

    public void onExecutionStart() {
        System.out.println("Execution started");
    }

    public void onExecutionFinish() {
        System.out.println("Execution finished");
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        return false;
    }
}
