package ca.ucalgary.edu.ensf380;

import org.junit.Test;
import static org.junit.Assert.*;

public class AdvertisementsTest {

    @Test
    public void testGetFilePath() {
        String filePath = "path/to/ads.txt";
        Advertisements ads = new Advertisements(filePath);
        assertEquals("path/to/ads.txt", ads.getFilePath());
    }
    
    @Test
    public void testGetFilePathWithDifferentPath() {
        String filePath = "another/path/to/ads.txt";
        Advertisements ads = new Advertisements(filePath);
        assertEquals("another/path/to/ads.txt", ads.getFilePath());
    }
    
    @Test
    public void testGetFilePathWithEmptyPath() {
        String filePath = "";
        Advertisements ads = new Advertisements(filePath);
        assertEquals("", ads.getFilePath());
    }
    
    @Test
    public void testGetFilePathWithNullPath() {
        String filePath = null;
        Advertisements ads = new Advertisements(filePath);
        assertNull(ads.getFilePath());
    }
}
