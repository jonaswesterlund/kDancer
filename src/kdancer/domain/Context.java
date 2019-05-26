package kdancer.domain;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private final Lexeme centralLexeme;
    private List<Lexeme> context;

    public Context(Lexeme centralLexeme) {
        this.centralLexeme = centralLexeme;
        this.context = new ArrayList<>();
    }

    public Lexeme getCentralLexeme() {
        return centralLexeme;
    }

    public List<Lexeme> getContext() {
        return context;
    }

    public void setContext(List<Lexeme> context) {
        this.context = context;
    }

    public void addToContext(Lexeme lexeme) {
        context.add(lexeme);
    }

    @Override
    public String toString() {
        StringBuilder contextString = new StringBuilder();
        for (Lexeme lexeme : this.context) {
            contextString.append(lexeme.toString()).append(" ");
        }
        contextString.append("| Line ").append(this.centralLexeme.getPosition());
        return contextString.toString();
    }
}
