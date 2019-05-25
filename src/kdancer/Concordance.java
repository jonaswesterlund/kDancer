package kdancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

class Concordance {

    void printConcordance(InputStreamReader fileForConcordance,
                          HashMap<String, ArrayList<String[]>> searchMap,
                          String targetLemma,
                          int contextSize) throws IOException {
        BufferedReader printReader = new BufferedReader(fileForConcordance);
        StringBuilder context = new StringBuilder();
        int position = 0;
        printReader.mark(1000);
        for (String[] lexemePosition : searchMap.get(targetLemma)) {
            printReader.reset();
            String line = "";
            while (position < Integer.parseInt(lexemePosition[1]) - contextSize) {
                line = printReader.readLine();
                position++;
                printReader.mark(1000);
            }
            for (int j = 0; j < 2 * contextSize + 1; j++) {
                String[] wordPair = line.split(" ");
                String lexeme;
                if (wordPair.length > 2) {
                    lexeme = "<LEXEME INCLUDES SPACE>";
                } else {
                    lexeme = wordPair[0];
                    if (lexeme.length() < 3) {
                        lexeme = "<EMPTY WORD>";
                    } else {
                        lexeme = lexeme.substring(1, lexeme.length() - 1);
                    }
                }
                context.append(lexeme).append(" ");
                if ((line = printReader.readLine()) == null) {
                    break;
                }
            }
            context.append("|| Line ").append(Integer.parseInt(lexemePosition[1]));
            System.out.println(context);
            context = new StringBuilder();
        }
    }
}
