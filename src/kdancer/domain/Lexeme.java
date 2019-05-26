package kdancer.domain;

public class Lexeme {

    private final String lexeme;
    private final String lemma;
    private final int position;

    public Lexeme(String lexeme, String lemma, int position) {
        this.lexeme = lexeme;
        this.lemma = lemma;
        this.position = position;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getLemma() {
        return lemma;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return this.lexeme;
    }
}
