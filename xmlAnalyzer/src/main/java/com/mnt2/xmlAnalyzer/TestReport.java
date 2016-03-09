package com.mnt2.xmlAnalyzer;

/**
 * Created by cazala on 04/03/16.
 */
public class TestReport {
    private TestStatusEnum status;
    private String className;
    private String testName;
    private String detail;

    public TestReport(String className, String testName) {
        new TestReport(TestStatusEnum.SUCCEED, className, testName, "");
    }

    public TestReport(TestStatusEnum status, String className, String testName, String detail) {
        this.testName = testName;
        this.className = className;
        this.status = status;
        this.detail = detail;
    }

    public TestStatusEnum getStatus() {
        return status;
    }

    public String getClassName() {
        return className;
    }

    public String getTestName() {
        return testName;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return status + "," + className + "," + testName + "," + detail;
    }
}
