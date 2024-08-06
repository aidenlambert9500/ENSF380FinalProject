package ca.ucalgary.edu.ensf380;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class DatabaseTest {
    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        try {
            db.connect();
        } catch (Exception e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetFilePaths() {
        try {
            ArrayList<String> filePaths = db.getFilePaths();
            
            ArrayList<String> expectedFilePaths = new ArrayList<String>();
            expectedFilePaths.add("data/advertisements/LeBron-Tide-Ad.png");
            expectedFilePaths.add("data/advertisements/Barbie-Ad.jpg");
            expectedFilePaths.add("data/advertisements/Cheezit-Ad.png");
            expectedFilePaths.add("data/advertisements/CocaCola-Ad.jpg");
            expectedFilePaths.add("data/advertisements/Ford-Truck-Ad.jpg");
            expectedFilePaths.add("data/advertisements/McDonalds-Ad.png");
            expectedFilePaths.add("data/advertisements/Nike-Ad.png");
            expectedFilePaths.add("data/advertisements/Nissan-Ad.jpg");

            assertEquals(expectedFilePaths, filePaths);
        } catch (Exception e) {
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetAds() {
        try {
            ArrayList<Advertisements> ads = db.getAds();

            ArrayList<Advertisements> expectedAds = new ArrayList<Advertisements>();
            expectedAds.add(new Advertisements("data/advertisements/LeBron-Tide-Ad.png"));
            expectedAds.add(new Advertisements("data/advertisements/Barbie-Ad.jpg"));
            expectedAds.add(new Advertisements("data/advertisements/Cheezit-Ad.png"));
            expectedAds.add(new Advertisements("data/advertisements/CocaCola-Ad.jpg"));
            expectedAds.add(new Advertisements("data/advertisements/Ford-Truck-Ad.jpg"));
            expectedAds.add(new Advertisements("data/advertisements/McDonalds-Ad.png"));
            expectedAds.add(new Advertisements("data/advertisements/Nike-Ad.png"));
            expectedAds.add(new Advertisements("data/advertisements/Nissan-Ad.jpg"));

            assertEquals(expectedAds.size(), ads.size());
            for (int i = 0; i < ads.size(); i++) {
                assertEquals(expectedAds.get(i).getFilePath(), ads.get(i).getFilePath());
            }
        } catch (Exception e) {
            fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
