package ca.ucalgary.edu.ensf380;

import javax.swing.*;

public class WeatherReport implements Displayable {
	// Member variables
	
	private JLabel weatherLabel;

	// Constructor
    public WeatherReport(JLabel weatherLabel) {
        this.weatherLabel = weatherLabel;
    }

	@Override // from Displayable class
	public void display() {
		System.out.println("Displaying Weather Report...");
	}
	
	public void updateWeather(String city) {
        WeatherParser weatherParser = new WeatherParser();
        try {
            String temperature = weatherParser.getWeatherInfo(city);
            weatherLabel.setText("City: " + city + " - Temperature: " + temperature);
        } catch (Exception e) {
            weatherLabel.setText("An error occurred while fetching the weather information.");
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		// Use command-line argument for the city name
		if (args.length == 0) {
			System.out.println("Please provide a city name as a command line argument.");
			return;
		}

		String city = args[0];
		WeatherParser weatherParser = new WeatherParser();
		try {
			String temperature = weatherParser.getWeatherInfo(city);
			System.out.println("City: " + city);
			System.out.println("Temperature: " + temperature);
			// System.out.println("Time: " + time);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching the weather information.");
			e.printStackTrace();
		}
	}
}
