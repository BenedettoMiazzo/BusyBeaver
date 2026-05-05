package TuringMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TuringMachine {

    private final List<Rule> rules = new ArrayList<>();
    private final Map<String, Map<Character, Rule>> transitionMap = new HashMap<>();
    private final Set<String> haltingStates = new HashSet<>();

    private List<Character> tape;
    private String currentState;
    private int headPosition;
    private char blankSymbol = '_';
    private int stepCount;

    public TuringMachine(String initialState) {
        this.currentState = initialState;
        this.tape = new ArrayList<>();
        this.headPosition = 0;
        this.stepCount = 0;
    }

    public void setBlankSymbol(char blankSymbol) {
        this.blankSymbol = blankSymbol;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
        transitionMap
                .computeIfAbsent(rule.getCurrentState(), state -> new HashMap<>())
                .put(rule.getReadSymbol(), rule);
    }

    public void addRules(List<Rule> ruleList) {
        for (Rule rule : ruleList) {
            addRule(rule);
        }
    }

    public void addHaltingState(String state) {
        haltingStates.add(state);
    }

    public void setCurrentState(String initialState) {
        this.currentState = initialState;
    }

    public void setTape(String input) {
        tape = new ArrayList<>();
        for (char c : input.toCharArray()) {
            tape.add(c);
        }
        if (tape.isEmpty()) {
            tape.add(blankSymbol);
        }
        headPosition = 0;
        stepCount = 0;
    }

    public void setTape(List<Character> tape) {
        this.tape = new ArrayList<>(tape);
        if (this.tape.isEmpty()) {
            this.tape.add(blankSymbol);
        }
        headPosition = 0;
        stepCount = 0;
    }

    public boolean isHalted() {
        return haltingStates.contains(currentState) || getCurrentRule() == null;
    }

    public Rule getCurrentRule() {
        char symbol = readSymbol();
        Map<Character, Rule> stateRules = transitionMap.get(currentState);
        return stateRules == null ? null : stateRules.get(symbol);
    }

    public char readSymbol() {
        ensureTapeBounds();
        return tape.get(headPosition);
    }

    public void writeSymbol(char symbol) {
        ensureTapeBounds();
        tape.set(headPosition, symbol);
    }

    public void moveHead(Rule.Direction direction) {
        switch (direction) {
            case LEFT -> headPosition--;
            case RIGHT -> headPosition++;
            case STAY -> {
                return;
            }
        }
        ensureTapeBounds();
    }

    private void ensureTapeBounds() {
        if (headPosition < 0) {
            tape.add(0, blankSymbol);
            headPosition = 0;
        } else if (headPosition >= tape.size()) {
            tape.add(blankSymbol);
        }
    }

    public boolean step() {
        if (isHalted()) {
            return false;
        }
        Rule rule = getCurrentRule();
        if (rule == null) {
            return false;
        }

        writeSymbol(rule.getWriteSymbol());
        currentState = rule.getNextState();
        moveHead(rule.getDirection());
        stepCount++;
        return true;
    }

    public void run(int maxSteps) {
        int steps = 0;
        while (steps < maxSteps && step()) {
            steps++;
        }
    }

    public String getTapeContents() {
        int left = 0;
        int right = tape.size() - 1;

        while (left < tape.size() && tape.get(left) == blankSymbol) {
            left++;
        }
        while (right >= 0 && tape.get(right) == blankSymbol) {
            right--;
        }
        if (left > right) {
            return String.valueOf(blankSymbol);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = left; i <= right; i++) {
            builder.append(tape.get(i));
        }
        return builder.toString();
    }

    public String getCurrentState() {
        return currentState;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public int getStepCount() {
        return stepCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tape.size(); i++) {
            char c = tape.get(i);
            if (i == headPosition) {
                builder.append('[').append(c).append(']');
            } else {
                builder.append(' ').append(c).append(' ');
            }
        }
        builder.append("  state=").append(currentState);
        return builder.toString();
    }
}
