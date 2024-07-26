package ca.ucalgary.edu.ensf380;
/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */
import javax.swing.*;
import java.awt.*;

public class SubwayScreen extends JFrame {
	public SubwayScreen(){
		// Initialize the frame with a title and layout
		setTitle("SubwayScreen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new GridBagLayout());
		
		// Create a section for the advertisements and big map
		JPanel adPanel = new JPanel();
		adPanel.setBackground(Color.LIGHT_GRAY);
		adPanel.add(new JLabel("Ad Panel"));
		adPanel.setPreferredSize(new Dimension(500,300));

		// Create a section for the weather reports
		JPanel weatherPanel = new JPanel();
		weatherPanel.setBackground(Color.white);
		weatherPanel.add(new JLabel("Weather Panel"));
		weatherPanel.setPreferredSize(new Dimension(150,300));
		
		// Create a section for the news 
		JPanel newsPanel = new JPanel();
		newsPanel.setBackground(Color.cyan);
		newsPanel.add(new JLabel("News Panel"));
		newsPanel.setPreferredSize(new Dimension(700, 60));
		
		// Create a section for the trains
		JPanel trainPanel = new JPanel();
		trainPanel.setBackground(Color.MAGENTA);
		trainPanel.add(new JLabel("Train Panel"));
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
		
		// Make the Frame visible
		setVisible(true);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(SubwayScreen::new);
    }
}
