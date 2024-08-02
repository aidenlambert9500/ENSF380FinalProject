package ca.ucalgary.edu.ensf380;

/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A class to call an API to retrieve news articles
 */
public class NewsAPI {
	// Member variables
	private static String apiKey = "a4ee91a541d94cb9950c4f2f22cd2a8a";
	private static String ENDPOINT = "https://newsapi.org/v2/";
	private ArrayList<String> news;

	
	/**
	 * This constructor creates a new NewsAPI object and initializes memory for an ArrayList of strings for the news titles 
	 */
	public NewsAPI() {
		this.news = new ArrayList<>();
	}

	/**
	 * This function gets the news from a NewsAPI object
	 * @return an ArrayList of string of all of the news titles
	 */
	public ArrayList<String> getNews() {
		return this.news;
	}

	/**
	 * This function takes a string parameter and calls fetchNewsByUrl with a specific search statement
	 * @param keyword a word the API searches articles containing
	 * @throws IOException throws errors if there are issues connecting to the API
	 */
	public void fetchNewsByKeyword(String keyword) throws IOException {
		String apiURL = ENDPOINT + "everything?q=" + keyword + "&apiKey=" + apiKey;
		fetchNewsByUrl(apiURL);
	}
	
	/**
	 * This function takes no parameters and calls fetchNewsByUrl with a general search statement
	 * @throws IOException throws errors if there are issues connecting to the API
	 */
	public void fetchNews() throws IOException {
		String apiURL = ENDPOINT + "top-headlines?country=us" + "&apiKey=" + apiKey;
		fetchNewsByUrl(apiURL);
	}

	// General news fetcher method
	/**
	 * This function takes a URL input and returns an ArrayList of Strings of news article titles
	 * @param apiURL a url passed by fetchNews or fetchNewsByKeyword
	 * @return an ArrayList of strings of news article titles
	 * @throws IOException throws errors if there are issues connecting to the API
	 */
	public ArrayList<String> fetchNewsByUrl(String apiURL) throws IOException {
		URL url = new URL(apiURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		// read the API response and build a string from it
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// convert StringBuilder response into a JSON object and initialize a parser to
		// read from the JSON
		String jsonString = response.toString();
		JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
		JsonArray articles = json.getAsJsonArray("articles");

		// For loop that extracts the title from each article
		for (int i = 0; i < 5; i++) {
			JsonObject article = articles.get(i).getAsJsonObject();
			String title = article.get("title").getAsString();
			news.add(title); //
		}

		// Disconnect the HttpURLConnection
		connection.disconnect();

		return news;
	}

}
