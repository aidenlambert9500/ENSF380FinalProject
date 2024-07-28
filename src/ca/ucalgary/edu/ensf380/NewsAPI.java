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

public class NewsAPI {
	// Member variables
	private static String apiKey = "a4ee91a541d94cb9950c4f2f22cd2a8a";
	private static String ENDPOINT = "https://newsapi.org/v2/";
	private ArrayList<String> news;

	// default constructor
	public NewsAPI() {
		this.news = new ArrayList<>();
	}

	public ArrayList<String> getNews() {
		return this.news;
	}

	// function takes a string parameter and adds 5 news titles containing that
	// string to a NewsAPI object
	public void fetchNewsByKeyword(String keyword) throws IOException {
		String apiURL = ENDPOINT + "everything?q=" + keyword + "&apiKey=" + apiKey;
		fetchNewsByUrl(apiURL);
	}

	// function takes no parameters and adds 5 news titles relating to World News to
	// a NewsAPI object
	public void fetchNews() throws IOException {
		String apiURL = ENDPOINT + "top-headlines?country=us" + "&apiKey=" + apiKey;
		fetchNewsByUrl(apiURL);
	}

	// General news fetcher method
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

	public static void main(String args[]) throws IOException {
		// test to print 5 news articles using keyword lebron
		String query = "lebron"; // Example query
		NewsAPI news = new NewsAPI();
		news.fetchNewsByKeyword(query);
		int i = 0;
		for (String title : news.getNews()) {
			i++;
			// System.out.println(i + ". " + title);
		}

		// test to print 5 breaking news articles
		NewsAPI newsNoKey = new NewsAPI();
		newsNoKey.fetchNews();
		int j = 0;
		for (String title : newsNoKey.getNews()) {
			j++;
			// System.out.println(j + ". " + title);
		}
	}
}
