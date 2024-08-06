package ca.ucalgary.edu.ensf380;

/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

/**
 * A class to store the filepath of each advertisement from the database
 */
public class Advertisements {
    private String filePath;
    
    /**
     * A default constructor
     * @param filePath a string for each filePath for each specific advertisment
     */
    public Advertisements(String filePath) {
        this.filePath = filePath;   
    }

    /**
     * A method that returns the filepath as a string
     * @return the filepath in string format
     */
    public String getFilePath() {
    	return this.filePath;
    }
    
}
