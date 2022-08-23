public class MapLocation {
    public int x, y;
    public MapLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        return hash;
    }

    // returns if two MapLocations are the same
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return this == null;
        }
        if (obj instanceof MapLocation) {
            MapLocation loc = (MapLocation) obj;
            return this.x == loc.x && this.y == loc.y;
        }
        return false;
    }

    // A String representation of a MapLocation
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}