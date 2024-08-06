
package ca.ucalgary.edu.ensf380;
/*
 * @author Jacelynn Doan, Aiden Lambert, Wesley Lui
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A class that represents a train route. It contains the trains stations and trains on the route.
 */
public class Line {
    private final String lineName;
    private final List<Train> trains;
    private final List<Station> stations;


    /**
     * A constructor that creates a Line object 
     * @param lineName a string of the name of the route
     * @param stations a List of Stations on that route
     * @param trains a List of Trains on that route
     */
    public Line(String lineName, List<Station> stations, List<Train> trains) {
        this.lineName = lineName;
        this.stations = Collections.unmodifiableList(stations);
        this.trains = Collections.unmodifiableList(trains);
    }

    
    /**
     * A constructor that creates a Line object with only stations and the line name
     * @param lineName a string of the name of the route
     * @param stations a List of Stations on that route
     */
    public Line(String lineName, List<Station> stations) {
        this.lineName = lineName;
        this.stations = Collections.unmodifiableList(stations);
        this.trains = null;
    }


    /**
     * A constructor that creates a Line object with only a line name
     * @param lineName a string of the name of the route
     */
    public Line(String lineName) {
        this.lineName = lineName;
        this.trains = null;
        this.stations = new ArrayList<>();
    }


    /**
     * A method get the line name
     * @return the line name in string format
     */
    public String getLineName() {
        return lineName;
    }


    /**
     * A method to get a list of Stations 
     * @return a list of stations on a route
     */
    public List<Station> getStations() {
        return stations;
    }

 
    /**
     * A method to get a list of Trains
     * @return a list of Trains on a route
     */
    public List<Train> getTrains() {
        return trains;
    }


    /**
     * A method to add a station to a line object
     * @param station a Station object being added to a route
     */
    public void addStation(Station station) {
        this.stations.add(station);
    }


    /**
     * A method to get a specific Station by its unique code
     * @param stationCode a code in string format of the station being searched for
     * @return a Station with the same code passed to the method
     */
    public Station getStationByCode(String stationCode) {
        Station matchingTrainStation = null;
        for (Station station : stations) {
            if (station.getStationCode().equals(stationCode)) {
                matchingTrainStation = station;
                break;
            }
        }
        return matchingTrainStation;
    }
}

	