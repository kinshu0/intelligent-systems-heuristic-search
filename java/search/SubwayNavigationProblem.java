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
        ArrayList<Station> adjacentStations = map.adjacentStations(station);
        ArrayList<Tuple> successors = new ArrayList<>();

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


}