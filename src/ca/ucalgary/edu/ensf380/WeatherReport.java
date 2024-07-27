package ca.ucalgary.edu.ensf380;

public class WeatherReport {	
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
