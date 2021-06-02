import java.util.List;

public class IdentifierRecognizer {
    State curState;
    List<Token> tokenList;

    public IdentifierRecognizer(State curState, List<Token> tokenList) {
        this.curState = curState;
        this.tokenList = tokenList;
    }

    public void endOfLexeme() {
        curState.setStateType(State.StateType.START);
    }

    public boolean recognizeIdentifier(char symbol, String buffer) {
        if (symbol == '.') {
            if (buffer.toLowerCase().equals("end")) {
                tokenList.add(new Token(Token.TokenType.IDENTIFIER, buffer));
                tokenList.add(new Token(Token.TokenType.PUNCTUATION, Character.toString(symbol)));
                endOfLexeme();
                return true;
            }
            if (buffer.toCharArray()[buffer.length() - 1] == '.') { // more than 1 dot
                buffer = buffer.substring(0, buffer.length() - 1);
                String str = "..";
                tokenList.add(new Token(Token.TokenType.IDENTIFIER, buffer));
                tokenList.add(new Token(Token.TokenType.PUNCTUATION, str));
                endOfLexeme();
                return true;
            }
            return true;
        } else if (!Symbol.isLetter(symbol) && !(Symbol.isDigit(symbol)) && symbol != '.') { // && symbol != '.' qualified identifiers
            if (Patterns.isPunctuation(symbol)) {// symbol == '.'
                tokenList.add(new Token(Token.TokenType.IDENTIFIER, buffer));
                endOfLexeme();
                return false;
            } else {
                //curState.setState(State.IND_ERROR);
                tokenList.add(new Token(Token.TokenType.IDENTIFIER, buffer));
                tokenList.add(new Token(Token.TokenType.ERROR, Character.toString(symbol)));
                endOfLexeme();
                return true;
            }
        } else return true;
    }

    public boolean recognizeErrInd(char symbol, String buffer) {
        if (Patterns.isPunctuation(symbol)) {
            tokenList.add(new Token(Token.TokenType.ERROR, buffer));
            tokenList.add(new Token(Token.TokenType.PUNCTUATION, Character.toString(symbol)));
            endOfLexeme();
        }
        return true;

    }

    public void checkTerminateSymbol(char character) {
        if (Patterns.isPunctuation(character))
            tokenList.add(new Token(Token.TokenType.PUNCTUATION, Character.toString(character)));
        else
            tokenList.add(new Token(Token.TokenType.ERROR, Character.toString(character)));
        curState.setStateType(State.StateType.START);
    }

    public boolean recognizeStringLiteral(char symbol, String buffer) {
        if (symbol == '\r' || symbol == '\n') {
            tokenList.add(new Token(Token.TokenType.ERROR, buffer));
            endOfLexeme();
            return true;
        } else if (symbol == '\"' || symbol == '\'') {
            tokenList.add(new Token(Token.TokenType.LITERAL, buffer + symbol));
            endOfLexeme();
            return true;
        } else {

            return true;
        }
    }
}
