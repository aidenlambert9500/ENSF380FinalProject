package ca.ucalgary.edu.ensf380;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Line {
    private final String lineName;
    private final List<Train> trains;
    private final List<Station> stations;


    public Line(String lineName, List<Station> stations, List<Train> trains) {
        this.lineName = lineName;
        this.stations = Collections.unmodifiableList(stations);
        this.trains = Collections.unmodifiableList(trains);
    }

    
    public Line(String lineName, List<Station> stations) {
        this.lineName = lineName;
        this.stations = Collections.unmodifiableList(stations);
        this.trains = null;
    }


    public Line(String lineName) {
        this.lineName = lineName;
        this.trains = null;
        this.stations = new ArrayList<>();
    }


    public String getLineName() {
        return lineName;
    }


    public List<Station> getStations() {
        return stations;
    }

 
    public List<Train> getTrains() {
        return trains;
    }


    public void addStation(Station station) {
        this.stations.add(station);
    }


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

	