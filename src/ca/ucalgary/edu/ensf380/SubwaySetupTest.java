package ca.ucalgary.edu.ensf380;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class SubwaySetupTest {

    @Before
    public void setUp() {
        SubwaySetup.initializeSubwaySystem();
    }

    @Test
    public void testGetStations() {
        List<Station> stations = SubwaySetup.getStations();
        assertNotNull("Stations list should not be null", stations);
        assertTrue("Stations list should not be empty", stations.size() > 0);
    }

    @Test
    public void testGetTrains() {
        List<Train> trains = SubwaySetup.getTrains();
        assertNotNull("Trains list should not be null", trains);
        assertTrue("Trains list should not be empty", trains.size() > 0);
    }

    @Test
    public void testInitializeSubwaySystem() {
        List<Station> stations = SubwaySetup.getStations();
        List<Train> trains = SubwaySetup.getTrains();
        assertNotNull("Stations list should not be null", stations);
        assertNotNull("Trains list should not be null", trains);
    }
}
