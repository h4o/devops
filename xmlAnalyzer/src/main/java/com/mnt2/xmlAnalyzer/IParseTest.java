package com.mnt2.xmlAnalyzer;

import java.util.List;

/**
 * Created by cazala on 04/03/16.
 */
public interface IParseTest {
    List<TestReport> parse(String filePath);
    List<TestReport> parseFolderXML(String folderPath);
}
