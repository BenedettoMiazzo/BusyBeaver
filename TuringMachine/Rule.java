package TuringMachine;

public class Rule {

    public enum Direction {
        LEFT,
        RIGHT,
        STAY
    }

    private final String currentState;
    private final char readSymbol;
    private final String nextState;
    private final char writeSymbol;
    private final Direction direction;

    public Rule(String currentState, char readSymbol, String nextState, char writeSymbol, Direction direction) {
        this.currentState = currentState;
        this.readSymbol = readSymbol;
        this.nextState = nextState;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
    }

    public String getCurrentState() {
        return currentState;
    }

    public char getReadSymbol() {
        return readSymbol;
    }

    public String getNextState() {
        return nextState;
    }

    public char getWriteSymbol() {
        return writeSymbol;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean appliesTo(String state, char symbol) {
        return currentState.equals(state) && readSymbol == symbol;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s) -> (%s, %s, %s)",
                currentState,
                readSymbol,
                nextState,
                writeSymbol,
                direction);
    }
}
