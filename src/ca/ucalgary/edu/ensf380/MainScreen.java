package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends JFrame {
    private JTextArea adArea;
    private JTextArea weatherArea;
    private JTextArea newsArea;
    private JTextArea trainInfoArea;

    private List<Advertisements> ads = new ArrayList<>();
    private WeatherReport weatherReport;
    private List<NewsFeed> newsFeeds = new ArrayList<>();
    private List<TrainInformation> trainInfo = new ArrayList<>();

    public MainScreen() {
        setTitle("Subway Screen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setupGUI();

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupGUI() {
        adArea = new JTextArea("Advertisements");
        weatherArea = new JTextArea("Weather");
        newsArea = new JTextArea("News");
        trainInfoArea = new JTextArea("Train Info");

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(createMainPanel(), BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(new JScrollPane(adArea));
        mainPanel.add(new JScrollPane(weatherArea));
        mainPanel.add(new JScrollPane(newsArea));
        mainPanel.add(new JScrollPane(trainInfoArea));
        return mainPanel;
    }

    public void updateDisplay() {
        for (Advertisements ad : ads) {
            ad.display();
        }
        if (weatherReport != null) {
            weatherReport.display();
        }
        for (NewsFeed news : newsFeeds) {
            news.display();
        }
        for (TrainInformation info : trainInfo) {
            info.display();
        }
    }

    public static void main(String[] args) {
        new MainScreen();
    }
}
