import java.util.ArrayList;

/**
 * Represents a single GPS data point from the trip log,
 * including the time, latitude, and longitude.
 * TripPoint
 */
public class TripPoint {
    private int time;
    private double lat;
    private double lon;
    
    private static ArrayList<TripPoint> trip = new ArrayList<TripPoint>();

    /**
     * Constructs a TripPoint with a specific time and location.
     * 
     * @param time The time of the point in minutes.
     * @param lat The Latitude coordinate
     * @param lon The Longitude coordinate
     */
    public TripPoint(int time, double lat, double lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Gets the time of this trip point.
     * 
     * @return The time in minutes.
     */
    public int getTime() {
        return time;
    }
    
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    /**
     * Gets the trip history.
     * 
     * @return A copy of the trip ArrayList.
     */
    public static ArrayList<TripPoint> getTrip() {
        return new ArrayList<>(trip);
    }
}
