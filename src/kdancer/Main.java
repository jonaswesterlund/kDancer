package kdancer;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws java.io.IOException {
        //String[] args = {"data/lait-koko.lem", "menettely", "3", "simple"};
        String file = args[0];
        String targetLemma = args[1];
        int contextSize = Integer.parseInt(args[2]);
        InputStreamReader fileForIndexing = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1);
        InputStreamReader fileForConcordance = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1);

        ConcordanceBuilder concordanceBuilder = new ConcordanceBuilder();
        switch (args[3]) {
            case "simple":
                concordanceBuilder.indexFile(fileForIndexing, targetLemma, false);
                break;
            case "compound":
                concordanceBuilder.indexFile(fileForIndexing, targetLemma, true);
                break;
            default:
                return;
        }
        concordanceBuilder.printConcordance(fileForConcordance, targetLemma, contextSize);
    }
}
