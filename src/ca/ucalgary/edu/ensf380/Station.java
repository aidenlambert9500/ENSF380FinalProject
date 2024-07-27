package ca.ucalgary.edu.ensf380; 

class Station {
    private String line;
    private String stationCode;
    private String stationID;
    private String stationName;

    public Station(String line, String stationCode, String stationID, String stationName) {
        this.line = line;
        this.stationCode = stationCode;
        this.stationID = stationID;
        this.stationName = stationName;
    }

    public String getLine() {
        return line;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationID() {
        return stationID;
    }

    public String getStationName() {
        return stationName;
    }
}
