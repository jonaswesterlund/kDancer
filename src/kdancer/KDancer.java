package kdancer;

import java.util.ArrayList;

public class KDancer {

    public static void main(String[] args) throws java.io.IOException {
        String inputFile = args[0];
        String targetLemma = args[1];
        int contextSize = Integer.parseInt(args[2]);
        String lemmaType = args[3];
        String outputFile = args[4];

        Processor processor = new Processor(inputFile, targetLemma, contextSize, lemmaType);
        ArrayList<String> contexts = processor.indexAndCreateContexts();
        processor.printContextsToFile(contexts, outputFile);
    }
}
