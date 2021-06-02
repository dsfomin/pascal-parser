public class Token {
    private String content;
    private final TokenType tokenType;

    public Token(TokenType tokenType, String content) {
        this.content = content;

        if (tokenType.equals(TokenType.IDENTIFIER)) {
            if (Patterns.isLiteral(content.toLowerCase())) {
                this.tokenType = TokenType.LITERAL;
            } else if (Patterns.isKeyword(content.toLowerCase())) {
                this.tokenType = TokenType.KEYWORD;
            } else
                this.tokenType = tokenType;
        } else
            this.tokenType = tokenType;

        if (tokenType.equals(TokenType.PUNCTUATION)) {
            switch (content) {
                case "\n" -> this.content = "\\" + "n";
                case "\t" -> this.content = "\\" + "t";
            }
        }
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getContent() {
        return content;
    }

    public enum TokenType {
        NUMBER,
        KEYWORD,
        IDENTIFIER,
        LITERAL,
        ERROR,
        OPERATOR,
        PUNCTUATION,
        DIRECTIVE,
        COMMENT
    }
}