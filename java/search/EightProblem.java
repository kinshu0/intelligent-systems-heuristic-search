package search;

import java.util.ArrayList;

public class EightProblem extends Problem {

    public EightProblem(State initial) {
        super(initial);
        this.goal = new State("123456780");
    }

    public EightProblem(String initial) {
        this(new State(initial));
    }

    @Override
    public ArrayList<Tuple> successor(State state) {
        ArrayList<Tuple> successors = new ArrayList<>();

        StringBuilder stateStringBuilder = new StringBuilder(state.getName());

        int zeroIndex = stateStringBuilder.indexOf("0");

        if (zeroIndex / 3 > 0) {
            Action slideUpAction = new Action("Slide 0 Up");
            StringBuilder slideUp = new StringBuilder(stateStringBuilder);
            slideUp.setCharAt(zeroIndex, slideUp.charAt(zeroIndex - 3));
            slideUp.setCharAt(zeroIndex - 3, '0');
            successors.add(new Tuple(slideUpAction, new State(slideUp.toString())));
        }
        if (zeroIndex % 3 > 0) {
            Action slideUpAction = new Action("Slide 0 Left");
            StringBuilder slideUp = new StringBuilder(stateStringBuilder);
            slideUp.setCharAt(zeroIndex, slideUp.charAt(zeroIndex - 1));
            slideUp.setCharAt(zeroIndex - 1, '0');
            successors.add(new Tuple(slideUpAction, new State(slideUp.toString())));
        }
        if (zeroIndex / 3 < 2) {
            Action slideUpAction = new Action("Slide 0 Down");
            StringBuilder slideUp = new StringBuilder(stateStringBuilder);
            slideUp.setCharAt(zeroIndex, slideUp.charAt(zeroIndex + 3));
            slideUp.setCharAt(zeroIndex + 3, '0');
            successors.add(new Tuple(slideUpAction, new State(slideUp.toString())));
        }
        if (zeroIndex % 3 < 2) {
            Action slideUpAction = new Action("Slide 0 Right");
            StringBuilder slideUp = new StringBuilder(stateStringBuilder);
            slideUp.setCharAt(zeroIndex, slideUp.charAt(zeroIndex + 1));
            slideUp.setCharAt(zeroIndex + 1, '0');
            successors.add(new Tuple(slideUpAction, new State(slideUp.toString())));
        }

        return successors;

    }

    @Override
    public double h(Node node) {
        State state = node.getState();
        int manhattanDistance = 0;
        for (int i = 0; i < state.getName().length(); i++) {
            char c = state.getName().charAt(i);
            if (c != '0') {
                int index = goal.getName().indexOf(c);
                int x1 = i % 3;
                int y1 = i / 3;
                int x2 = index % 3;
                int y2 = index / 3;
                manhattanDistance += Math.abs(x1 - x2) + Math.abs(y1 - y2);
            }
        }
        return manhattanDistance;
    }
    

    
}
