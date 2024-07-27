package ca.ucalgary.edu.ensf380;

public class TrainInformation implements Displayable {
    private String trainNumber;
    private String arrivalTime;
    private String status;
    private Station station;
    private String direction;

    public TrainInformation(String trainNumber, String arrivalTime, String status, Station station, String direction) {
        this.trainNumber = trainNumber;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.station = station;
        this.direction = direction;
    }

    @Override
    public void display() {
        System.out.println("Train " + trainNumber + " arriving at " + arrivalTime + " is " + status + " at " + station + " heading " + direction);
    }

    public Station getStation() {
        return station;
    }

    public String getDirection() {
        return direction;
    }

    public String getTrainInfo() {
        return "Train " + trainNumber + " arriving at " + arrivalTime + " is " + status + " at " + station + " heading " + direction;
    }
}