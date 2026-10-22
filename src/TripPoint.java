import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

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

    /**
     * Reads trip data from a CSV file and populates the trip ArrayList.
     * Each line of valid data is converted into a TripPoint object.
     * 
     * @param filename The name of the CSV file to read.
     */
    public static void readFile(String filename) {
        trip.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skips header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int time = Integer.parseInt(parts[0]);
                double lat = Double.parseDouble(parts[1]);
                double lon = Double.parseDouble(parts[2]);
                trip.add(new TripPoint(time, lat, lon));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Calculates the total elapsed time of the trip.
     * Converts the tracked minutes into hours
     * 
     * @return the total trip duration in hours, or 0.0 if the trip has no data.
     */
    public static double totalTime() {
        // If there are fewer than two points, no time has passed.
        if (trip == null || trip.size() < 2) {
            return 0.0;
        }
        
        // Get the time from the first point in the CSV
        int startTime = trip.get(0).getTime();
        
        // Get the time from the very last point in the CSV
        int endTime = trip.get(trip.size() - 1).getTime();
        
        // Calculate the difference in minutes
        int totalMinutes = endTime - startTime;
        
        // Convert to hours using 60.0.
        return totalMinutes / 60.0;
    }

    public static double haversineDistance(TripPoint a, TripPoint b) {
        final double RADIUS = 6371.0; // Radius of the Earth in kilometers

        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(a.getLat());
        double lon1 = Math.toRadians(a.getLon());
        double lat2 = Math.toRadians(b.getLat());
        double lon2 = Math.toRadians(b.getLon());

        // Calculate the differences
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        // Apply the Haversine formula
        double aVal = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                      Math.cos(lat1) * Math.cos(lat2) *
                      Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double cTerm = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));

        // Calculate the distance
        return RADIUS * cTerm;
    }
}
