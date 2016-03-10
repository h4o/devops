package com.mnt2.xmlAnalyzer;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    private int nbMortNe;
    private String modal_output;
    private HashMap<String, Integer> tests;

    String dataTests = new String();
    String dataDrilldown = new String();

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

    /*@Override
    public void generateMutant(HashMap<String,List<TestReport>> mutant) {
        boolean killed = false;
        int nbSucc = 0, nbFailed = 0;
        for(Map.Entry<String, List<TestReport>> entry : mutant.entrySet()) {
            String cle = entry.getKey(); // ClassName
            List<TestReport> valeur = entry.getValue(); // list des teste de la classe
            for(int i = 0 ; i < valeur.size() ; ++i) {
                if (valeur.get(i).getStatus().equals(TestStatusEnum.FAILED)) {
                    nbFailed++;
                    if (initialReport.get(i).getStatus().equals(TestStatusEnum.SUCCEED)) {
                        if(!killed) nbKilled++;
                        killed = true;
                        String testName = valeur.get(i).getClassName()+ "." +valeur.get(i).getTestName();
                        Integer count = tests.get(testName);
                        tests.put(testName, count+1);
                    }
                } else if (valeur.get(i).getStatus().equals(TestStatusEnum.SUCCEED)) {
                    nbSucc++;
                }
            }
        }
    }*/
    @Override
    public void generateMutant(List<TestReport> testList) {
        if (testList.size() != 0) {
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
            int numMut = nbKilled + nbMortNe;

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

        } else {
            generateMortNes();
        }

        }
    private void generateMortNes() {
        int numMut = nbSurvived + nbKilled + ++nbMortNe;
        mutant_output += "<tr class=\"warning\""+
            "><td>"+numMut;
        mutant_output += "</td><td class=\"warning_txt\">Stillborn</td>";
        mutant_output+="</td><td>0</td><td>0</td></tr>";

    }
    private String getName(String filename,int type) {
        String[] newP = filename.split("\\.");
        return newP[newP.length - 1 - type + 1];
    }
    private void generateTestChart() {
        boolean first = true;
        HashMap<String, HashMap<String, Integer>> data = new HashMap<>();
        for(Map.Entry<String, Integer> entry : tests.entrySet()) {
            String cle = entry.getKey();
            System.out.println(cle);
            Integer valeur = entry.getValue();
            if (!data.containsKey(getName(cle, 2))) {
                data.put(getName(cle, 2), new HashMap<>());
            }
            data.get(getName(cle,2)).put(getName(cle,1), valeur);
        }

        for(Map.Entry<String,HashMap<String,Integer>> entry : data.entrySet()) {
            if (!first) {
                dataTests += ",";
                dataDrilldown+= ",";
            }
            else first = false;
            int sum = 0;
            HashMap<String, Integer> value = entry.getValue();
            dataDrilldown+="{\nname: '"+entry.getKey()+"',\nid: '"+entry.getKey()+"',\n data: [";
            boolean firstSub = false;
            for(Map.Entry<String,Integer> subEntry : value.entrySet()) {
                if (subEntry.getValue() > sum) sum = subEntry.getValue();
                if (!firstSub) {
                    firstSub = true;
                } else {
                    dataDrilldown +=",";
                }
                dataDrilldown+= "[\n'"+subEntry.getKey()+"',\n"+subEntry.getValue()+"\n]";
            }
            dataDrilldown+="]}";
            dataTests += "{\nname:'"+entry.getKey()+"'," +
                    "\ny: "+sum+
                    ", drilldown: '"+entry.getKey()+"'\n}\n";
        }
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
        generateTestChart();

        htmlString = htmlString.replace("$TABLEBODY$", mutant_output);
        htmlString = htmlString.replace("$NBMUTANTSKILLED$", ""+nbKilled);
        htmlString = htmlString.replace("$NBMUTANTSSURVIVED$", ""+nbSurvived);
        htmlString = htmlString.replace("$DATATESTCHART$", dataTests);
        htmlString = htmlString.replace("$DATADRILLDOWN$", dataDrilldown);
        htmlString = htmlString.replace("$MODALCONTENT$", modal_output);
        htmlString = htmlString.replace("$STILLBORN$", ""+nbMortNe);
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
