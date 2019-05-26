package kdancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Indexer {

    private List<String> problemLines;
    private Map<String, List<Lexeme>> searchMap;

    Indexer() {
        this.searchMap = new HashMap<>();
        this.problemLines = new ArrayList<>();
    }

    Map<String, List<Lexeme>> indexFile(InputStreamReader fileForIndexing, String targetLemma, String lemmaType) throws IOException {
        BufferedReader indexingReader = new BufferedReader(fileForIndexing);
        int i = 0;
        String line;
        while ((line = indexingReader.readLine()) != null) {
            i++;
            String[] wordPair = line.split(" ");
            String lexemePart = wordPair[0];
            if (line.contains(targetLemma) && lexemePart.length() >= 2) {
                lexemePart = lexemePart.substring(1, lexemePart.length() - 1);
                if (wordPair.length != 2) {
                    problemLines.add(line + " | Line " + Integer.toString(i));
                } else {
                    String lemmaPart = wordPair[1];
                    Lexeme lexeme = new Lexeme(lexemePart, lemmaPart, i);
                    if (!lexemeTypeIsCompound(lemmaType)) {
                        if (!this.searchMap.keySet().contains(lexeme.getLemma())) {
                            this.searchMap.put(lexeme.getLemma(), new ArrayList<>());
                        }
                        this.searchMap.get(lexeme.getLemma()).add(lexeme);
                    } else if (!lexeme.getLemma().equals(targetLemma)) {
                        if (!this.searchMap.keySet().contains(targetLemma)) {
                            this.searchMap.put(targetLemma, new ArrayList<>());
                        }
                        this.searchMap.get(targetLemma).add(lexeme);
                    }
                }
            }
        }
        indexingReader.close();
        return this.searchMap;
    }

    private boolean lexemeTypeIsCompound(String lemmaType) {
        switch (lemmaType) {
            case "simple":
                return false;
            case "compound":
                return true;
            default:
                return false;
        }
    }

    List<String> getProblemLines() {
        return this.problemLines;
    }

    Map<String, List<Lexeme>> getSearchMap() {
        return this.searchMap;
    }
}
