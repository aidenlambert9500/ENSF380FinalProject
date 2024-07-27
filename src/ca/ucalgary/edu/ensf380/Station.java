package ca.ucalgary.edu.ensf380;

public class Station {
    private String line;
    private String stationNumber;
    private String stationCode;
    private String stationName;

    public Station(String line, String stationNumber, String stationCode, String stationName) {
        this.line = line;
        this.stationNumber = stationNumber;
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    public String getLine() {
        return line;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public String toString() {
        return stationName + " (" + stationCode + ")";
    }
}
