/*
 * @author Jacelynn Doan, Aiden Lambert, Wesley Lui
 */

package ca.ucalgary.edu.ensf380;


/**
 * A class for a train object
 */
public class Train {
    private final String trainNum;
    private final Line currentLine;
    private String direction;
    private Station currentStation;


    /**
     * A constructor to create a train object with a train number, current line, and direction
     * @param trainNum a string of the train's unique number
     * @param currentLine a Line object which the train is currently on
     * @param direction a string representing the direction that the train is travelling in currently
     */
    public Train(String trainNum, Line currentLine, String direction) {
        this.trainNum = trainNum;
        this.currentLine = currentLine;
        this.direction = direction;
    }


    /**
     * A method to get the number of a train
     * @return a string of the train's number
     */
    public String getTrainNum() {
        return trainNum;
    }


    /**
     * A method to get the current line that the train is on
     * @return a Line object that the train is currently on
     */
    public Line getCurrentLine() {
        return currentLine;
    }


    /**
     * A method to get the current direction that the train is travelling in
     * @return the direction the train is travelling in string format
     */
    public String getDirection() {
        return direction;
    }
    
    /**
     * Returns the current station of this object.
     * @return the current station
     */
    public Station getCurrentStation() {
        return currentStation;
    }

    /**
     * Sets the direction for this object.
     * @param direction the direction to be set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Sets the current station of this object.
     * @param currentStation the station to set as the current station
     */
    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    /**
     * Returns the code of the current station.
     * If the current station is not set, returns {@code null}.
     * @return the station code of the current station, or {@code null} if no current station is set
     */
    public String getCurrentStationCode() {
        if (this.currentStation != null) {
            return this.currentStation.getStationCode();
        } else {
            return null;
        }
    }

}
