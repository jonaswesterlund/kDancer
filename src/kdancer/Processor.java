package kdancer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class Processor {

    private InputStreamReader fileForIndexing;
    private InputStreamReader fileForConcordance;
    private String targetLemma;
    private int contextSize;
    private String lemmaType;

    Processor(String file, String targetLemma, int contextSize, String lexemeType) throws FileNotFoundException {
        this.fileForIndexing = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1);
        this.fileForConcordance = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1);
        this.targetLemma = targetLemma;
        this.contextSize = contextSize;
        this.lemmaType = lexemeType;
    }

    ArrayList<String> indexAndCreateContexts() throws IOException {
        Indexer indexer = new Indexer();
        ContextParser contextParser = new ContextParser();
        HashMap<String, ArrayList<String[]>> searchMap = indexer.indexFile(this.fileForIndexing, this.targetLemma, this.lemmaType);
        return contextParser.createContexts(this.fileForConcordance, searchMap, this.targetLemma, this.contextSize);
    }

    void printContextsToFile(ArrayList<String> contexts, String outputFile) throws IOException {
        if(outputFile == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            LocalDateTime now = LocalDateTime.now();
            outputFile = this.targetLemma + "_" + this.contextSize + "_" + this.lemmaType + "_" + dtf.format(now);
        }
        FileWriter fileWriter = new FileWriter(outputFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("Lemma: %s%n", this.targetLemma);
        printWriter.printf("Context size: %d%n", this.contextSize);
        printWriter.printf("Lemma type: %s%n", this.lemmaType);
        printWriter.printf("Occurrences: %d%n", contexts.size());
        printWriter.println("");
        for(String context : contexts) {
            printWriter.println(context);
        }
        printWriter.close();
    }
}
