package ca.ucalgary.edu.ensf380;

public class TrainInformation implements Displayable {
    private String trainNumber;
    private String arrivalTime;
    private String status;

    public TrainInformation(String trainNumber, String arrivalTime, String status) {
        this.trainNumber = trainNumber;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }

    @Override
    public void display() {
        System.out.println("Train " + trainNumber + " arriving at " + arrivalTime + " is " + status);
    }
}
