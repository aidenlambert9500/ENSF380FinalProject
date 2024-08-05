package ca.ucalgary.edu.ensf380;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class LineTest {
    private Line redLine;
    private Line blueLine;
    private Line greenLine;

    @Before
    public void setUp() {
        // Initialize Red Line with a few stations
        List<Station> redLineStations = new ArrayList<>();
        redLineStations.add(new Station("R01", "Maplewood Station", 8.756969333, 14.79016876));
        redLineStations.add(new Station("R02", "Lakeview Heights Station", 35.30510521, 38.3885107));
        redLineStations.add(new Station("R05", "Southland Plaza Station", 134.0755901, 62.26225471));
        redLineStations.add(new Station("R10", "Forestview Station", 294.5589409, 90.67517853));
        redLineStations.add(new Station("R20", "City Hall Station", 575.2224503, 252.9356918));
        redLine = new Line("Red Line", redLineStations);

        // Initialize Blue Line with a few stations
        List<Station> blueLineStations = new ArrayList<>();
        blueLineStations.add(new Station("B01", "Northside Station", 7.895084381, 669.8231659));
        blueLineStations.add(new Station("B04", "University Station", 72.78870201, 590.3655777));
        blueLineStations.add(new Station("B10", "Central Park Station", 250.2409515, 535.9384689));
        blueLineStations.add(new Station("B20", "Hillcrest Heights Station", 580.4266968, 499.3887024));
        blueLineStations.add(new Station("B30", "Hillside Station", 907.1087036, 460.3737488));
        blueLine = new Line("Blue Line", blueLineStations);

        // Initialize Green Line with a few stations
        List<Station> greenLineStations = new ArrayList<>();
        greenLineStations.add(new Station("G01", "Central Station", 9.34002018, 290.2851563));
        greenLineStations.add(new Station("G04", "West Central Station", 73.09390259, 353.8105164));
        greenLineStations.add(new Station("G10", "Market Street Station", 263.0203094, 426.6099548));
        greenLineStations.add(new Station("G20", "Eastside Station", 579.0446167, 549.3594971));
        greenLineStations.add(new Station("G30", "Zoo Station", 895.0680847, 672.1091003));
        greenLine = new Line("Green Line", greenLineStations);
    }

    @Test
    public void testGetLineName() {
        assertEquals("Red Line", redLine.getLineName());
        assertEquals("Blue Line", blueLine.getLineName());
        assertEquals("Green Line", greenLine.getLineName());
    }

    @Test
    public void testGetStations() {
        assertEquals(5, redLine.getStations().size());
        assertEquals(5, blueLine.getStations().size());
        assertEquals(5, greenLine.getStations().size());
    }

    @Test
    public void testGetStationByCode() {
        Station station = redLine.getStationByCode("R01");
        assertNotNull(station);
        assertEquals("Maplewood Station", station.getStationName());

        station = blueLine.getStationByCode("B10");
        assertNotNull(station);
        assertEquals("Central Park Station", station.getStationName());

        station = greenLine.getStationByCode("G30");
        assertNotNull(station);
        assertEquals("Zoo Station", station.getStationName());
    }
}
