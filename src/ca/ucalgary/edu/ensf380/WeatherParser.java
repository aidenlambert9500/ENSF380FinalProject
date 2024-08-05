package ca.ucalgary.edu.ensf380;

/*
 * @author: Aiden Lambert, Jacelynn Doan, Wesley Lui 
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherParser {

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

    public String getTime(String city) {
        String url = "http://worldtimeapi.org/api/timezone/America/" + city;

        try {
            Document doc = fetchJsonFromUrl(url);
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

    // New methods to fetch Document, which can be overridden in tests
    protected Document fetchDocumentFromUrl(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    protected Document fetchJsonFromUrl(String url) throws IOException {
        return Jsoup.connect(url).ignoreContentType(true).get();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a city as a command line argument.");
            return;
        }

        String city = args[0];
        WeatherParser weatherParser = new WeatherParser();
        String temperature = weatherParser.getWeatherInfo(city);
        String time = weatherParser.getTime(city);
        System.out.println("City: " + city);
        System.out.println(temperature);
        System.out.println(time);
    }
}
