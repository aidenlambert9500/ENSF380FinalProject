package ca.ucalgary.edu.ensf380; 

class Station {
    private String stationCode;
    private String stationName;
    private final double x;
    private final double y;

    public Station(String stationCode, String stationName, double x, double y) {
        this.x = x;
        this.y = y;
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }


    public String getStationName() {
        return stationName;
    }
    
    public double getXCoord() {
        return x;
    }

    public double getYCoord() {
        return y;
    }
}
