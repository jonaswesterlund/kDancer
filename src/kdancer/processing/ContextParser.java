package kdancer.processing;

import kdancer.domain.Context;
import kdancer.domain.Lexeme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ContextParser {

    List<Context> createContexts(InputStreamReader fileForConcordance,
                                 Map<String, List<Lexeme>> searchMap,
                                 String targetLemma,
                                 int contextSize) throws IOException {
        BufferedReader printReader = new BufferedReader(fileForConcordance);
        List<Context> contexts = new ArrayList<>();
        int position = 0;
        printReader.mark(0);
        for (Lexeme lexeme : searchMap.get(targetLemma)) {
            printReader.reset();
            Context context = new Context(lexeme);
            String line = "";
            while (position < lexeme.getPosition() - contextSize) {
                line = printReader.readLine();
                position++;
            }
            printReader.mark(1000);
            for (int j = 0; j < 2 * contextSize + 1; j++) {
                String[] wordPair = line.split(" ");
                String lexemePart;
                if (wordPair.length > 2) {
                    Lexeme contextLexemeWithSpace = new Lexeme("<LEXEME INCLUDES SPACE>", targetLemma, position);
                    context.addToContext(contextLexemeWithSpace);
                } else {
                    lexemePart = wordPair[0];
                    if (lexemePart.length() < 3) {
                        lexemePart = "<EMPTY WORD>";
                    } else {
                        lexemePart = lexemePart.substring(1, lexemePart.length() - 1);
                    }
                    Lexeme contextLexeme = new Lexeme(lexemePart, targetLemma, position);
                    context.addToContext(contextLexeme);
                }
                if ((line = printReader.readLine()) == null) {
                    break;
                }
            }
            contexts.add(context);
        }
        return contexts;
    }
}
