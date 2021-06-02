import java.util.List;

public class OperatorRecognizer {
    private final State curState;
    private final List<Token> tokenList;

    OperatorRecognizer(State curState, List<Token> tokenList) {
        this.curState = curState;
        this.tokenList = tokenList;
    }

    private void endOfLexeme() {
        curState.setStateType(State.StateType.START);
    }

    public boolean recognizeCompilerDirective(char symbol) {
        if (symbol == '$') {
            curState.setStateType(State.StateType.COMPILER_DIRECTIVE_NEXT);
            return true;
        } else {
            curState.setStateType(State.StateType.COMMENT_2);
            return false;
        }
    }

    public boolean recognizeCompilerDirectiveNext(char symbol, String buffer, boolean endOfFile) {
        if (symbol == '}') {
            buffer += symbol;
            tokenList.add(new Token(Token.TokenType.DIRECTIVE, buffer));
            endOfLexeme();
        }
        if (Symbol.isDigit(symbol) && buffer.length() == 2) {
            curState.setStateType(State.StateType.COMMENT_2);
            return false;
        } else if (endOfFile) {
            tokenList.add(new Token(Token.TokenType.ERROR, buffer));
        }
        return true;
    }

    public boolean recognizeComment2(char symbol, String buffer, boolean endOfFile) {
        if (symbol == '}') {
            buffer += symbol;
            tokenList.add(new Token(Token.TokenType.COMMENT, buffer));
            endOfLexeme();
        } else if (endOfFile) {
            tokenList.add(new Token(Token.TokenType.ERROR, buffer));
        }
        // else recognizeCommentEnd(symbol,buffer);
        return true;
    }

    public boolean recognizeComment3(char symbol) {
        if (symbol == '*') {
            curState.setStateType(State.StateType.COMMENT_3NEXT);
            return true;//false
        } else {
            tokenList.add(new Token(Token.TokenType.PUNCTUATION, Character.toString('(')));
            endOfLexeme();
            return false;
        }
    }

    public boolean recognizeComment3next(char symbol, String buffer, boolean endOfFile) {
        if (symbol == '*') {
            curState.setStateType(State.StateType.COMMENT_3END);
            return false; // true
        } else if (endOfFile) {
            tokenList.add(new Token(Token.TokenType.ERROR, buffer));
        }
        return true;
    }

    public boolean recognizeComment3end(char symbol, String buffer, boolean endOfFile) {
        if (buffer.toCharArray()[buffer.length() - 1] == '*' && symbol == ')') {
            buffer += symbol;
            tokenList.add(new Token(Token.TokenType.COMMENT, buffer));
            endOfLexeme();
        } else if (endOfFile) {
            tokenList.add(new Token(Token.TokenType.ERROR, buffer));
        }

        return true;
    }

    public boolean recognizeMultiplication(char symbol, String buffer) {
        if (symbol == '=' || symbol == '*') {                              // "*=", "**" - pow
            buffer += symbol;
            tokenList.add(new Token(Token.TokenType.OPERATOR, buffer));
            endOfLexeme();
            return true;
        } else {
            tokenList.add(new Token(Token.TokenType.OPERATOR, buffer)); // "*"
            endOfLexeme();
            return false; // not to miss the next char
        }


    }

    public boolean recognizeAssign(char symbol, String buffer) {
        if (symbol == '=') {                              // "+=", "-=", ":="
            buffer += symbol;
            tokenList.add(new Token(Token.TokenType.OPERATOR, buffer));
            endOfLexeme();
            return true;
        } else {
            tokenList.add(new Token(Token.TokenType.OPERATOR, buffer)); // "+", "-", ":"
            endOfLexeme();
            return false;
        }
    }

    public boolean recognizeRelop(char symbol, String buffer) {
        if (symbol == '=') {                              // "<=", ">="
            buffer += symbol;
            tokenList.add(new Token(Token.TokenType.OPERATOR, buffer));
            endOfLexeme();
            return true;
        }
        if (symbol == '<' || symbol == '>') {             // "<<", "<>", ">>", "><"- sym difference
            buffer += symbol;
            tokenList.add(new Token(Token.TokenType.OPERATOR, buffer));
            endOfLexeme();
            return true;
        } else {
            tokenList.add(new Token(Token.TokenType.OPERATOR, buffer)); // "<", ">"
            endOfLexeme();
            return false;
        }
    }

    public boolean recognizeMinus(char symbol, String buffer) {
        if (Symbol.isDigit(symbol)) {
            curState.setStateType(State.StateType.NUMBER);
            return false;
        } else return recognizeAssign(symbol, buffer);

    }
}
