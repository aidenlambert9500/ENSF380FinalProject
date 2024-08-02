package ca.ucalgary.edu.ensf380;

/*
 * @author: Aiden Lambert, Jacelynn Doan, Wesley Lui 
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * A class to retrieve weather and time information from specified cities
 */
public class WeatherParser {

	/**
	 * A method to get the current weather in a specified city
	 * @param city name of the city to retrieve the weather from
	 * @return the temperature of city in String format
	 */
	public String getWeatherInfo(String city) {
		String url = "https://wttr.in/" + city;

		try {
			// Fetch the HTML from the URL
			Document doc = Jsoup.connect(url).get();

			// Extract the entire text content
			String textContent = doc.text();

			// Define a regex pattern to match the weather information
			Pattern pattern = Pattern.compile("(\\w+)(\\s+\\d+°C)?\\s*\\(?(\\d+)?\\)?\\s*°C?");
			// (\\w+): weather condition
			// (\\s+\\d+°C)?: white space, one or more digits, literal °C character, ? makes
			// it optional
			// \\s*: zero or more whitespace
			// \\(?: matches literal (, is optional
			// \\(\\d+)?: one or more digits, optional
			// \\)?: F
			// ("(\\w+)\\s+(\\d+°C)\\s*\\((\\d+°C)\\)\\s+↖\\s+(\\d+\\s+km/h)\\s+(\\d+%\\s+humidity)");
			Matcher matcher = pattern.matcher(textContent);

			if (matcher.find()) {
				String temperature = matcher.group(1) != null ? matcher.group(1).trim() : "N/A";
				return ("Temperature: " + temperature + " °C");
			} else {
				return ("Weather information not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ("Error fetching weather data.");
		}
	}

	/**
	 * A method to get the current time at a specified city
	 * @param city name of the city to get the time of
	 * @return the time in a String format
	 */
	public String getTime(String city) {
		String url = "http://worldtimeapi.org/api/timezone/America/" + city;

		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true).get();
			String json = doc.text();
			Pattern pattern = Pattern.compile("\"datetime\":\"(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2})");
			Matcher matcher = pattern.matcher(json);

			if (matcher.find()) {
				String date = matcher.group(1);
				String time = matcher.group(2);
				return "Date: " + date + ", Time: " + time;
			} else {
				return "Time information not found.";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Error fetching time data.";
		}
	}
}
