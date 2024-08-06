/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

package ca.ucalgary.edu.ensf380;

import java.sql.*;
import java.util.ArrayList;


/**
 * A class to connect to a MySQL database to retrieve filepaths to different media types
 */
public class Database {
	private Connection dbConnection;
	private ResultSet results;
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ads";
	private static final String USER = "root";
	private static final String PASSWORD = "30195900";
	private ArrayList<String> adPaths = new ArrayList<String>();
	
	
	/**
	 * A method to connect to the database using a predefined url, password, and username
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
	 * A method to get the filePaths of all the media present in the database
	 * @return an ArrayList of strings containing all the filepaths 
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
	 * A method that calls getFilePaths and creates an Advertisement object for each string that it returns
	 * @return an ArrayList of Advertisements where each Advertisement contains a filepath string
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

