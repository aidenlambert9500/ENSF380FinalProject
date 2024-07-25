package ca.ucalgary.edu.ensf380;

import com.google.gson.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class NewsAPI {
	private static String apiKey = "a4ee91a541d94cb9950c4f2f22cd2a8a";
	private static String ENDPOINT = "https://newsapi.org/v2/everything?";
	
	public static void main(String args[]) throws IOException {
		
		String query = "bitcoin"; // Example query

		// setup api for a GET request
		String apiURL = "https://newsapi.org/v2/everything?q=" + query + "&apiKey=" + apiKey;
		URL url = new URL(apiURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer " + apiKey);
		
		
		// gets the http response code from the api GET request
		int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        // convert StringBuilder response into a JSON object and initialize a parser to read from the JSON
        String jsonString = response.toString();
        JsonParser parser = new JsonParser();
        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
        
        // get the status from the json response 'status' key
        System.out.println(json.get("status").getAsString());
        
        // Print the API response
//        System.out.println("API Response:");
//        System.out.println(response.toString());
        

        
        
        // Disconnect the HttpURLConnection
        connection.disconnect();
    }
}
	

