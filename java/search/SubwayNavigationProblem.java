package search;

import java.util.ArrayList;

import subway.Station;
import subway.SubwayMap;

public class SubwayNavigationProblem extends Problem {

    SubwayMap map;

    public SubwayNavigationProblem(State initial, State goal, SubwayMap map) {
        super(initial, goal);
        this.map = map;
    }

    public SubwayNavigationProblem(String initialStationName, String goalStationName, SubwayMap map) {
        this(new State(initialStationName), new State(goalStationName), map);
    }

    @Override
    public ArrayList<Tuple> successor(State state) {
        Station station = map.getStationByName(state.getName());

        ArrayList<Tuple> successors = new ArrayList<>();

        ArrayList<Station> adjacentStations;

        try {
            adjacentStations = map.adjacentStations(station);
        } catch (NullPointerException e) {
            return successors;
        }

        for (Station station2 : adjacentStations) {
            Action action = new Action("Travel from " + state.getName() + " to " + station2.name);
            State adjState = new State(station2.name);
            successors.add(new Tuple(
                action, adjState));
        }

        return successors;

    }

    public double h(Node node) {
        Station stationU = map.getStationByName(initial.getName());
        Station stationV = map.getStationByName(node.getState().getName());
        return SubwayMap.straightLineDistance(stationU, stationV);
    }

    @Override
    public double pathCost(double c, State state1, Action action, State state2) {
        // TODO Auto-generated method stub
        // return super.pathCost(c, state1, action, state2);
        return c + SubwayMap.straightLineDistance(map.getStationByName(state1.getName()), map.getStationByName(state2.getName()));
    }


}