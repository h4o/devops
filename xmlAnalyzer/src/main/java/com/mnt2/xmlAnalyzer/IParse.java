package com.mnt2.xmlAnalyzer;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cazala on 04/03/16.
 */
public interface IParse {
    List<TestReport> parse(String filePath);
    HashMap<Integer,List<TestReport>> parse(List<String> filePathList);
}
