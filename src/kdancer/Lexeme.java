package kdancer;

public class Lexeme {

    private final String lexeme;
    private final String lemma;
    private final int position;

    Lexeme(String lexeme, String lemma, int position) {
        this.lexeme = lexeme;
        this.lemma = lemma;
        this.position = position;
    }

    public String getLexeme() {
        return lexeme;
    }

    String getLemma() {
        return lemma;
    }

    int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return this.lexeme;
    }
}
