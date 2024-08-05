package ca.ucalgary.edu.ensf380;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StationTest {
    private Station station1;
    private Station station2;

    @Before
    public void setUp() {
        // Initialize test stations with some values from the populateStations method
        station1 = new Station("R01", "Maplewood Station", 8.756969333, 14.79016876);
        station2 = new Station("G05", "Old Town Station", 105.0085678, 365.235260);
    }

    @Test
    public void testGetStationCode() {
        // Test the getStationCode method for both stations
        assertEquals("R01", station1.getStationCode());
        assertEquals("G05", station2.getStationCode());
    }

    @Test
    public void testGetStationName() {
        // Test the getStationName method for both stations
        assertEquals("Maplewood Station", station1.getStationName());
        assertEquals("Old Town Station", station2.getStationName());
    }

    @Test
    public void testGetXCoord() {
        // Test the getXCoord method for both stations
        assertEquals(8.756969333, station1.getXCoord(), 0.001);
        assertEquals(105.0085678, station2.getXCoord(), 0.001);
    }

    @Test
    public void testGetYCoord() {
        // Test the getYCoord method for both stations
        assertEquals(14.79016876, station1.getYCoord(), 0.001);
        assertEquals(365.235260, station2.getYCoord(), 0.001);
    }

    @Test
    public void testStationConstructor() {
        // Test if the constructor correctly initializes the fields for both stations
        Station testStation1 = new Station("R02", "Lakeview Heights Station", 35.30510521, 38.3885107);
        Station testStation2 = new Station("G10", "Market Street Station", 263.0203094, 426.6099548);
        
        assertEquals("R02", testStation1.getStationCode());
        assertEquals("Lakeview Heights Station", testStation1.getStationName());
        assertEquals(35.30510521, testStation1.getXCoord(), 0.001);
        assertEquals(38.3885107, testStation1.getYCoord(), 0.001);
        
        assertEquals("G10", testStation2.getStationCode());
        assertEquals("Market Street Station", testStation2.getStationName());
        assertEquals(263.0203094, testStation2.getXCoord(), 0.001);
        assertEquals(426.6099548, testStation2.getYCoord(), 0.001);
    }
}
