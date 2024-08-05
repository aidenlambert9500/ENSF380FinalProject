package ca.ucalgary.edu.ensf380;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TrainTest {

    private Train train1;
    private Train train2;
    private Train train3;
    private Line lineR;
    private Line lineB;
    private Line lineG;
    private Station stationR01;
    private Station stationB10;
    private Station stationG20;

    @Before
    public void setUp() {
        // Initialize Line objects
        lineR = new Line("R");
        lineB = new Line("B");
        lineG = new Line("G");

        // Initialize Station objects
        stationR01 = new Station("R01", "Maplewood Station", 8.756969333, 14.79016876);
        stationB10 = new Station("B10", "Central Park Station", 250.2409515, 535.9384689);
        stationG20 = new Station("G20", "Eastside Station", 579.0446167, 549.3594971);

        // Initialize Train objects
        train1 = new Train("T1", lineR, "North");
        train2 = new Train("T2", lineB, "South");
        train3 = new Train("T3", lineG, "East");
    }

    @Test
    public void testConstructor() {
        assertEquals("T1", train1.getTrainNum());
        assertEquals(lineR, train1.getCurrentLine());
        assertEquals("North", train1.getDirection());
        assertNull(train1.getCurrentStation());
        
        assertEquals("T2", train2.getTrainNum());
        assertEquals(lineB, train2.getCurrentLine());
        assertEquals("South", train2.getDirection());
        assertNull(train2.getCurrentStation());
        
        assertEquals("T3", train3.getTrainNum());
        assertEquals(lineG, train3.getCurrentLine());
        assertEquals("East", train3.getDirection());
        assertNull(train3.getCurrentStation());
    }

    @Test
    public void testSetDirection() {
        train1.setDirection("South");
        assertEquals("South", train1.getDirection());
        
        train2.setDirection("North");
        assertEquals("North", train2.getDirection());
        
        train3.setDirection("West");
        assertEquals("West", train3.getDirection());
    }

    @Test
    public void testSetCurrentStation() {
        train1.setCurrentStation(stationR01);
        assertEquals(stationR01, train1.getCurrentStation());
        
        train2.setCurrentStation(stationB10);
        assertEquals(stationB10, train2.getCurrentStation());
        
        train3.setCurrentStation(stationG20);
        assertEquals(stationG20, train3.getCurrentStation());
    }

    @Test
    public void testGetCurrentStationCode() {
        train1.setCurrentStation(stationR01);
        assertEquals("R01", train1.getCurrentStationCode());
        
        train2.setCurrentStation(stationB10);
        assertEquals("B10", train2.getCurrentStationCode());
        
        train3.setCurrentStation(stationG20);
        assertEquals("G20", train3.getCurrentStationCode());
    }

    @Test
    public void testGetCurrentStationCodeWhenStationIsNull() {
        assertNull(train1.getCurrentStationCode());
        assertNull(train2.getCurrentStationCode());
        assertNull(train3.getCurrentStationCode());
    }
}
