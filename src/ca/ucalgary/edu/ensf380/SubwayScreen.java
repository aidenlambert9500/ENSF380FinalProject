package ca.ucalgary.edu.ensf380;

/*
 * @author: Aiden Lambert, Jacelynn Doan, Wesley Lui 
 */

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

//import java.util.TimerTask;
import javax.swing.Timer;
import java.util.ArrayList;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SubwayScreen extends JFrame {
	// Member Variables
	private JLabel weatherLabel, timeLabel, cityLabel, newsLabel, imageLabel;
	private JPanel adPanel;
	private String[] args;
	private List<Station> stations;
	private int currentStationIndex = 1; // Starting from "Lakeview Heights Station"
	private Timer trainTimer, newsTimer, adTimer, weatherTimer, stationChangeTimer;
	private int newsIndex = 0;
	private ArrayList<String> newsList;
	private Database db = new Database();
	private ArrayList<Advertisements> ads;
	private ArrayList<String> adPaths = new ArrayList<String>();
	private int adCounter = 0, mapCounter = 0;
	private final String MAP_PATH = "data/Trains.png";
	private List<Train> trains;
	private Train currentTrain;
	private Station currentStation;

	public SubwayScreen(String[] args) {
		// Constructor (launches gui)

		this.args = args;
		SubwaySetup.initializeSubwaySystem(); // Run simulator and populate all lines and trains
		trains = SubwaySetup.getTrains();
		stations = SubwaySetup.getStations(); // Initialize the stations list
		if (trains != null && !trains.isEmpty()) {
			Random rand = new Random();
			currentTrain = trains.get(rand.nextInt(trains.size())); // select random train from the twelve for next
			// stations. only required to run once. index 0 to 11; 12 trains
		}

		// Connect to the database
//		db.connect();

		// Get ad paths from database
//		ads = db.getAds();
//		for (Advertisements ad : ads) {
//			adPaths.add(ad.getFilePath());
//		}

		// Initialize the frame with a title and layout
		setTitle("SubwayScreen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new GridBagLayout());

		// Create a section for the advertisements and big map
		adPanel = new JPanel();
		adPanel.setBackground(Color.LIGHT_GRAY);
		adPanel.add(new JLabel("Advertisements") {
			{
				setFont(new Font("Serif", Font.BOLD, 20));
			}
		});
		adPanel.setPreferredSize(new Dimension(500, 300));

		// Create a section for the weather and time reports
		JPanel weatherPanel = new JPanel();
		weatherPanel.setBackground(Color.white);
		weatherPanel.add(new JLabel("Weather") {
			{
				setFont(new Font("Serif", Font.BOLD, 20));
			}
		});
		weatherLabel = new JLabel("Temperature:");
		setFixedSize(weatherLabel, 120, 30);
		weatherLabel.setVerticalAlignment(JLabel.BOTTOM);
		timeLabel = new JLabel("Time: ");
		setFixedSize(timeLabel, 120, 30);
		cityLabel = new JLabel("CITY");
		setFixedSize(cityLabel, 120, 30);

		weatherPanel.add(weatherLabel);
		weatherPanel.add(timeLabel);
		weatherPanel.add(cityLabel);
		weatherPanel.setPreferredSize(new Dimension(150, 300));

		// Create a section for the news
		JPanel newsPanel = new JPanel();
		newsPanel.setBackground(Color.cyan);
		newsPanel.add(new JLabel("News") {
			{
				setFont(new Font("Serif", Font.BOLD, 20));
			}
		});
		newsPanel.setPreferredSize(new Dimension(700, 60));
		newsLabel = new JLabel();
		newsLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size and type for news
		setFixedSize(newsLabel, 120, 30);
		newsPanel.add(newsLabel);

		// Create a section for the trains
		JPanel trainPanel = new JPanel();
		trainPanel.setBackground(Color.white);
		trainPanel.setLayout(new BorderLayout());
		JLabel nextStationLabel = new JLabel("Next: ", JLabel.CENTER) {
			{
				setFont(new Font("Serif", Font.BOLD, 20));
			}
		};
		trainPanel.add(nextStationLabel, BorderLayout.SOUTH);
		addTrainInformation(trainPanel); // Add train information to the panel
		trainPanel.setPreferredSize(new Dimension(700, 60));

		// Set the dimensions of the adPanel and move it to the top left corner
		GridBagConstraints adsGBC = new GridBagConstraints();
		adsGBC.gridx = 0;
		adsGBC.gridy = 0;
		adsGBC.gridheight = 1;
		adsGBC.gridwidth = 1;
		adsGBC.weightx = 0.7;
		adsGBC.weighty = 0.6;
		adsGBC.anchor = GridBagConstraints.NORTHWEST;
		adsGBC.fill = GridBagConstraints.BOTH;

		// Set the dimensions of the weatherPanel and move it to the top right corner
		GridBagConstraints weatherGBC = new GridBagConstraints();
		weatherGBC.gridx = 1;
		weatherGBC.gridy = 0;
		weatherGBC.gridheight = 1;
		weatherGBC.gridwidth = 1;
		weatherGBC.weightx = 0.3;
		weatherGBC.weighty = 0.6;
		weatherGBC.anchor = GridBagConstraints.NORTHWEST;
		weatherGBC.fill = GridBagConstraints.BOTH;

		// Set the dimensions of the newsPanel and place it across the bottom of the
		// adPanel and weatherPanel
		GridBagConstraints newsGBC = new GridBagConstraints();
		newsGBC.gridx = 0;
		newsGBC.gridy = 1;
		newsGBC.gridheight = 1;
		newsGBC.gridwidth = 2;
		newsGBC.weightx = 1.0;
		newsGBC.weighty = 0.2;
		newsGBC.anchor = GridBagConstraints.NORTHWEST;
		newsGBC.fill = GridBagConstraints.BOTH;

		// Set the dimensions of the trainPanel and place it across the bottom of the
		// newsPanel
		GridBagConstraints trainGBC = new GridBagConstraints();
		trainGBC.gridx = 0;
		trainGBC.gridy = 2;
		trainGBC.gridheight = 1;
		trainGBC.gridwidth = 2;
		trainGBC.weightx = 1.0;
		trainGBC.weighty = 0.2;
		trainGBC.anchor = GridBagConstraints.NORTHWEST;
		trainGBC.fill = GridBagConstraints.BOTH;

		// Add all 4 panels to the frame according to their constraints previously
		// defined
		add(adPanel, adsGBC);
		add(weatherPanel, weatherGBC);
		add(newsPanel, newsGBC);
		add(trainPanel, trainGBC);

//		// Update the weather information
//		weatherTimer = new Timer(60000, e -> updateWeatherPanel());
//		weatherTimer.start();

		// Set up the timer to update the station every 20 seconds
//		trainTimer = new Timer(20000, e -> updateTrainPanel(trainPanel));
//		trainTimer.start();

		stationChangeTimer = new Timer(5000, e -> {
			currentStationIndex = (currentStationIndex + 1) % stations.size();
			SubwaySetup.initializeSubwaySystem(); // Run simulator and populate all lines and trains
			trains = SubwaySetup.getTrains();
			stations = SubwaySetup.getStations(); // Initialize the stations list
			updateTrainPanel(trainPanel);
		});
		stationChangeTimer.start();

//		// Timer to update news every 100 milliseconds
//		newsTimer = new Timer(500, e -> updateNewsPanel());
//		newsTimer.start();

		// Timer to update the ads every 10 seconds
		// TODO make the adPanel show the big map every other refresh

		adTimer = new Timer(10000, e -> {
			if (mapCounter % 2 != 0) {
				updateAdPanel(adCounter);
			} else {
				drawTrainPositionsOnMap();
			}
			mapCounter++;
		});
		adTimer.start();

		// Make the Frame visible
		setVisible(true);
	}

	private void addTrainInformation(JPanel trainPanel) {
		updateTrainPanel(trainPanel);
	}

	// Method to update current train station and next stations represented
	private void updateTrainPanel(JPanel trainPanel) {
		// Check if train list is empty and fetch the current station of the first train
		if (currentTrain != null) {
			Station currentStation = currentTrain.getCurrentStation();
			if (currentStation != null) {
				currentStationIndex = stations.indexOf(currentStation);
			}
		}

		trainPanel.removeAll();
		JPanel mapPanel = new JPanel();
		mapPanel.setLayout(new GridLayout(1, 6));

		int start = currentStationIndex - 1;
		int end = currentStationIndex + 5;
		if (start < 0) {
			start = 0;
		}
		if (end > stations.size()) {
			end = stations.size();
		}

		// Create larger fonts for the circles and station names
		Font circleFont = new Font("Arial", Font.PLAIN, 20); // Adjust the font size as needed
		Font stationNameFont = new Font("Arial", Font.PLAIN, 13); // Adjust the font size as needed

		for (int i = start; i < end; i++) {
			JPanel stationPanel = new JPanel();
			stationPanel.setLayout(new BorderLayout());

			JLabel stationLabel = new JLabel(stations.get(i).getStationName(), JLabel.CENTER);
			stationLabel.setFont(stationNameFont); // Set the larger font for station names
			JLabel circleLabel = new JLabel("\u25CB", JLabel.CENTER); // Unicode for a circle (○)
			circleLabel.setFont(circleFont); // Set the larger font for the circle

			if (i == currentStationIndex) {
				circleLabel.setText("\u25CF"); // Unicode for a filled circle (●)
				circleLabel.setForeground(Color.RED);
			}

			// Add station label closer to the circle
			stationPanel.add(circleLabel, BorderLayout.CENTER);
			stationPanel.add(stationLabel, BorderLayout.NORTH); // Move station name closer to the dot
			mapPanel.add(stationPanel);
		}

		// Create a larger font for the "Next Stop" label
		Font nextStopFont = new Font("Arial", Font.PLAIN, 16); // Adjust the font size as needed

		// Add the "Next:" label
		String nextStationName = currentStationIndex < stations.size() - 1
				? stations.get(currentStationIndex + 1).getStationName()
				: "End of Line";
		JLabel nextLabel = new JLabel("Next Stop: " + nextStationName, JLabel.CENTER);
		nextLabel.setFont(nextStopFont);
		trainPanel.add(mapPanel, BorderLayout.CENTER);
		trainPanel.add(nextLabel, BorderLayout.SOUTH);

		playStationAnnouncement(nextStationName);

		trainPanel.revalidate();
		trainPanel.repaint();
	}

	// Play the audio announcement for the given station name
	private void playStationAnnouncement(String stationName) {
		// Trim stationName to avoid issues with leading/trailing spaces
		stationName = stationName.trim();
		String audioFilePath = "data/stationAudio/" + stationName + ".mp3";
		System.out.println("Attempting to play audio file: " + audioFilePath);
		File audioFile = new File(audioFilePath);

		// Check if the audio file exists
		if (!audioFile.exists()) {
			System.err.println("Audio file not found: " + audioFilePath);
			return;
		}

		try (InputStream is = new FileInputStream(audioFilePath)) {
			Player player = new Player(is);
			player.play();
		} catch (FileNotFoundException e) {
			System.err.println("Audio file not found: " + audioFilePath);
			e.printStackTrace();
		} catch (JavaLayerException | IOException e) {
			System.err.println("Error playing the audio file: " + audioFilePath);
			e.printStackTrace();
		}
	}

	// Method to fetch Weather and Time from API's
	private void updateWeatherPanel() {
		if (args == null || args.length == 0) {
			weatherLabel.setText("Please provide a city as a command line argument.");
		} else {
			String city = args[0];
			WeatherParser weatherReport = new WeatherParser();
			String weatherInfo = weatherReport.getWeatherInfo(city);
			String time = weatherReport.getTime(city);
			weatherLabel.setText(weatherInfo);
			timeLabel.setText(time);
			cityLabel.setText(city);
		}
	}

	// Method to fetch news head titles based on keyword or world news from News API
	private void updateNewsPanel() {
		NewsAPI newsAPI = new NewsAPI();
		// Only fetches using the API if the news list is null (empty). Ensures we
		// aren't fetching every .5 seconds
		if (newsList == null) {
			// fetches news on second string in arg otherwise it fetches general news
			if (args.length != 2 || args[1] == null) {
				try {
					newsAPI.fetchNews(); // Fetch top headlines
					newsList = newsAPI.getNews();
					if (!newsList.isEmpty()) {
						newsIndex = (newsIndex + 1) % newsList.size();
						newsLabel.setText(newsList.get(newsIndex));
					}
				} catch (IOException e) {
					newsLabel.setText("Failed to fetch news");
				}
			} else {
				try {
					newsAPI.fetchNewsByKeyword(args[1]);
					if (newsList != null) {
						newsIndex = (newsIndex + 1) % newsList.size();
						newsLabel.setText(newsList.get(newsIndex));
					}
				} catch (IOException e) {
					newsLabel.setText("Failed to fetch news");
				}
			}
		} else { // if newsList isn't empty we iterate over to the next element and print to the
					// screen
			newsIndex = (newsIndex + 1) % newsList.size();
			newsLabel.setText(newsList.get(newsIndex));
		}
	}

	private void updateAdPanel(int adCount) {
		BufferedImage img = null;
		int index = adCounter % adPaths.size();

		try {
			img = ImageIO.read(new File(adPaths.get(adCounter)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (img != null) {
			Image scaledImage = img.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(scaledImage);
			JLabel newImageLabel = new JLabel(imageIcon);

			if (imageLabel != null) {
				imageLabel.setIcon(imageIcon); // Update the existing label with the new image
			} else {
				imageLabel = new JLabel(imageIcon); // Create the label if it doesn't exist
				adPanel.add(imageLabel, BorderLayout.CENTER);
			}

			// refresh the adPanel
			adPanel.setPreferredSize(new Dimension(500, 300));
			adPanel.revalidate();
			adPanel.repaint();
		}

		adCounter++;
	}

	private void showMap() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(MAP_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (img != null) {
			Image scaledImage = img.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(scaledImage);
			JLabel newImageLabel = new JLabel(imageIcon);

			if (imageLabel != null) {
				imageLabel.setIcon(imageIcon); // Update the existing label with the new image
			} else {
				imageLabel = new JLabel(imageIcon); // Create the label if it doesn't exist
				adPanel.add(imageLabel, BorderLayout.CENTER);
			}

			// refresh the adPanel
			adPanel.setPreferredSize(new Dimension(500, 300));
			adPanel.revalidate();
			adPanel.repaint();
		}
	}

	private void drawTrainPositionsOnMap() {
		final int origWidth = 1750, origHeight = 1750; // size used for cords of train stations
		int newWidth = 472, newHeight = 264; // size of map a.k.a Trains.png
		double scaleX = (double) newWidth / origWidth;
		double scaleY = (double) newHeight / origHeight;

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(MAP_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (img != null) {
			Graphics2D g2d = img.createGraphics(); // Create Graphics2D obj from buffered img
			g2d.setColor(Color.RED);
			int dotSize = 5; // Adjust this value to change the size of the dots
			for (Train train : trains) {
				if (train.getCurrentStation() != null) {
					Station station = train.getCurrentStation();

					// Calculate the scaled coordinates
					int scaledX = (int) (station.getXCoord() * scaleX);
					int scaledY = (int) (station.getYCoord() * scaleY);

					// Draw the dot on the map
					g2d.fillOval(scaledX - dotSize / 2, scaledY - dotSize / 2, dotSize, dotSize);
				}
			}
			g2d.dispose();
			
			// Update img label with new img
			Image scaledImage = img.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(scaledImage);
			if (imageLabel != null) {
				imageLabel.setIcon(imageIcon); // Update the existing label with the new image
			} else {
				imageLabel = new JLabel(imageIcon); // Create the label if it doesn't exist
				adPanel.add(imageLabel, BorderLayout.CENTER);
			}
			
			// Refresh the adPanel
//			adPanel.setPreferredSize(new Dimension(500, 300));
			adPanel.revalidate();
			adPanel.repaint();
		}
	}

	// Method to fix the size of JLabels
	private void setFixedSize(JLabel label, int width, int height) {
		Dimension size = new Dimension(width, height);
		label.setPreferredSize(size);
		label.setMinimumSize(size);
		label.setMaximumSize(size);
	}

	public static void main(String[] args) {
		startSimulator();
		SwingUtilities.invokeLater(() -> new SubwayScreen(args));
	}

	private static void startSimulator() {
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
		final Process finalProcess = process;

		// It will destroy the simulator process at the end
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (finalProcess != null) {
				finalProcess.destroy();
			}
		}));

		// Prints simulator in the console.
		// Just for test. Its while loop friezes the application.
//        InputStream inputStream = process.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        try {
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	}
}