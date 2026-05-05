package TuringMachine;

public class Main {
    public static void main(String[] args) {
        TuringMachine tm = new TuringMachine("q0");

        tm.addRule(new Rule("q0", '0', "q1", '1', Rule.Direction.RIGHT));
        tm.addRule(new Rule("q0", '1', "q0", '0', Rule.Direction.RIGHT));
        tm.addRule(new Rule("q1", '0', "q0", '1', Rule.Direction.RIGHT));
        tm.addRule(new Rule("q1", '1', "q1", '0', Rule.Direction.RIGHT));

        tm.addHaltingState("q2");

        tm.setTape("1101");
        tm.run(1000);

        System.out.println("Final State: " + tm.getCurrentState()); 
    }
}
