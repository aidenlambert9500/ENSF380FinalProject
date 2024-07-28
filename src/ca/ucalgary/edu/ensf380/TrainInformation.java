package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrainInformation {

	public static void main(String[] args) {
		// Runs the simulator
		Process process = null;
		try {
			String[] command = { "java", "-jar", "./exe/SubwaySimulator.jar", "--in", "./data/subway.csv", "--out",
					"./out" };
			process = new ProcessBuilder(command).start();
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}

		// Ensure the process is not null
		if (process == null) {
			System.err.println("Failed to start the simulator process.");
			return;
		}

		final Process finalProcess = process;

		// Keep the application alive for 5 minutes
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (finalProcess != null) {
					finalProcess.destroy();
				}
				timer.cancel();
			}
		}, 5 * 60 * 1000); // 5 minutes in milliseconds

		// Schedule a task to destroy the process after 5 minutes
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(() -> {
			if (finalProcess.isAlive()) {
				finalProcess.destroy();
			}
		}, 5, TimeUnit.MINUTES);

		// Read the first train's position
		String firstTrainPosition = readFirstTrainPosition(finalProcess);
		if (firstTrainPosition != null) {
			System.out.println("First Train Position: " + firstTrainPosition);
		} else {
			System.out.println("No train position found.");
		}

		// Shutdown the executor service
		executorService.shutdown();

		// Wait for the process to finish
		try {
			finalProcess.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String readFirstTrainPosition(Process process) {
		InputStream inputStream = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println("Reading line: " + line); // Log the line being read
				if (line.startsWith("Train positions:")) {
					// Read the next line which contains the train positions
					line = reader.readLine();
					System.out.println("Train positions line: " + line); // Log the positions line
					if (line != null) {
						// Extract and return the first train's position
						String[] parts = line.split(", ");
						if (parts.length > 0) {
							String firstTrain = parts[0];
							return extractTrainInfo(firstTrain);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String extractTrainInfo(String trainInfo) {
		// Example train info: "T1(R25, F)"
		int startIndex = trainInfo.indexOf('(') + 1;
		int endIndex = trainInfo.indexOf(')');
		if (startIndex > 0 && endIndex > startIndex) {
			String info = trainInfo.substring(startIndex, endIndex);
			return info; // This will be "R25, F" or similar
		}
		return null;
	}
}
