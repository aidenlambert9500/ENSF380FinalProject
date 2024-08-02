package ca.ucalgary.edu.ensf380;

/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

/**
 * A class for media objects (.png, .pdf, .jpg) filepaths 
 */
public class Advertisements {
    private String filePath;
    
    /**
     * This constructor creates a new Advertisement object
     * @param filePath a string of the filepath to the Advertisement media
     */
    public Advertisements(String filePath) {
        this.filePath = filePath;   
    }

    /**
     * This function returns the filePath of a specific Advertisement object
     * @return a string of the filepath to the specific Advertisement media
     */
    public String getFilePath() {
    	return this.filePath;
    }
    
}
