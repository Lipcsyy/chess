package GamePanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The leaderboardPanel class extends JPanel and represents the leaderboard panel in the chess game.
 * It contains a reference to the GameFrame to handle navigation between panels.
 * It also contains a method to read the leaderboard from a file and display it on the panel.
 */
public class leaderboardPanel extends JPanel {
    GameFrame gameFrame;

    /**
     * The constructor for the leaderboardPanel class.
     * It initializes the gameFrame, sets up the size and layout of the panel, and adds a border with the title "Leaderboard".
     * It then reads the leaderboard from a file and adds each entry to the panel.
     * If an error occurs while reading the file, it adds an error message to the panel.
     * Finally, it adds a "Back" button to the panel that navigates back to the menu when clicked.
     *
     * @param _gameFrame The frame for the chess game
     */
    public leaderboardPanel(GameFrame _gameFrame)
    {
        gameFrame = _gameFrame;
        this.setPreferredSize( new Dimension(800, 800) );
        this.setSize( new Dimension(800, 800) );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Leaderboard"));

        int rank = 1; // Initialize a counter for the ranking
        try ( BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    String points = parts[1];
                    this.add(new JLabel(rank+ ". " + name + ": " + points));
                    rank++;
                }
            }
        } catch ( IOException e) {
            this.add(new JLabel("Error reading the leaderboard file."));
            e.printStackTrace();
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener( e -> {
            gameFrame.showMenu();
        });
        this.add(backButton);

    }
}