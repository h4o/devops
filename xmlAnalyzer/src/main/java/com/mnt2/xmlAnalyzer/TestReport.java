package com.mnt2.xmlAnalyzer;

/**
 * Created by cazala on 04/03/16.
 */
public class TestReport {
    TestStatusEnum status;
    String className;
    String testName;
    String detail;

    public TestReport(String className, String testName) {
        TestReport(TestStatusEnum.SUCCEED, className, testName, "");
    }

    public TestReport(TestStatusEnum status, String className, String testName, String detail) {
        this.testName = testName;
        this.className = className;
        this.status = status;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return status + "," + className + "," + testName + "," + detail;
    }
}
