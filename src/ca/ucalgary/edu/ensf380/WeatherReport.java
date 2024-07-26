package ca.ucalgary.edu.ensf380;

import libraries.WeatherParser;

public class WeatherReport implements Displayable {

	@Override // from Displayable class
	public void display() {
		System.out.println("Weather: ");
	}

	public static void main(String[] args) {
		// Use command-line argument for the city name
		if (args.length == 0) {
			System.out.println("Please provide a city name as a command line argument.");
			return;
		}

		String city = args[0];
		WeatherParser weatherParser = new WeatherParser();
		String temperature = weatherParser.getWeatherInfo(city);
		System.out.println("Temperature for " + city + ": " + temperature);
	}
}
