package GamePanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * The gameStartPanel class extends JPanel and represents the panel displayed when starting a new game.
 * It contains a reference to the GameFrame to handle navigation between panels.
 * It also contains text fields for the players to enter their names and a button to start the game.
 */
public class gameStartPanel extends JPanel
{
    GameFrame gameFrame;


    /**
     * The constructor for the gameStartPanel class.
     * It initializes the gameFrame, sets up the size of the panel, and adds the text fields and button.
     * The text fields allow the players to enter their names.
     * The "Start game" button starts the game with the entered names and navigates to the chess game panel.
     *
     * @param _gameFrame The frame for the chess game
     */
    public gameStartPanel(GameFrame _gameFrame)
    {
        gameFrame = _gameFrame;
        this.setPreferredSize( new Dimension(800, 800) );
        this.setSize( new Dimension(800, 800) );

        JTextField playerWhite = new JTextField();
        JTextField playerBlack = new JTextField();

        //setting the placeholder text
        playerWhite.setText("WHITE");
        playerBlack.setText("BLACK");

        // Add focus listeners to the text fields to handle placeholder text
        playerWhite.addFocusListener( new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                playerWhite.setText("");
                playerWhite.setForeground( Color.BLACK );
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (playerWhite.getText().equals("")) {
                    playerWhite.setText("Player 1");
                    playerWhite.setForeground( Color.GRAY );
                }
            }
        } );
        playerBlack.addFocusListener( new FocusAdapter() {
            @Override
            public void focusGained( FocusEvent e) {
                playerBlack.setText("");
                playerWhite.setForeground( Color.BLACK );
            }

            @Override
            public void focusLost( FocusEvent e) {
                if (playerBlack.getText().equals("")) {
                    playerBlack.setText("Player 2");
                    playerWhite.setForeground( Color.GRAY );
                }
            }
        } );

        // Create a "Start game" button that starts the game when clicked
        JButton startGameButton = new JButton("Start game");
        startGameButton.addActionListener( e -> {
            gameFrame.showChessGame( playerWhite.getText(), playerBlack.getText() );
        } );

        // Add the text fields and button to the panel
        this.add( playerWhite );
        this.add( playerBlack );
        this.add( startGameButton );

    }
}