package ca.ucalgary.edu.ensf380;

/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

import java.sql.*;
import java.util.ArrayList;

/**
 * A class to link to an SQL database and retrieve String filepaths
 */
public class Database {
	private Connection dbConnection;
	private ResultSet results;
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ads";
	private static final String USER = "root";
	private static final String PASSWORD = "30195900";
	private ArrayList<String> adPaths = new ArrayList<String>();
	
	/**
	 * Constructor to initialize a new Database object
	 */
	public Database() {
		
	}
	
	/**
	 * A method to connect to the database using a url, username, and password
	 */
	public void connect() {
		// try to connect to the database, catches any errors
		try {
			dbConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A method to get the filepaths from the advertisements SQL table
	 * @return an ArrayList of Strings containing all the filepaths present in the database table
	 */
	public ArrayList<String> getFilePaths() {
		String query = "Select * from advertisements";
		// try to execute the query and throws any potential errors
		try {
			Statement myStatement = dbConnection.createStatement();
			results = myStatement.executeQuery(query);
			// runs until file paths of all ads have been added to adPaths
			while(results.next()) {
				adPaths.add(results.getString("file_path"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adPaths;
	}
	
	/**
	 * A method that calls getFilePaths() and uses its return value to initialize an ArrayList of Advertisement objects
	 * @return an ArrayList of Advertisement objects 
	 */
	public ArrayList<Advertisements> getAds(){
		adPaths = getFilePaths();
		ArrayList<Advertisements> ads = new ArrayList<Advertisements>();
		for(String ad : adPaths) {
			ads.add(new Advertisements(ad));
		}
		return ads;
	}
}

