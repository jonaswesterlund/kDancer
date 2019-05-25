package kdancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

class ConcordanceBuilder {
    private HashMap<String, ArrayList<String[]>> searchMap;
    private ArrayList<String> problemLines;

    ConcordanceBuilder() {
        this.searchMap = new HashMap<>();
        this.problemLines = new ArrayList<>();
    }

    void indexFile(InputStreamReader fileForIndexing, String targetLemma, boolean compoundWords) throws IOException {
        BufferedReader indexingReader = new BufferedReader(fileForIndexing);
        int i = 0;
        String line;
        while ((line = indexingReader.readLine()) != null) {
            i++;
            String[] wordPair;
            String lexeme;
            if ((line.contains(targetLemma)) && ((lexeme = (wordPair = line.split(" "))[0]).length() >= 2)) {
                lexeme = lexeme.substring(1, lexeme.length() - 1);
                if (wordPair.length != 2) {
                    problemLines.add(line + " | Line " + Integer.toString(i));
                } else {
                    String currentLemma = wordPair[1];
                    if(!compoundWords) {
                        if (!this.searchMap.keySet().contains(currentLemma)) {
                            this.searchMap.put(currentLemma, new ArrayList<>());
                        }
                        String[] lexemePosition = {lexeme, Integer.toString(i)};
                        this.searchMap.get(currentLemma).add(lexemePosition);
                    } else if(!currentLemma.equals(targetLemma)){
                        if (!this.searchMap.keySet().contains(targetLemma)) {
                            this.searchMap.put(targetLemma, new ArrayList<>());
                        }
                        String[] lexemePosition = {lexeme, Integer.toString(i)};
                        this.searchMap.get(targetLemma).add(lexemePosition);
                    }
                }
            }
        }
        indexingReader.close();
    }

    void printConcordance(InputStreamReader fileForConcordance, String targetLemma, int contextSize) throws IOException {
        BufferedReader printReader = new BufferedReader(fileForConcordance);
        StringBuilder context = new StringBuilder();
        int position = 0;
        printReader.mark(1000);
        for (String[] lexemePosition : this.searchMap.get(targetLemma)) {
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

    ArrayList<String> getProblemLines() {
        return this.problemLines;
    }
}
