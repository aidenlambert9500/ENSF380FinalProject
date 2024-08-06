package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A class that initializes and manages the subway system by loading station
 * data, creating lines and trains, and updating train positions from CSV files.
 */
public class SubwaySetup {

	private static ArrayList<Station> stationsList = new ArrayList<>();
	private static ArrayList<Line> lineList = new ArrayList<>();
	private static ArrayList<Train> trainList = new ArrayList<>();
	private static Line red, blue, green;

	/**
	 * Retrieves the list of all stations in the subway system.
	 * 
	 * @return a List of Station objects representing all stations
	 */
	public static List<Station> getStations() {
		return stationsList;
	}

	/**
	 * Retrieves the list of all trains in the subway system.
	 * 
	 * @return a List of Train objects representing all trains
	 */
	public static List<Train> getTrains() {
		return trainList;
	}

	/**
	 * Initializes the subway system by loading stations from a file, creating
	 * subway lines, assigning stations to lines, creating trains, and updating
	 * train positions.
	 */
	public static void initializeSubwaySystem() {
		loadStationsFromFile("data/subway.csv");
		initializeLines();
		assignStationsToLines();
		createTrains();
		updateTrainPositions("out");
	}

	/**
	 * Loads station data from a CSV file and adds the stations to the stations
	 * list.
	 * 
	 * @param filePath the path to the CSV file containing station data
	 */
	private static void loadStationsFromFile(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			reader.readLine(); // Skip header
			while ((line = reader.readLine()) != null) {
				String[] details = line.split(",");
				String stationCode = details[3];
				String stationName = details[4];
				double xCoord = Double.parseDouble(details[5]);
				double yCoord = Double.parseDouble(details[6]);
				Station station = new Station(stationCode, stationName, xCoord, yCoord);
				stationsList.add(station);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the subway lines by creating instances of the Red, Blue, and
	 * Green lines.
	 */
	private static void initializeLines() {
		red = new Line("Red");
		blue = new Line("Blue");
		green = new Line("Green");
	}

	/**
	 * Assigns stations to the appropriate subway lines based on their codes.
	 */
	private static void assignStationsToLines() {
		for (Station station : stationsList) {
			String lineInitial = station.getStationCode().substring(0, 1);
			switch (lineInitial) {
			case "R":
				red.addStation(station);
				break;
			case "B":
				blue.addStation(station);
				break;
			case "G":
				green.addStation(station);
				break;
			}
		}
	}

	/**
	 * Creates trains and assigns them to the subway lines. Each line receives a set
	 * of trains.
	 */
	private static void createTrains() {
		for (int i = 1; i <= 12; i++) {
			Line assignedLine;
			if (i <= 4) {
				assignedLine = red;
			} else if (i <= 8) {
				assignedLine = blue;
			} else {
				assignedLine = green;
			}
			Train train = new Train("T" + i, assignedLine, null);
			trainList.add(train);
		}

		lineList.add(red);
		lineList.add(blue);
		lineList.add(green);
	}

	/**
	 * Updates the positions of trains based on the latest CSV file in the specified
	 * directory.
	 * 
	 * @param directoryPath the path to the directory containing CSV files with
	 *                      train data
	 */
	private static void updateTrainPositions(String directoryPath) {
		try {
			Path dir = Paths.get(directoryPath);
			DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.csv");
			List<Path> csvFiles = new ArrayList<>();
			for (Path file : stream) {
				csvFiles.add(file);
			}

			Path latestCSV = csvFiles.stream().max(Comparator.comparingLong(file -> {
				try {
					return Files.getLastModifiedTime(file).toMillis();
				} catch (IOException e) {
					e.printStackTrace();
					return 0L;
				}
			})).orElse(null);

			if (latestCSV != null) {
				processCSVFile(latestCSV);
			} else {
				System.out.println("No CSV files found in the directory.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes a CSV file to update the positions and directions of trains.
	 * 
	 * @param csvFilePath the path to the CSV file containing train position data
	 */
	private static void processCSVFile(Path csvFilePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath.toFile()))) {
			String line;
			reader.readLine(); // Skip header
			int lineNum = 1;
			while ((line = reader.readLine()) != null) {
				lineNum++;
				String[] details = line.split(",");
				if (details.length < 4) {
					System.err.println("Invalid CSV format on line " + lineNum + ": " + line);
					continue;
				}

				String trainId = "T" + details[1];
				String stationCode = details[2];
				String direction = details[3];

				Train foundTrain = trainList.stream().filter(t -> t.getTrainNum().equals(trainId)).findFirst()
						.orElse(null);
				if (foundTrain != null) {
					foundTrain.setDirection(direction);
					Line currentLine = foundTrain.getCurrentLine();
					Station currentStation = currentLine.getStationByCode(stationCode);
					foundTrain.setCurrentStation(currentStation);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}