package com.mnt2.xmlAnalyzer;

/**
 * Created by cazala on 04/03/16.
 */
public enum TestStatusEnum {
    FAILED("danger"),
    SKIPPED("warning"),
    SUCCEED("success");

    private String value;

    TestStatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
