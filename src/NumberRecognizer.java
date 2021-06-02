import java.util.List;

public class NumberRecognizer {
    State curState;
    List<Token> tokenList;

    public NumberRecognizer(State curState, List<Token> tokenList) {
        this.curState = curState;
        this.tokenList = tokenList;
    }

    public void endOfLexeme() {
        curState.setStateType(State.StateType.START);
    }

    public void checkEndOfNum(char symbol, String buffer) {
        if (Patterns.isPunctuation(symbol)) {
            //if (symbol == ' '){
            tokenList.add(new Token(Token.TokenType.NUMBER, buffer));
            endOfLexeme();
        } else curState.setStateType(State.StateType.ERROR);
    }

    public boolean recognizeNumber(char symbol, String buffer) {
        if (symbol == '.') {
            curState.setStateType(State.StateType.FRACTIONAL_NUM);
            return true;
        }
        if (symbol == 'e' || symbol == 'E') {
            curState.setStateType(State.StateType.NUM_E);
            return true;
        }
        if (!Symbol.isDigit(symbol)) { // check for punctuation symbol
            checkEndOfNum(symbol, buffer);
            return false;
        } else return true;
    }

    public boolean recognizeNumE(char symbol, String buffer) {
        if (!Symbol.isDigit(symbol)) {
            if (symbol == '+' || symbol == '-') {
                curState.setStateType(State.StateType.NUM_E_NEXT);
                return true;
            } else {
                checkEndOfNum(symbol, buffer);
                return false;
            }
        } else return true;
    }

    public boolean recognizeNumENext(char symbol, String buffer) {
        if (!Symbol.isDigit(symbol)) {
            checkEndOfNum(symbol, buffer);
            return false;
        } else return true;
    }


    public boolean recognizeFractionalNum(char symbol, String buffer) {
        if (Symbol.isDigit(symbol)) {
            curState.setStateType(State.StateType.FRACTIONAL_NUM_AFTER_DOT);
            return true;
        }
        if (symbol == '.') {
            buffer = buffer.substring(0, buffer.length() - 1);
            String str = "..";
            tokenList.add(new Token(Token.TokenType.NUMBER, buffer));
            tokenList.add(new Token(Token.TokenType.PUNCTUATION, str));
            endOfLexeme();
        } else {
            // checkEndOfNum(symbol, buffer);
            curState.setStateType(State.StateType.ERROR);
        }// check for punctuation symbol
        return true;
    }

    public boolean recognizeFractionalNumAfterDot(char symbol, String buffer) {
        if (!Symbol.isDigit(symbol)) {
            if (symbol == 'e' || symbol == 'E') {
                curState.setStateType(State.StateType.NUM_E);
                return true;
            } else {
                checkEndOfNum(symbol, buffer);
                return false;
            }
        } // check for punctuation symbol
        else return true;
    }

    public boolean recognizeHexNum(char symbol, String buffer) {
        if (!(Symbol.isValidHex(symbol)) || buffer.length() > 8) {
            checkEndOfNum(symbol, buffer);
            return false;
        } else return true;
    }

    public boolean recognizeError(char symbol, String buffer) {
        if (Patterns.isPunctuation(symbol)) {
            tokenList.add(new Token(Token.TokenType.ERROR, buffer));
            endOfLexeme();
            return false;
        } else {
            curState.setStateType(State.StateType.ERROR);
            return true;
        } // curstate remove
    }
}
