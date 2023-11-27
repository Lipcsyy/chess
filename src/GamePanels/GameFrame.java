package GamePanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The GameFrame class extends JFrame and represents the main window of the chess game.
 * It contains a CardLayout and a JPanel to switch between different panels (e.g., game panel, menu panel, pause panel).
 * It also contains a ChessPanel for the chess game itself.
 */
public class GameFrame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel;

    private ChessPanel chessPanel = null;

    /**
     * The constructor for the GameFrame class.
     * It initializes the cardLayout, cardPanel, and chessPanel, and sets up the JFrame.
     * It also adds a key listener to handle the ESC key for pausing and resuming the game.
     */
    public GameFrame() {

        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setSize( 800, 800 );
        this.setResizable( false );
        this.setLocationRelativeTo( null );
        this.setFocusable(true);
        this.requestFocusInWindow();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        chessPanel = new ChessPanel( this );

        cardPanel.add( chessPanel, "Chess Game");
        cardPanel.add( new menuPanel(this, chessPanel), "Menu" );
        cardPanel.add( new pausePanel( this, chessPanel ), "Pause" );
        cardPanel.add( new gameStartPanel( this ), "Game Start" );
        cardPanel.add( new leaderboardPanel( this ), "Leaderboard" );

        cardLayout.show( cardPanel, "Menu" );

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed( KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                    JPanel currentCard = null;

                    for ( Component comp : cardPanel.getComponents() ) {
                        if ( comp.isVisible() ) {
                            currentCard = (JPanel) comp;
                        }
                    }

                    if ( currentCard.getName() == "ChessPanel" ) {
                        cardLayout.show( cardPanel, "Pause" );
                        return;
                    }

                    if ( currentCard.getName() == "PausePanel") {
                        cardLayout.show( cardPanel, "Chess Game" );
                        return;
                    }

                }
            }

            @Override
            public void keyTyped( KeyEvent e ) {

            }

            @Override
            public void keyReleased( KeyEvent e ) {

            }
        });
        this.add( cardPanel );
        this.pack();
    }


    /**
     * This method is used to show the chess game panel.
     * It sets the names of the players and shows the "Chess Game" card in the card layout.
     *
     * @param playerWhite The name of the player playing as white
     * @param playerBlack The name of the player playing as black
     */
    public void showChessGame( String playerWhite, String playerBlack ) {
        chessPanel.getController().setPlayers( playerWhite, playerBlack );
        cardLayout.show( cardPanel, "Chess Game" );
    }


    /**
     * This method is used to show the game start panel.
     * It shows the "Game Start" card in the card layout.
     */
    public void showGameStart() {
        cardLayout.show( cardPanel, "Game Start" );
    }


    /**
     * This method is used to show the game start panel.
     * It shows the "Game Start" card in the card layout.
     */
    public void showLeaderboard() {
        cardLayout.show( cardPanel, "Leaderboard" );
    }


    /**
     * This method is used to show the menu panel.
     * It shows the "Menu" card in the card layout.
     */
    public void showMenu() {
        cardLayout.show( cardPanel, "Menu" );
    }


    /**
     * This method is used to show the won panel.
     * It creates a new wonPanel with the game result and player names, adds it to the cardPanel, and shows the "Won" card in the card layout.
     *
     * @param isDraw A boolean indicating whether the game was a draw
     * @param isCheckmate A boolean indicating whether the game ended in checkmate
     * @param playerWhite The name of the player playing as white
     * @param playerBlack The name of the player playing as black
     * @param isWhite A boolean indicating whether the last move was made by the white player
     */
    public void showWonPanel( boolean isDraw, boolean isCheckmate, String playerWhite, String playerBlack, boolean isWhite ) {
        cardPanel.add( new wonPanel( this, isDraw, isCheckmate, playerWhite, playerBlack, isWhite ), "Won" );
        cardLayout.show( cardPanel, "Won" );
    }


    /**
     * This method is used to exit the game.
     * It calls System.exit(0) to terminate the JVM.
     */
    public void exitGame() {
        System.exit(0);
    }

}
