package GamePanels;

import javax.swing.*;
import java.awt.*;

/**
 * The wonPanel class extends JPanel and represents the panel displayed when the game ends.
 * It contains a reference to the GameFrame to handle navigation between panels.
 * It also contains a label to display the game result and a button to exit the game.
 */
public class wonPanel extends JPanel
{
    GameFrame gameFrame;

    /**
     * The constructor for the wonPanel class.
     * It initializes the gameFrame, sets up the size of the panel, and adds the label and button.
     * The label displays the game result, which can be a win for either player or a draw.
     * The "EXIT" button exits the game when clicked.
     *
     * @param _gameFrame The frame for the chess game
     * @param isDraw A boolean indicating whether the game was a draw
     * @param isCheckmate A boolean indicating whether the game ended in checkmate
     * @param playerWhite The name of the player playing as white
     * @param playerBlack The name of the player playing as black
     * @param isWhiteLast A boolean indicating whether the last move was made by the white player
     */
    public wonPanel(GameFrame _gameFrame, boolean isDraw, boolean isCheckmate, String playerWhite, String playerBlack, boolean isWhiteLast)
    {
        gameFrame = _gameFrame;
        this.setPreferredSize( new Dimension(800, 800) );
        this.setSize( new Dimension(800, 800) );

        // Determine the game result and create a label with the appropriate message
        if (isCheckmate) {
            if (isWhiteLast) {
                JLabel wonLabel = new JLabel(playerWhite + " won!");
                this.add(wonLabel);
            } else {
                JLabel wonLabel = new JLabel(playerBlack + " won!");
                this.add(wonLabel);
            }
        } else if (isDraw) {
            JLabel wonLabel = new JLabel("Draw!");
            this.add(wonLabel);
        }

        // Create an "EXIT" button that exits the game when clicked
        JButton backButton = new JButton("EXIT");
        backButton.addActionListener( e -> {
            gameFrame.exitGame();
        });

        this.add(backButton);

    }
}