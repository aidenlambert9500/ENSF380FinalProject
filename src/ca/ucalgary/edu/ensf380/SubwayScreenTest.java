package ca.ucalgary.edu.ensf380;

import org.junit.*;
import static org.junit.Assert.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.File;

public class SubwayScreenTest {

    private SubwayScreen screen;
    private String[] args = {"Calgary", "1"};

    @Before
    public void setUp() {
        screen = new SubwayScreen(args);
    }

    @Test
    public void testConstructor() {
        assertNotNull("SubwayScreen instance should not be null", screen);
    }

    @Test
    public void testWeatherLabel() {
        assertNotNull("Weather label should not be null", screen.getWeatherLabel());
    }

    @Test
    public void testTimeLabel() {
        assertNotNull("Time label should not be null", screen.getTimeLabel());
    }

    @Test
    public void testCityLabel() {
        assertNotNull("City label should not be null", screen.getCityLabel());
    }

    @Test
    public void testNewsLabel() {
        assertNotNull("News label should not be null", screen.getNewsLabel());
    }

    @Test
    public void testAdPanel() {
        assertNotNull("Ad panel should not be null", screen.getAdPanel());
    }

    @Test
    public void testAdPaths() {
        assertNotNull("Ad paths list should not be null", screen.getAdPaths());
    }

    @Test
    public void testUpdateAdPanel() {
        screen.updateAdPanel(0);
        assertNotNull("Image label should not be null after updating ad panel", screen.getImageLabel());
    }

    @Test
    public void testDrawTrainPositionsOnMap() {
        screen.drawTrainPositionsOnMap();
        assertNotNull("Image label should not be null after drawing train positions", screen.getImageLabel());
    }

    @Test
    public void testPlayStationAnnouncement() {
        SubwayScreen screen = new SubwayScreen(new String[]{"Calgary", "1"});
        String stationName = "Maplewood Station"; // Make sure this file exists in data/stationAudio/ExampleStation.mp3

        File audioFile = new File("data/stationAudio/" + stationName + ".mp3");
        assertTrue("Audio file should exist or have been attempted to play", audioFile.exists());
        
        // Assuming that the method is invoked during construction or some other method
        screen.playStationAnnouncement(stationName);
    }
}

