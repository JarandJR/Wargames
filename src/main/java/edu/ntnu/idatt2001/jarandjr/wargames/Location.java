package edu.ntnu.idatt2001.jarandjr.wargames;

/**
 * The class for a location on the battlefield
 */
public class Location {

    private int x;
    private int y;

    /**
     * The constructor for the location
     *
     * @param x is the x-coordinate
     * @param y is the y-coordinate
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The method for getting the x-coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * The method for getting the y-coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * The method for setting the x-coordinate
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * The method for setting the y-coordinate
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * An overrided equalsmethod for checking if a location is equal
     * @param o the object being compared
     * @return true or false depending on if the object is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public String toString() {
        return  "x: " + x +
                ", y: " + y;
    }
}
