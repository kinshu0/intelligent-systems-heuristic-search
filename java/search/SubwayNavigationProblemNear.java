package search;

import subway.SubwayMap;

public class SubwayNavigationProblemNear extends SubwayNavigationProblem {


    double thresholdDistance;
    
    public SubwayNavigationProblemNear(String initialStationName, String goalStationName, SubwayMap map, double thresholdDistance) {
        super(initialStationName, goalStationName, map);
        this.thresholdDistance = thresholdDistance;
    }



    @Override
    public boolean goalTest(State state) {
        return SubwayMap.straightLineDistance(map.getStationByName(state.getName()), map.getStationByName(goal.getName())) < thresholdDistance;
    }

    
}
