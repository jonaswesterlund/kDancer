package kdancer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

    List<Context> indexAndCreateContexts() throws IOException {
        Indexer indexer = new Indexer();
        ContextParser contextParser = new ContextParser();
        Map<String, List<Lexeme>> searchMap = indexer.indexFile(this.fileForIndexing, this.targetLemma, this.lemmaType);
        return contextParser.createContexts(this.fileForConcordance, searchMap, this.targetLemma, this.contextSize);
    }

    void printContextsToFile(List<Context> contexts, String outputFile) throws IOException {
        String file = generateFilename(outputFile);
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        generateFileHeader(printWriter, contexts);
        for(Context context : contexts) {
            printWriter.println(context.toString());
        }
        printWriter.close();
    }

    private String generateFilename(String outputFile) {
        if(outputFile == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            LocalDateTime now = LocalDateTime.now();
            return this.targetLemma + "_" + this.contextSize + "_" + this.lemmaType + "_" + dtf.format(now);
        } else {
            return outputFile;
        }
    }

    void generateFileHeader(PrintWriter printWriter, List<Context> contexts) {
        printWriter.printf("Lemma: %s%n", this.targetLemma);
        printWriter.printf("Context size: %d%n", this.contextSize);
        printWriter.printf("Lemma type: %s%n", this.lemmaType);
        printWriter.printf("Occurrences: %d%n", contexts.size());
        printWriter.println("");
    }
}
