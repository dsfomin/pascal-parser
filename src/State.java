public class State {
    StateType stateType;

    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    public StateType getStateType() {
        return stateType;
    }

    public enum StateType {
        START,
        NUMBER,
        FRACTIONAL_NUM,
        FRACTIONAL_NUM_AFTER_DOT,
        HEX_NUM,
        NUM_E,
        NUM_E_NEXT,
        IDENTIFIER,
        ERROR,
        IND_ERROR,
        COMPILER_DIRECTIVE,
        COMPILER_DIRECTIVE_NEXT,
        COMMENT_2,
        COMMENT_3,
        COMMENT_3NEXT,
        COMMENT_3END,
        STRINGLITERAL,
        MULTIPLY,
        MINUS,
        RELOP,
        ASSIGN,
    }
}
