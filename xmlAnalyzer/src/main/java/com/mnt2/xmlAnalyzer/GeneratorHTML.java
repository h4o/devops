package com.mnt2.xmlAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by cazala on 08/03/16.
 */
public class GeneratorHTML implements IGenerator{

    private List<TestReport> initialReport;
    private String path;
    private String mutant_output;
    private String mutant_table;
    private int nbKilled;
    private int nbSurvived;
    public GeneratorHTML (List<TestReport> init, String path) {
        initialReport = init;
        this.path = path;
        mutant_output = "<table class=\"table table-striped\"><thead><tr><th>Statut</th><th># Mutant</th><th># Test Succeed</th><th># Test Failed</th></tr></thead><tbody>";

    }

    @Override
    public void generateMutant(List<TestReport> testList) {
        boolean killed = false;
        int nbSucc = 0, nbFailed = 0;
        for(int i = 0; i < testList.size() ; ++i) {
            if (testList.get(i).getStatus().equals(TestStatusEnum.FAILED)) {
                nbFailed++;
                if (initialReport.get(i).getStatus().equals(TestStatusEnum.SUCCEED)) {
                    if(!killed) nbKilled++;
                    killed = true;
                }
            } else if (testList.get(i).getStatus().equals(TestStatusEnum.SUCCEED)) {
                nbSucc++;
            }
        }
        mutant_output += "<tr class=\"";

        if (killed) mutant_output+="success";
        else mutant_output+="warning";

        mutant_output +="\"><td>";
        if (killed) mutant_output += nbKilled + nbSurvived;
        else mutant_output += nbKilled + ++nbSurvived;
        mutant_output+="</td><td>";
        if (killed) mutant_output += "Killed";
        else mutant_output += "Survived";
        mutant_output+="</td><td>"+nbSucc+"</td><td>"+nbFailed+"</td></tr>";
    }

    private String generateMutantTable(List<TestReport> testList) {
        String mutant_output_table = "<table class=\"table table-striped\"><thead><tr><th>Statut</th><th>#Test</th><th>Classe de Test</th><th>TestName</th></tr></thead><tbody>";

        for (int i = 0; i < testList.size() ; ++i) {
            TestReport tmp = testList.get(i);
            mutant_output_table += "<tr class=\""+ tmp.getStatus() +"\">" +
                    "<td class=\""+ tmp.getStatus().toString() + ">"+tmp.getStatus()+"</td>"+
                    "<td>"+tmp.getClassName()+"</td>"+
                    "<td>"+tmp.getTestName()+"</td>"+
                    "</tr>";
        }
        return mutant_output_table;
    }

    private String readFile(String path ){
        String strFile = new String();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\testing.txt")))
        {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                strFile+=sCurrentLine;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strFile;
    }


}
