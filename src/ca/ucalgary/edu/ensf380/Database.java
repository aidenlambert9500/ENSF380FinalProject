/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

package ca.ucalgary.edu.ensf380;

import java.sql.*;
import java.util.ArrayList;


public class Database {
	private Connection dbConnection;
	private ResultSet results;
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ads";
	private static final String USER = "root";
	private static final String PASSWORD = "3015900";
	private ArrayList<String> adPaths = new ArrayList<String>();
	
	public Database() {
		
	}
	
	public void connect() {
		// try to connect to the database, catches any errors
		try {
			dbConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// method returns an ArrayList<String> of all the file paths of the ads in the database
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
	
	public ArrayList<Advertisements> getAds(){
		adPaths = getFilePaths();
		ArrayList<Advertisements> ads = new ArrayList<Advertisements>();
		for(String ad : adPaths) {
			ads.add(new Advertisements(ad));
		}
		return ads;
	}
}

