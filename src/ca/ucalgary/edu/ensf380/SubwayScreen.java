package ca.ucalgary.edu.ensf380;

/*
 * @author: Aiden Lambert, Jacelynn Doan, Wesley Lui 
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class SubwayScreen extends JFrame {
	// Member Variables
	private JLabel weatherLabel, timeLabel, cityLabel, newsLabel, imageLabel;
	private JPanel adPanel;
	private String[] args;
	private List<Station> stations;
	private int currentStationIndex = 1; // Starting from "Lakeview Heights Station"
	private Timer timer, newsTimer, adTimer;
	private int newsIndex = 0;
	private ArrayList<String> newsList;
	private Database db = new Database();
	private ArrayList<Advertisements> ads;
	private ArrayList<String> adPaths = new ArrayList<String>();
	private int adCounter = 0, mapCounter = 0;
	private final String MAP_PATH = "data/Trains.png";

	
 	public SubwayScreen(String[] args) {
 		// Constructor (launches gui)
		this.args = args;
		this.stations = initializeStations();
		
		// Connect to the database
		db.connect();
		
		// Get ad paths from database 
		ads = db.getAds();
		for(Advertisements ad : ads) {
			adPaths.add(ad.getFilePath());
		}
		
		
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

		// Update the weather information
		updateWeatherPanel();

		// Set up the timer to update the station every 20 seconds
		timer = new Timer(20000, e -> updateTrainPanel(trainPanel));
		timer.start();

		// Timer to update news every 100 milliseconds
		newsTimer = new Timer(500, e -> updateNewsPanel());
		newsTimer.start();

			
		// Timer to update the ads every 10 seconds
		// TODO make the adPanel show the big map every other refresh
		
		updateAdPanel(adCounter);
		adTimer = new Timer(10000, e -> {
			if(mapCounter % 2 != 0) {
				updateAdPanel(adCounter);
			} else {
				System.out.println("Implement function to make big map show here");
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

		for (int i = start; i < end; i++) {
			JPanel stationPanel = new JPanel();
			stationPanel.setLayout(new BorderLayout());

			JLabel stationLabel = new JLabel(stations.get(i).getStationName(), JLabel.CENTER);
			stationLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font for station names
			JLabel circleLabel = new JLabel("\u25CB", JLabel.CENTER); // Unicode for a circle (○)

			if (i == currentStationIndex) {
				circleLabel.setText("\u25CF"); // Unicode for a filled circle (●)
				circleLabel.setForeground(Color.RED);
			}

			// Add station label closer to the circle
			stationPanel.add(circleLabel, BorderLayout.CENTER);
			stationPanel.add(stationLabel, BorderLayout.NORTH); // Move station name closer to the dot
			mapPanel.add(stationPanel);
		}

		trainPanel.add(mapPanel, BorderLayout.CENTER);
		trainPanel.add(new JLabel("Next: " + stations.get((currentStationIndex + 1) % stations.size()).getStationName(),
				JLabel.CENTER), BorderLayout.SOUTH);

		currentStationIndex = (currentStationIndex + 1) % stations.size();

		trainPanel.revalidate();
		trainPanel.repaint();
	}

	// List array of Station variables containing all stations
	private List<Station> initializeStations() {
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("R", "01", "R01", "Maplewood Station"));
		stations.add(new Station("R", "02", "R02", "Lakeview Heights Station"));
		stations.add(new Station("R", "03", "R03", "Green Hills Station"));
		stations.add(new Station("R", "04", "R04", "Brightwater Station"));
		stations.add(new Station("R", "05", "R05", "Southland Plaza Station"));
		stations.add(new Station("R", "06", "R06", "Suncrest Station"));
		stations.add(new Station("R", "07", "R07", "Riverstone Station"));
		stations.add(new Station("R", "08", "R08", "Rosewood Station"));
		stations.add(new Station("R", "09", "R09", "Springhill Station"));
		stations.add(new Station("R", "10", "R10", "Forestview Station"));
		stations.add(new Station("R", "11", "R11", "Oakdale Station"));
		stations.add(new Station("R", "12", "R12", "Brookside Station"));
		stations.add(new Station("R", "13", "R13", "Highland Park Station"));
		stations.add(new Station("R", "14", "R14", "Cedar Point Station"));
		stations.add(new Station("R", "15", "R15", "Northgate Station"));
		stations.add(new Station("R", "16", "R16", "Central Square Station"));
		stations.add(new Station("R", "17", "R17", "Parkside Station"));
		stations.add(new Station("R", "18", "R18", "Woodland Station"));
		stations.add(new Station("R", "19", "R19", "Summerfield Station"));
		stations.add(new Station("R", "20", "R20", "City Hall Station"));
		stations.add(new Station("R", "21", "R21", "Hillcrest Station"));
		stations.add(new Station("R", "22", "R22", "Mill Creek Station"));
		stations.add(new Station("R", "23", "R23", "Riverside Station"));
		stations.add(new Station("R", "24", "R24", "Willow Grove Station"));
		stations.add(new Station("R", "25", "R25", "Pine Grove Station"));
		stations.add(new Station("R", "26", "R26", "Fairview Station"));
		stations.add(new Station("R", "27", "R27", "Lakeside Station"));
		stations.add(new Station("R", "28", "R28", "Grandview Station"));
		stations.add(new Station("R", "29", "R29", "Stonebridge Station"));
		stations.add(new Station("R", "30", "R30", "Westbrook Station"));
		stations.add(new Station("R", "31", "R31", "The Meadows Station"));
		stations.add(new Station("R", "32", "R32", "Fairview Station"));
		stations.add(new Station("R", "33", "R33", "West Hills Station"));
		stations.add(new Station("R", "34", "R34", "Oakwood Station"));
		stations.add(new Station("R", "35", "R35", "Southbank Station"));
		stations.add(new Station("R", "36", "R36", "Midtown Station"));
		stations.add(new Station("R", "37", "R37", "Maple Leaf Station"));
		stations.add(new Station("R", "38", "R38", "Lakewood Station"));
		stations.add(new Station("R", "39", "R39", "City Center Station"));
		stations.add(new Station("R", "40", "R40", "Forest Hill Station"));
		stations.add(new Station("R", "41", "R41", "Skyview Station"));
		stations.add(new Station("R", "42", "R42", "Cedar Hill Station"));
		stations.add(new Station("R", "43", "R43", "Hilltop Station"));
		stations.add(new Station("B", "1", "B01", "Northside Station"));
		stations.add(new Station("B", "2", "B02", "Bayview Station"));
		stations.add(new Station("B", "3", "B03", "Greenfield Station"));
		stations.add(new Station("B", "4", "B04", "University Station"));
		stations.add(new Station("B", "5", "B05", "Stonebridge Station"));
		stations.add(new Station("B", "6", "B06", "Cherrywood Station"));
		stations.add(new Station("B", "7", "B07", "Broadview Station"));
		stations.add(new Station("B", "8", "B08", "Riverfront Station"));
		stations.add(new Station("B", "9", "B09", "Parkview Station"));
		stations.add(new Station("B", "10", "B10", "Central Park Station"));
		stations.add(new Station("B", "11", "B11", "Redwood Station"));
		stations.add(new Station("B", "12", "B12", "Brookhaven Station"));
		stations.add(new Station("B", "13", "B13", "Elmwood Station"));
		stations.add(new Station("B", "14", "B14", "Downtown Station"));
		stations.add(new Station("B", "15", "B15", "Southside Station"));
		stations.add(new Station("B", "16", "B16", "Lakeside Station"));
		stations.add(new Station("B", "17", "B17", "Grandview Station"));
		stations.add(new Station("B", "18", "B18", "Harrison Station"));
		stations.add(new Station("B", "19", "B19", "Sunset Station"));
		stations.add(new Station("B", "20", "B20", "Sunnydale Station"));
		stations.add(new Station("B", "21", "B21", "Woodland Station"));
		stations.add(new Station("B", "22", "B22", "Meadowbrook Station"));
		stations.add(new Station("B", "23", "B23", "Forest Ridge Station"));
		stations.add(new Station("B", "24", "B24", "Springfield Station"));
		stations.add(new Station("B", "25", "B25", "Greenfield Park Station"));
		stations.add(new Station("B", "26", "B26", "Riverside Station"));
		stations.add(new Station("B", "27", "B27", "Cedar Grove Station"));
		stations.add(new Station("B", "28", "B28", "Oakwood Station"));
		stations.add(new Station("B", "29", "B29", "Oak Hill Station"));
		stations.add(new Station("B", "30", "B30", "Summit Hill Station"));
		stations.add(new Station("B", "31", "B31", "Pleasantview Station"));
		stations.add(new Station("B", "32", "B32", "Ridgeview Station"));
		stations.add(new Station("B", "33", "B33", "Northfield Station"));
		stations.add(new Station("B", "34", "B34", "Lakeshore Station"));
		stations.add(new Station("B", "35", "B35", "Mountainview Station"));
		stations.add(new Station("B", "36", "B36", "Vista Heights Station"));
		stations.add(new Station("B", "37", "B37", "Sunridge Station"));
		stations.add(new Station("B", "38", "B38", "Silver Creek Station"));
		stations.add(new Station("B", "39", "B39", "Timberline Station"));
		stations.add(new Station("B", "40", "B40", "Clearwater Station"));
		stations.add(new Station("B", "41", "B41", "Ridgewood Station"));
		stations.add(new Station("B", "42", "B42", "Horizon Station"));
		stations.add(new Station("B", "43", "B43", "Summit Station"));
		stations.add(new Station("B", "44", "B44", "South Park Station"));
		stations.add(new Station("G", "1", "G01", "Midway Station"));
		stations.add(new Station("G", "2", "G02", "Fairmont Heights Station"));
		stations.add(new Station("G", "3", "G03", "Oceanfront Heights Station"));
		stations.add(new Station("G", "4", "G04", "Ashland Station"));
		stations.add(new Station("G", "5", "G05", "Westmont Station"));
		stations.add(new Station("G", "6", "G06", "Southview Station"));
		stations.add(new Station("G", "7", "G07", "Lakefront Station"));
		stations.add(new Station("G", "8", "G08", "City Heights Station"));
		stations.add(new Station("G", "9", "G09", "Forest Manor Station"));
		stations.add(new Station("G", "10", "G10", "Skyline Heights Station"));
		stations.add(new Station("G", "11", "G11", "Cedar Heights Station"));
		stations.add(new Station("G", "12", "G12", "Hillside Station"));
		stations.add(new Station("G", "13", "G13", "North Hills Station"));
		stations.add(new Station("G", "14", "G14", "Bayview Heights Station"));
		stations.add(new Station("G", "15", "G15", "Green Hills Heights Station"));
		stations.add(new Station("G", "16", "G16", "University Park Station"));
		stations.add(new Station("G", "17", "G17", "Stoneview Station"));
		stations.add(new Station("G", "18", "G18", "Willow Grove Station"));
		stations.add(new Station("G", "19", "G19", "Cherrywood Heights Station"));
		stations.add(new Station("G", "20", "G20", "Riverfront Heights Station"));
		stations.add(new Station("G", "21", "G21", "Parkview Heights Station"));
		stations.add(new Station("G", "22", "G22", "Central Park Heights Station"));
		stations.add(new Station("G", "23", "G23", "Redwood Park Station"));
		stations.add(new Station("G", "24", "G24", "Brookhaven Heights Station"));
		stations.add(new Station("G", "25", "G25", "Elmwood Heights Station"));
		stations.add(new Station("G", "26", "G26", "Southside Heights Station"));
		stations.add(new Station("G", "27", "G27", "Midland Heights Station"));
		stations.add(new Station("G", "28", "G28", "Fairway Heights Station"));
		stations.add(new Station("G", "29", "G29", "Oceanview Heights Station"));
		stations.add(new Station("G", "30", "G30", "Ashwood Heights Station"));
		stations.add(new Station("G", "31", "G31", "Westwood Heights Station"));
		stations.add(new Station("G", "32", "G32", "Southgate Heights Station"));
		stations.add(new Station("G", "33", "G33", "Broadview Heights Station"));
		return stations;
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
		} catch (IOException e){
				e.printStackTrace();
		}
		
		if(img != null) {
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
			img = ImageIO.read(new File(mapPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(img != null) {
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
	
	// Method to fix the size of JLabels
	private void setFixedSize(JLabel label, int width, int height) {
		Dimension size = new Dimension(width, height);
		label.setPreferredSize(size);
		label.setMinimumSize(size);
		label.setMaximumSize(size);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new SubwayScreen(args));
		}
}
