package kdancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

class Indexer {

    private ArrayList<String> problemLines;
    private HashMap<String, ArrayList<String[]>> searchMap;

    Indexer() {
        this.searchMap = new HashMap<>();
        this.problemLines = new ArrayList<>();
    }

    HashMap<String, ArrayList<String[]>> indexFile(InputStreamReader fileForIndexing, String targetLemma, String lexemeType) throws IOException {
        boolean compoundWords = lexemeTypeIsCompound(lexemeType);
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
        return this.searchMap;
    }

    private boolean lexemeTypeIsCompound(String lexemeType) {
        switch (lexemeType) {
            case "simple":
                return false;
            case "compound":
                return true;
            default:
                return false;
        }
    }

    ArrayList<String> getProblemLines() {
        return this.problemLines;
    }
    HashMap<String, ArrayList<String[]>> getSearchMap() {
        return this.searchMap;
    }
}
