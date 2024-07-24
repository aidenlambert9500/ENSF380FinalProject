package ca.ucalgary.edu.ensf380;

public class WeatherReport implements Displayable {
    private String temperature;
    private String conditions;

    public WeatherReport(String temperature, String conditions) {
        this.temperature = temperature;
        this.conditions = conditions;
    }

    @Override
    public void display() {
        System.out.println("Weather: " + temperature + ", " + conditions);
    }
}
