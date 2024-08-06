package ca.ucalgary.edu.ensf380;

/*
 * @author: Aiden Lambert, Jacelynn Doan, Wesley Lui 
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that retrieves weather information of a given city.
 */
public class WeatherParser {

    /**
     * A method to retrieve the weather information of a city 
     * @param city the name of a city to get weather for in string format
     * @return a string of the current weather in the city passed
     */
    public String getWeatherInfo(String city) {
        String url = "https://wttr.in/" + city;

        try {
            // Fetch the HTML from the URL
            Document doc = fetchDocumentFromUrl(url);

            // Extract the entire text content
            String textContent = doc.text();

            // Define a regex pattern to match the weather information
            Pattern pattern = Pattern.compile("(\\w+)(\\s+\\d+°C)?\\s*\\(?(\\d+)?\\)?\\s*°C?");
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
     * A method to retrieve the date and time at a current city
     * @param city the name of a city to get the time for in string format
     * @return a string array of the current date and time in the given city
     */
    public String[] getDateAndTime(String city) {
        String url = "http://worldtimeapi.org/api/timezone/America/" + city;

        try {
            Document doc = fetchJsonFromUrl(url);
            String json = doc.text();
            Pattern pattern = Pattern.compile("\"datetime\":\"(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2})");
            Matcher matcher = pattern.matcher(json);

            if (matcher.find()) {
                String date = matcher.group(1);
                String time = matcher.group(2);
                String[] info = new String[2];
                info[0] = date;
                info[1] = time;
                return info;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetches a document from the specified URL.
     * This method uses Jsoup to make a HTTP GET request to the provided URL
     * and parses the response as an HTML document.
     *
     * @param URL the URL to fetch the document from
     * @return the parsed HTML document
     * @throws IOException if an I/O error occurs while connecting to the URL or reading the response
     */
    protected Document fetchDocumentFromUrl(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    /**
     * Fetches a JSON document from the specified URL.
     * This method uses Jsoup to make a HTTP GET request to the provided URL
     * and parses the response as a JSON document. The content type is ignored
     * to allow Jsoup to process the response as plain text and convert it to a document.
     *
     * @param URL the URL to fetch the JSON document from
     * @return the parsed JSON document
     * @throws IOException if an I/O error occurs while connecting to the URL or reading the response
     */
    protected Document fetchJsonFromUrl(String url) throws IOException {
        return Jsoup.connect(url).ignoreContentType(true).get();
    }
}
