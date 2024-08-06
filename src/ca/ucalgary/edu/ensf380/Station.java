package ca.ucalgary.edu.ensf380; 

/**
 * A class to represent a train station
 */
class Station {
    private String stationCode;
    private String stationName;
    private final double x;
    private final double y;

    /**
     * A constructor to create a Station
     * @param stationCode a unique code in string format for each station
     * @param stationName a string of the name of the station
     * @param x a double coordinate for the x position of the train station
     * @param y a double coordinate for the y position of the train station
     */
    public Station(String stationCode, String stationName, double x, double y) {
        this.x = x;
        this.y = y;
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    /**
     * A method to get the unique station code of a station
     * @return a string of the station code
     */
    public String getStationCode() {
        return this.stationCode;
    }


    /**
     * A method to get the station name 
     * @return a string of the name of the station 
     */
    public String getStationName() {
        return this.stationName;
    }
    
    /**
     * A method to return the x coordinate of a station
     * @return the x coordinate in double format
     */
    public double getXCoord() {
        return this.x;
    }

    /**
     * A method to return the y coordinate of a station
     * @return the y coordinate in double format
     */
    public double getYCoord() {
        return this.y;
    }
}
