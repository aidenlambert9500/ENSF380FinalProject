package ca.ucalgary.edu.ensf380;

import org.junit.Test;
import static org.junit.Assert.*;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.io.IOException;

public class WeatherParserTest {

    private class TestableWeatherParser extends WeatherParser {
        private String htmlContent;
        private String jsonContent;

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }

        public void setJsonContent(String jsonContent) {
            this.jsonContent = jsonContent;
        }

        @Override
        protected Document fetchDocumentFromUrl(String url) throws IOException {
            return Jsoup.parse(htmlContent);
        }

        @Override
        protected Document fetchJsonFromUrl(String url) throws IOException {
            return Jsoup.parse(jsonContent);
        }
    }

    @Test
    public void testGetWeatherInfo() {
        TestableWeatherParser parser = new TestableWeatherParser();
        parser.setHtmlContent("Calgary: ☀ 25°C");

        String expected = "Temperature: 25 °C";
        String actual = parser.getWeatherInfo("Calgary");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetWeatherInfoNoMatch() {
        TestableWeatherParser parser = new TestableWeatherParser();
        parser.setHtmlContent("No weather information");

        String expected = "Weather information not found.";
        String actual = parser.getWeatherInfo("Nowhere");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetWeatherInfoError() {
        TestableWeatherParser parser = new TestableWeatherParser() {
            @Override
            protected Document fetchDocumentFromUrl(String url) throws IOException {
                throw new IOException();
            }
        };

        String expected = "Error fetching weather data.";
        String actual = parser.getWeatherInfo("ErrorCity");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetTime() {
        TestableWeatherParser parser = new TestableWeatherParser();
        parser.setJsonContent("{\"datetime\":\"2024-08-05T12:34:00.000Z\"}");

        String expected = "Date: 2024-08-05, Time: 12:34";
        String actual = parser.getTime("Calgary");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeNoMatch() {
        TestableWeatherParser parser = new TestableWeatherParser();
        parser.setJsonContent("{}");

        String expected = "Time information not found.";
        String actual = parser.getTime("Nowhere");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetTimeError() {
        TestableWeatherParser parser = new TestableWeatherParser() {
            @Override
            protected Document fetchJsonFromUrl(String url) throws IOException {
                throw new IOException();
            }
        };

        String expected = "Error fetching time data.";
        String actual = parser.getTime("ErrorCity");

        assertEquals(expected, actual);
    }
}
