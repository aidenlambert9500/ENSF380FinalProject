package ca.ucalgary.edu.ensf380;


public class Train {
    private final String trainNum;
    private final Line currentLine;
    private String direction;
    private String destination;
    private Station currentStation;


    public Train(String trainNum, Line currentLine, String direction, String destination) {
        this.trainNum = trainNum;
        this.currentLine = currentLine;
        this.direction = direction;
        this.destination = destination;
    }


    public String getTrainNum() {
        return trainNum;
    }


    public Line getCurrentLine() {
        return currentLine;
    }


    public String getDirection() {
        return direction;
    }
    

    public Station getCurrentStation() {
        return currentStation;
    }


    public void setDirection(String direction) {
        this.direction = direction;
    }


    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }


    public String getCurrentStationCode() {
        String statCode = this.currentStation.getStationCode();
        return statCode;
    }
}
