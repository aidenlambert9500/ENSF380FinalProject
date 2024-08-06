package ca.ucalgary.edu.ensf380;

/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

/**
 * A class to store the file path of each advertisement from the database
 */
public class Advertisements {
    private String filePath;
    
    /**
     * A default constructor
     * @param filePath a string for each filePath for each specific advertisement
     */
    public Advertisements(String filePath) {
        this.filePath = filePath;   
    }

    /**
     * A method that returns the file path as a string
     * @return the file path in string format
     */
    public String getFilePath() {
    	return this.filePath;
    }
    
}
