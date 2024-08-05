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
        assertNotNull("Weather label should not be null", screen.weatherLabel);
    }

    @Test
    public void testTimeLabel() {
        assertNotNull("Time label should not be null", screen.timeLabel);
    }

    @Test
    public void testCityLabel() {
        assertNotNull("City label should not be null", screen.cityLabel);
    }

    @Test
    public void testNewsLabel() {
        assertNotNull("News label should not be null", screen.newsLabel);
    }

    @Test
    public void testAdPanel() {
        assertNotNull("Ad panel should not be null", screen.adPanel);
    }

    @Test
    public void testAdPaths() {
        assertNotNull("Ad paths list should not be null", screen.adPaths);
    }

    @Test
    public void testUpdateAdPanel() {
        screen.updateAdPanel(0);
        assertNotNull("Image label should not be null after updating ad panel", screen.imageLabel);
    }

    @Test
    public void testDrawTrainPositionsOnMap() {
        screen.drawTrainPositionsOnMap();
        assertNotNull("Image label should not be null after drawing train positions", screen.imageLabel);
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

