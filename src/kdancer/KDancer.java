package kdancer;

import kdancer.domain.Context;
import kdancer.processing.Processor;

import java.util.List;

public class KDancer {

    public static void main(String[] args) throws java.io.IOException {
        String inputFile = args[0];
        String targetLemma = args[1];
        int contextSize = Integer.parseInt(args[2]);
        String lemmaType = args[3];
        String outputFile;
        if (args.length != 5) {
            outputFile = null;
        } else {
            outputFile = args[4];
        }

        Processor processor = new Processor(inputFile, targetLemma, contextSize, lemmaType);
        List<Context> contexts = processor.indexAndCreateContexts();
        processor.printContextsToFile(contexts, outputFile);
    }
}
