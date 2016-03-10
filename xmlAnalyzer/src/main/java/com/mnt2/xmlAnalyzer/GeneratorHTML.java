package com.mnt2.xmlAnalyzer;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    private String modal_output;
    private HashMap<String, Integer> tests;

    public GeneratorHTML (List<TestReport> init, String path) {
        modal_output = "";
        mutant_output = "";
        initialReport = init;
        this.path = path;tests = new HashMap<>();
        for(TestReport t : initialReport) {
            String testName = t.getClassName()+ "." +t.getTestName();
            tests.put(testName, 0);
        }
    }

    @Override
    public void generateMutant(List<TestReport> testList) {
        boolean killed = false;
        int nbSucc = 0, nbFailed = 0;
        for(int i = 0 ; i < testList.size() ; ++i) {
            if (testList.get(i).getStatus().equals(TestStatusEnum.FAILED)) {
                nbFailed++;
                if (initialReport.get(i).getStatus().equals(TestStatusEnum.SUCCEED)) {
                    if(!killed) nbKilled++;
                    killed = true;
                    String testName = testList.get(i).getClassName()+ "." +testList.get(i).getTestName();
                    Integer count = tests.get(testName);
                    tests.put(testName, count+1);
                }
            } else if (testList.get(i).getStatus().equals(TestStatusEnum.SUCCEED)) {
                nbSucc++;
            }
        }
        mutant_output += "<tr class=\"";
        int numMut = nbKilled;

        if (killed) numMut += nbSurvived;
        else numMut += ++nbSurvived;

        if (killed) mutant_output+="success\" ";
        else mutant_output+="danger\" ";

        mutant_output += "data-toggle=\"modal\" data-target=\"#modal-"+numMut+"\"";
        mutant_output +="><td>";
        mutant_output += numMut;
        mutant_output+="</td><td class=\"";
        if (killed) mutant_output+="success_txt";
        else mutant_output+="echec_txt";
        mutant_output+="\">";
        if (killed) mutant_output += "Killed";
        else mutant_output += "Survived";
        mutant_output+="</td><td>"+nbSucc+"</td><td>"+nbFailed+"</td></tr>";
        modal_output += "<div id=\"modal-"+numMut+"\" class=\"modal fade\" role=\"dialog\">\n" +
                "  <div class=\"modal-dialog\">\n" +
                "\n" +
                "    <!-- Modal content-->\n" +
                "    <div class=\"modal-content\">";
        modal_output += "<div class=\"modal-header\">\n" +
                "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
                "        <h4 class=\"modal-title\">[INFO] Mutant - "+numMut+"</h4>\n" +
                "      </div>"+
                "<div class=\"modal-body\">";
        modal_output += generateMutantTable(testList);
        modal_output += "</div>\n" +
                "      <div class=\"modal-footer\">\n" +
                "        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "\n" +
                "  </div>\n" +
                "</div>";
    }

    private String generateTestChart() {
        Set cles = tests.keySet();
        String dataTests = new String();
        Iterator it = cles.iterator();
        boolean first = true;
        while (it.hasNext()){
            Object cle = it.next(); // tu peux typer plus finement ici
            Integer valeur = tests.get(cle); // tu peux typer plus finement ici
            if (!first) dataTests += ",";
            else first = false;
            dataTests += "{\nname:'"+cle.toString()+"',\ny: "+((valeur.doubleValue()/((double)(nbKilled+nbSurvived))) * 100)+"\n}\n";
        }
        return dataTests;
    }
    private String generateMutantTable(List<TestReport> testList) {
        String mutant_output_table = "<table class=\"table table-striped\"><thead><tr><th>Statut</th><th>#Test</th><th>Classe de Test</th><th>TestName</th></tr></thead><tbody>";

        for (int i = 0; i < testList.size() ; ++i) {
            TestReport tmp = testList.get(i);
            mutant_output_table += "<tr class=\""+ tmp.getStatus() +"\">" +
                    "<td class=\""+ tmp.getStatus().toString() + "\">"+tmp.getStatus().name()+"</td>"+
                    "<td>"+i+"</td>"+
                    "<td>"+tmp.getClassName()+"</td>"+
                    "<td>"+tmp.getTestName()+"</td>"+
                    "</tr>";
        }
        mutant_output_table+="</tbody></table>";
        return mutant_output_table;
    }

    public void generateReport() {
        File htmlTemplate = new File(path+"/index.html");
        File newHtml = new File(path+"/newIndex.html");
        String htmlString = new String();
        try {
            htmlString = FileUtils.readFileToString(htmlTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlString = htmlString.replace("$TABLEBODY$", mutant_output);
        htmlString = htmlString.replace("$NBMUTANTSKILLED$", ""+nbKilled);
        htmlString = htmlString.replace("$NBMUTANTSSURVIVED$", ""+nbSurvived);
        htmlString = htmlString.replace("$DATATESTCHART$", generateTestChart());
        htmlString = htmlString.replace("$MODALCONTENT$", modal_output);

        try {
            FileUtils.writeStringToFile(newHtml, htmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
