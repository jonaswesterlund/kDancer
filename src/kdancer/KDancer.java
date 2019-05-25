package kdancer;

/**
 * Test
 */
public class KDancer {

    public static void main(String[] args2) throws java.io.IOException {
        String[] args = {"data/lait-koko.lem", "menettely", "3", "simple"};
        String file = args[0];
        String targetLemma = args[1];
        int contextSize = Integer.parseInt(args[2]);
        String lexemeType = args[3];
        Processor processor = new Processor(file, targetLemma, contextSize, lexemeType);
        processor.indexAndPrintConcordance();
    }
}
