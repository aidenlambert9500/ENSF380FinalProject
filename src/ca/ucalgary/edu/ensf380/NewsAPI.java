/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

package ca.ucalgary.edu.ensf380;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A class to fetch news articles from the internet via an API
 */
public class NewsAPI {
	// Member variables
	private static String apiKey = "a4ee91a541d94cb9950c4f2f22cd2a8a";
	private static String ENDPOINT = "https://newsapi.org/v2/";
	private ArrayList<String> news;

	
	/**
	 * Default constructor that initializes a new ArrayList for the news articles
	 */
	public NewsAPI() {
		this.news = new ArrayList<>();
	}

	/**
	 * A method to get the news articles 
	 * @return an ArrayList of strings of news article titles
	 */
	public ArrayList<String> getNews() {
		return this.news;
	}

	
	/**
	 * A method that formats a url with a keyword and calls fetchNewsByUrl with that url.
	 * @param keyword a string that the user wishes to search the internet for news articles relating to that word
	 * @throws IOException throws an exception if there is any error connecting to the api
	 */
	public void fetchNewsByKeyword(String keyword) throws IOException {
		String apiURL = ENDPOINT + "everything?q=" + keyword + "&apiKey=" + apiKey;
		fetchNewsByUrl(apiURL);
	}

	
	/**
	 * A method that formats a url to find general world news by calling fetchNewsByUrl with a generic search term
	 * @throws IOException throws an exception if there is any error connecting to the api
	 */
	public void fetchNews() throws IOException {
		String apiURL = ENDPOINT + "top-headlines?country=us" + "&apiKey=" + apiKey;
		fetchNewsByUrl(apiURL);
	}

	
	/**
	 * A method that connects to the api and uses a url to search for news articles. It then parses over the returned JSON to create an ArrayList of strings.
	 * @param apiURL the url to connect to the api
	 * @return an ArrayList of strings of news article titles
	 * @throws IOException throws an exception if there is any error connecting to the api
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
//			System.out.println(title);
		}

		// Disconnect the HttpURLConnection
		connection.disconnect();

		return news;
	}
}
