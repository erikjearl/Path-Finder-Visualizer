import java.util.*;

public class AStarSearch {

    MapLocation start;
    MapLocation goal;
    int xExtent;
    int yExtent;
    Set<MapLocation> blockLocations;
    public AStarSearch(MapLocation start, MapLocation goal, int xExtent, int yExtent, Set<MapLocation> blockLocations){
        this.start = start;
        this.goal = goal;
        this.xExtent = xExtent;
        this.yExtent = yExtent;
        this.blockLocations = blockLocations;
    }

    public Stack<MapLocation> getPath(){
        return AStarSearch(start, goal, xExtent, yExtent, blockLocations);
    }

    /**
     * @param start Starting position of the footman
     * @param goal MapLocation of the townhall
     * @param xExtent Width of the map
     * @param yExtent Height of the map
     * @param blockLocations Set of positions occupied by road blocks
     * @return Stack of positions with top of stack being first move in plan
     */
    private Stack<MapLocation> AStarSearch(MapLocation start, MapLocation goal, int xExtent, int yExtent, Set<MapLocation> blockLocations)
    {
        // search Lists
        Comparator comparator = pathComparator(start, goal);
        PriorityQueue<Stack<MapLocation>> openList = new PriorityQueue<Stack<MapLocation>>(11, comparator);

        ArrayList<MapLocation> closedList = new ArrayList<MapLocation>();
        Stack<MapLocation> finalPath = new Stack<MapLocation>();
        Stack<MapLocation> initialPath = new Stack<MapLocation>();

        initialPath.add(start);
        openList.add(initialPath);

        // The search
        while (!openList.isEmpty()) {

            Stack<MapLocation> currentPath = openList.poll();
            MapLocation currentLoc = currentPath.peek();

            if (currentLoc.equals(goal)) {
                // found the goal
                currentPath.pop();
                while (!currentPath.isEmpty()) {
                    finalPath.push(currentPath.pop());
                }
                break;
            } else {
                // didn't find the goal
                MapLocation[] successors = expandState(currentLoc, goal, xExtent, yExtent);
                closedList.add(currentLoc);

                for (MapLocation successor : successors) {
                    //System.out.println(successor + " : " + blockLocations);
                    if (successor != null  && !closedList.contains(successor)
                            && !blockLocations.contains(successor) && !alreadyPath(openList, successor)) {
                        Stack<MapLocation> newPath = (Stack<MapLocation>)currentPath.clone();
                        newPath.push(successor);
                        openList.add(newPath);
                    }
                }

            }
        }

        if (finalPath.isEmpty()) {
            System.out.println("No Avaliable path");
            return null;
        }

        // return the path
        finalPath.pop();
        return finalPath;
    }

    /**
     * @return comparator for lists of locations
     */
    private Comparator pathComparator(MapLocation start, MapLocation goal) {

        // class that compares two MapLocations by their heuristic
        class PathComparator implements Comparator<Stack<MapLocation>> {
            MapLocation start;
            MapLocation goal;

            public PathComparator(MapLocation start, MapLocation goal) {
                this.start = start;
                this.goal = goal;
            }

            public int compare(Stack<MapLocation> o1, Stack<MapLocation> o2) {
                int dist1 = o1.size();
                int dist2 = o2.size();
                int chebyshev1 = chebyshev(o1.peek(), goal);
                int chebyshev2 = chebyshev(o2.peek(), goal);
                if (chebyshev1 + dist1 == chebyshev2 + dist2) {
                    return 0;
                }
                return  dist1 + chebyshev1 < dist2 + chebyshev2 ? -1 : 1;

            }

            public boolean equals(Object obj) {
                return false;
            }
        }

        return new PathComparator(start, goal);
    }

    /**
     * Calculates the Chebyshev distance
     *
     * @param start the node we are one
     * @param goal  the node we want to get to
     * @return      the Chebyshev distance
     */
    private int chebyshev(MapLocation start, MapLocation goal) {
        int deltaX = goal.x - start.x;
        int deltaY = goal.y - start.y;

        deltaX = deltaX < 0 ? deltaX * -1 : deltaX;
        deltaY = deltaY < 0 ? deltaY * -1 : deltaY;

        return deltaX < deltaY ? deltaY : deltaX;
    }

    /**
     * returns all successor nodes of a MapLocation
     * @param loc
     * @return the successor nodes
     */
    private MapLocation[] expandState(MapLocation loc, MapLocation goal, int xExtent, int yExtent) {
        MapLocation north       = new MapLocation(loc.x, loc.y - 1);
        MapLocation south       = new MapLocation(loc.x, loc.y + 1);
        MapLocation west        = new MapLocation(loc.x - 1, loc.y);
        MapLocation east        = new MapLocation(loc.x + 1, loc.y);
        MapLocation northWest   = new MapLocation(loc.x - 1, loc.y - 1);
        MapLocation northEast   = new MapLocation(loc.x + 1, loc.y - 1);
        MapLocation southWest   = new MapLocation(loc.x - 1, loc.y + 1);
        MapLocation southEast   = new MapLocation(loc.x + 1, loc.y + 1);
        MapLocation[] successors = {north, south, east, west, northEast, northWest, southEast, southWest};

        for (int i = 0; i < successors.length; i++) {
            if ( successors[i].x < 0 || successors[i].x > xExtent || successors[i].y < 0 || successors[i].y > yExtent) {
                successors[i] = null;
            }
        }

        return successors;
    }

    /**
     * Checks the open list to see if a path already has reached the location
     *
     * @param openList  the open list
     * @param location  the location
     * @return          Whether there is already a path
     */
    private boolean alreadyPath(PriorityQueue<Stack<MapLocation>> openList, MapLocation location) {
        for (Stack<MapLocation> path : openList) {
            if (path.peek().equals(location)) {
                return true;
            }
        }

        return false;
    }

}
