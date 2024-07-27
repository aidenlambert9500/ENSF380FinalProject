package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SubwayScreen extends JFrame {
    private JLabel weatherLabel, timeLabel, cityLabel;
    private String[] args;
    private List<Station> stations;
    private int currentStationIndex = 1; // Starting from "Lakeview Heights Station"
    private Timer timer;

    public SubwayScreen(String[] args) {
        this.args = args;
        this.stations = initializeStations();

        // Initialize the frame with a title and layout
        setTitle("SubwayScreen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new GridBagLayout());

        // Create a section for the advertisements and big map
        JPanel adPanel = new JPanel();
        adPanel.setBackground(Color.LIGHT_GRAY);
        adPanel.add(new JLabel("Ad Panel"));
        adPanel.setPreferredSize(new Dimension(500, 300));

        // Create a section for the weather and time reports
        JPanel weatherPanel = new JPanel();
        weatherPanel.setBackground(Color.white);
        weatherPanel.add(new JLabel("Weather Panel"));
        weatherLabel = new JLabel("Temperature:");
        timeLabel = new JLabel("Time: ");
        cityLabel = new JLabel("CITY");
        weatherPanel.add(weatherLabel);
        weatherPanel.add(timeLabel);
        weatherPanel.add(cityLabel);
        weatherPanel.setPreferredSize(new Dimension(150, 300));

        // Create a section for the news
        JPanel newsPanel = new JPanel();
        newsPanel.setBackground(Color.cyan);
        newsPanel.add(new JLabel("News Panel"));
        newsPanel.setPreferredSize(new Dimension(700, 60));

        // Create a section for the trains
        JPanel trainPanel = new JPanel();
        trainPanel.setBackground(Color.white);
        trainPanel.setLayout(new BorderLayout());
        JLabel nextStationLabel = new JLabel("Next: ", JLabel.CENTER);
        nextStationLabel.setFont(new Font("Arial", Font.BOLD, 20));
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

        // Set the dimensions of the newsPanel and place it across the bottom of the adPanel and weatherPanel
        GridBagConstraints newsGBC = new GridBagConstraints();
        newsGBC.gridx = 0;
        newsGBC.gridy = 1;
        newsGBC.gridheight = 1;
        newsGBC.gridwidth = 2;
        newsGBC.weightx = 1.0;
        newsGBC.weighty = 0.2;
        newsGBC.anchor = GridBagConstraints.NORTHWEST;
        newsGBC.fill = GridBagConstraints.BOTH;

        // Set the dimensions of the trainPanel and place it across the bottom of the newsPanel
        GridBagConstraints trainGBC = new GridBagConstraints();
        trainGBC.gridx = 0;
        trainGBC.gridy = 2;
        trainGBC.gridheight = 1;
        trainGBC.gridwidth = 2;
        trainGBC.weightx = 1.0;
        trainGBC.weighty = 0.2;
        trainGBC.anchor = GridBagConstraints.NORTHWEST;
        trainGBC.fill = GridBagConstraints.BOTH;

        // Add all 4 panels to the frame according to their constraints previously defined
        add(adPanel, adsGBC);
        add(weatherPanel, weatherGBC);
        add(newsPanel, newsGBC);
        add(trainPanel, trainGBC);

        // Update the weather information
        updateWeatherPanel();

        // Set up the timer to update the station every 20 seconds
        timer = new Timer(20000, e -> updateTrainPanel(trainPanel));
        timer.start();

        // Make the Frame visible
        setVisible(true);
    }

    private void addTrainInformation(JPanel trainPanel) {
        updateTrainPanel(trainPanel);
    }

    private void updateTrainPanel(JPanel trainPanel) {
        trainPanel.removeAll();
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(1, 7)); // Adjusted to include the arrow

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
            JLabel circleLabel = new JLabel("\u25CB", JLabel.CENTER); // Unicode for a circle (○)

            if (i == currentStationIndex) {
                circleLabel.setText("\u25CF"); // Unicode for a filled circle (●)
                circleLabel.setForeground(Color.RED);
            }

            stationPanel.add(circleLabel, BorderLayout.NORTH);
            stationPanel.add(stationLabel, BorderLayout.SOUTH);
            mapPanel.add(stationPanel);

            if (i == currentStationIndex && i + 1 < end) {
                // Add arrow panel between the current and next station
                mapPanel.add(new ArrowPanel());
            }
        }

        trainPanel.add(mapPanel, BorderLayout.CENTER);
        trainPanel.add(new JLabel("Next: " + stations.get((currentStationIndex + 1) % stations.size()).getStationName(), JLabel.CENTER), BorderLayout.SOUTH);

        currentStationIndex = (currentStationIndex + 1) % stations.size();

        trainPanel.revalidate();
        trainPanel.repaint();
    }

    // Custom panel to draw the arrow
 // Custom panel to draw the arrow
    class ArrowPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);
            
            // Coordinates for the stick of the arrow
            int x1 = getWidth() / 3; // Adjusted to shorten the stick
            int y1 = getHeight() / 7; // Moved up slightly
            int x2 = 3 * getWidth() / 3; // Adjusted to shorten the stick
            int y2 = getHeight() / 7; // Moved up slightly
            
            // Draw the stick of the arrow
            g2d.drawLine(x1, y1, x2, y2);
            
            // Arrow size
            int arrowSize = 5; // Smaller arrow size
            
            // Coordinates for the arrowhead
            int[] xPoints = {x2 - arrowSize, x2 - arrowSize, x2};
            int[] yPoints = {y2 - arrowSize, y2 + arrowSize, y2};
            
            // Draw the arrowhead
            g2d.fillPolygon(xPoints, yPoints, 3);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(30, 30); 
        }
    }


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
        stations.add(new Station("R", "09", "R09", "Westridge Station"));
        stations.add(new Station("R", "10", "R10", "Pinehurst Station"));
        return stations;
    }

    private void updateWeatherPanel() {
        String city = args.length > 0 ? args[0] : "Calgary"; // Set default city to Calgary if no argument is passed
        weatherLabel.setText("Temperature: " + Weather.getTemperature(city));
        timeLabel.setText("Time: " + Weather.getTime());
        cityLabel.setText(city);
    }

    public static void main(String[] args) {
        new SubwayScreen(args);
    }
}

class Station {
    private String line;
    private String stationCode;
    private String stationID;
    private String stationName;

    public Station(String line, String stationCode, String stationID, String stationName) {
        this.line = line;
        this.stationCode = stationCode;
        this.stationID = stationID;
        this.stationName = stationName;
    }

    public String getLine() {
        return line;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationID() {
        return stationID;
    }

    public String getStationName() {
        return stationName;
    }
}

class Weather {
    public static String getTemperature(String city) {
        // Mock implementation, replace with actual API call
        return "20°C";
    }

    public static String getTime() {
        // Mock implementation, replace with actual time fetching
        return "12:00 PM";
    }
}
