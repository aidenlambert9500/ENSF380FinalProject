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
	// Member variables
	private static Line red;
	private static Line blue;
	private static Line green;
	private static List<Train> trains = new ArrayList<>();

	public static Line getRedLine() {
		return red;
	}

	public static Line getBlueLine() {
		return blue;
	}

	public static Line getGreenLine() {
		return green;
	}

	public static List<Train> getTrains() {
		return trains;
	}

	public static void getSubwaySystem() {
		List<Station> stations = new ArrayList<>();
		List<Line> lines = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("data\\subway.csv"))) {
			String line;
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String stationCode = parts[3];
				String stationName = parts[4];
				double x = Double.parseDouble(parts[5]);
				double y = Double.parseDouble(parts[6]);
				Station station = new Station(stationCode, stationName, x, y);
				stations.add(station);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		red = new Line("R");
		blue = new Line("B");
		green = new Line("G");

		for (Station station : stations) {
			String lineName = station.getStationCode().substring(0, 1);
			if (lineName.equals("R")) {
				red.addStation(station);
			} else if (lineName.equals("B")) {
				blue.addStation(station);
			} else if (lineName.equals("G")) {
				green.addStation(station);
			}
		}

		for (int i = 1; i <= 12; i++) {
			Line line;
			if (i <= 4) {
				line = red;
			} else if (i <= 8) {
				line = blue;
			} else {
				line = green;
			}
			Train train = new Train("T" + i, line, null);
			trains.add(train);
		}

		lines.add(red);
		lines.add(blue);
		lines.add(green);

		String folderPath = "out";
		updateTrainsFromCSV(folderPath, trains);
	}

	private static void updateTrainsFromCSV(String csvFilePath, List<Train> trains) {
		try {
			Path folder = Paths.get(csvFilePath);
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder, "*.csv");
			List<Path> csvFiles = new ArrayList<>();
			for (Path file : directoryStream) {
				csvFiles.add(file);
			}

			Path mostRecentCSV = csvFiles.stream().max(Comparator.comparingLong(file -> {
				try {
					return Files.getLastModifiedTime(file).toMillis();
				} catch (IOException e) {
					e.printStackTrace();
					return 0L;
				}
			})).orElse(null);

			if (mostRecentCSV != null) {
				try (BufferedReader reader = new BufferedReader(new FileReader(mostRecentCSV.toFile()))) {
					String line;
					reader.readLine();
					int lineNumber = 1;
					while ((line = reader.readLine()) != null) {
						lineNumber++;

						String[] parts = line.split(",");
						if (parts.length < 4) {
							System.err.println("Invalid CSV format on line " + lineNumber + ": " + line);
							continue;
						}

						String trainNum = parts[1];
						String stationCode = parts[2];
						String direction = parts[3];
						String trainNumber = "T" + trainNum;

						Train matchingTrain = null;
						for (Train train : trains) {
							if (train.getTrainNum().equals(trainNumber)) {
								matchingTrain = train;
								break;
							}
						}

						if (matchingTrain != null) {
							matchingTrain.setDirection(direction);

							Line line1 = matchingTrain.getCurrentLine();
							Station currentStation = line1.getStationByCode(stationCode);

							matchingTrain.setCurrentStation(currentStation);
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("No CSV files found in the folder.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
