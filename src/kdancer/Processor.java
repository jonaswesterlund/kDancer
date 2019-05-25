package kdancer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    void indexAndPrintConcordance() throws IOException {
        Indexer indexer = new Indexer();
        Concordance concordance = new Concordance();
        HashMap<String, ArrayList<String[]>> searchMap = indexer.indexFile(this.fileForIndexing, this.targetLemma, this.lemmaType);
        concordance.printConcordance(this.fileForConcordance, searchMap, this.targetLemma, this.contextSize);
    }
}
