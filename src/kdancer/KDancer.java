package kdancer;

public class KDancer {

    public static void main(String[] args) throws java.io.IOException {
        String file = args[0];
        String targetLemma = args[1];
        int contextSize = Integer.parseInt(args[2]);
        String lemmaType = args[3];
        Processor processor = new Processor(file, targetLemma, contextSize, lemmaType);
        processor.indexAndPrintConcordance();
    }
}
