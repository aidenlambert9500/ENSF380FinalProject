/*
 * @author Jacelynn Doan, Aiden Lambert, Wesley Lui
 */
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

public class SubwaySetup {
	private static List<Station> stationsList = new ArrayList<>();
	private static List<Line> lineList = new ArrayList<>();
	private static List<Train> trainList = new ArrayList<>();
	private static Line red, blue, green;

	public static void initializeSubwaySystem() {
		loadStationsFromFile("data\\subway.csv");
		initializeLines();
		assignStationsToLines();
		createTrains();
		updateTrainPositions("out");
	}

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

	private static void initializeLines() {
		red = new Line("Red");
		blue = new Line("Blue");
		green = new Line("Green");
	}

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